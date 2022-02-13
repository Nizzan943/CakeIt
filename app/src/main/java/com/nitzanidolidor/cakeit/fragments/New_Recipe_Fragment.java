package com.nitzanidolidor.cakeit.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.nitzanidolidor.cakeit.MyApplication;
import com.nitzanidolidor.cakeit.R;
import com.nitzanidolidor.cakeit.model.Model;
import com.nitzanidolidor.cakeit.model.ModelStorage;
import com.nitzanidolidor.cakeit.model.Recipe;
import com.nitzanidolidor.cakeit.model.User;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.UUID;


public class New_Recipe_Fragment extends Fragment {

    View view;
    EditText recipeTitle;
    EditText recipeIngredients;
    EditText recipeInstructions;
    Spinner chooseACategory;
    ImageView addAImage;
    Button uploadBtn;
    Uri addImageUri;
    Bitmap addImageBitmap;
    static int REQUEST_CODE = 1;

    FusedLocationProviderClient client;


    public New_Recipe_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new__recipe_, container, false);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        recipeTitle= view.findViewById(R.id.new_recipe_title_edit_text);
        recipeIngredients = view.findViewById(R.id.new_recipe_ingredients_edit_text);
        recipeInstructions = view.findViewById(R.id.new_recipe_instructions_edit_text);
        chooseACategory = (Spinner) view.findViewById(R.id.new_recipe_category_spinner);
        addAImage= view.findViewById(R.id.new_recipe_camera_image_view);
        uploadBtn= view.findViewById(R.id.new_recipe_upload_btn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MyApplication.context,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseACategory.setAdapter(adapter);

        addAImage.setOnClickListener((view) -> {
            chooseImageFromGallery();
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBtn.setEnabled(false);
                if (addImageUri != null && recipeTitle != null && recipeIngredients != null && recipeInstructions != null ) {
                    saveARecipe();
                }
                    else
                {
                    Toast.makeText(getContext(), "Please fill all fields and add a photo", Toast.LENGTH_SHORT).show();
                    uploadBtn.setEnabled(true);
                }
            }
        });
        return view;
    }

    @SuppressLint("MissingPermission")
    void saveARecipe() {
            //Init location manager
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            //Check condition
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                // When location service is enabled
                // Get last location
                client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        //Init location
                        Location location = task.getResult();
                        //check condition
                        if(location != null){
                            final Recipe newRecipe = createANewRecipe(
                                    location.getLatitude(), location.getLongitude()
                            );

                            ModelStorage.uploadImage(addImageBitmap, new ModelStorage.Listener() {
                                @Override
                                public void onSuccess(String url) {
                                    newRecipe.setRecipeImageUrl(url);
                                    Model.instance.addRecipe(newRecipe, new Model.Listener<Boolean>() {
                                        @Override
                                        public void onComplete(Boolean data) {
                                            NavController navCtrl = Navigation.findNavController(view);
                                            navCtrl.navigateUp();
                                        }
                                    });
                                }

                                @Override
                                public void onFail() {
                                    Snackbar.make(view, "Failed to create post recipe and save it", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                });
            }

    }


    private Recipe createANewRecipe(Double latitude, Double longitude) {
        Log.i("TAG","Cakeit" + User.getInstance().getUserId());
        Recipe newRecipe = new Recipe();
        newRecipe.setRecipeId(UUID.randomUUID().toString());
        newRecipe.setRecipeName(recipeTitle.getText().toString());
        newRecipe.setRecipeIngredients(recipeIngredients.getText().toString());
        newRecipe.setRecipeContent(recipeInstructions.getText().toString());
        newRecipe.setUserId(User.getInstance().userId);
        newRecipe.setRecipeImageUrl(User.getInstance().getUserprofileImageUrl());
        newRecipe.setUsername(User.getInstance().username);
        newRecipe.setCategoryId(chooseACategory.getSelectedItem().toString());
        newRecipe.setLatitude(latitude);
        newRecipe.setLongitude(longitude);
        return newRecipe;
    }

    void chooseImageFromGallery() {
        try {
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "*Error* Choose image from gallery: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( data != null && resultCode == RESULT_OK) {
            addImageUri = data.getData();
            addAImage.setImageURI(addImageUri);
            addImageBitmap = uriToBitmap(addImageUri);
        } else {
            Toast.makeText(getActivity(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}