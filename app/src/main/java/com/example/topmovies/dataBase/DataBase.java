package com.example.topmovies.dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = FavouriteItem.class, version = 1)

public abstract class DataBase extends RoomDatabase {

    public abstract DAO getDAO ();
}
