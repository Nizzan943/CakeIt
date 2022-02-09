package com.nitzanidolidor.cakeit.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.nitzanidolidor.cakeit.R;

public class HomeFragment extends Fragment {

    ImageButton map;
    ImageButton waffle;
    ImageButton pai;
    ImageButton cupcake;
    ImageButton pancakes;
    ImageButton cookies;
    ImageButton icecream;
    ImageButton cake;
    ImageButton croissant;
    ImageButton cinnamon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        waffle = view.findViewById(R.id.waffle);
        pai= view.findViewById(R.id.pai);
        cupcake= view.findViewById(R.id.cupcake);
        pancakes= view.findViewById(R.id.pancakes);
        cookies= view.findViewById(R.id.cookies);
        icecream= view.findViewById(R.id.icecream);
        cake= view.findViewById(R.id.cake);
        croissant= view.findViewById(R.id.croissant);
        cinnamon= view.findViewById(R.id.cinnamon);

        waffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("waffle");
                Navigation.findNavController(view).navigate(action); }});

        pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("pai");
                Navigation.findNavController(view).navigate(action); }});

        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("cupcake");
                Navigation.findNavController(view).navigate(action); }});

        pancakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("pancakes");
                Navigation.findNavController(view).navigate(action); }});

        cookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("cookies");
                Navigation.findNavController(view).navigate(action); }});

        icecream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("icecream");
                Navigation.findNavController(view).navigate(action); }});

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("cake");
                Navigation.findNavController(view).navigate(action); }});

        croissant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("croissant");
                Navigation.findNavController(view).navigate(action); }});

        cinnamon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToListOfRecipesFragment action = HomeFragmentDirections.actionHomeFragmentToListOfRecipesFragment("cinnamon");
                Navigation.findNavController(view).navigate(action); }});

        return view;
    }
}