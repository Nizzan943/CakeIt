package com.nitzanidolidor.cakeit.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nitzanidolidor.cakeit.MyApplication;

@Database(entities = {Recipe.class}, version = 8)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract RecipeDao RecipeDao();
}

public class AppLocalDb {

    static public AppLocalDbRepository db=
            Room.databaseBuilder(
                    MyApplication.context,
                    AppLocalDbRepository.class,
                    "AppLocalDb.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
