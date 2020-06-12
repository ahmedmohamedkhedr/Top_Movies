package com.example.topmovies.dataBase;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private DataBase dataBase;
    private static DatabaseClient client;

    private DatabaseClient(Context context) {
        dataBase = Room.databaseBuilder(context , DataBase.class , "favourites.db").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if (client == null){
            client = new DatabaseClient(context);
        }
        return client;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

}
