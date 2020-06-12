package com.example.topmovies.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void addFavourite(FavouriteItem item);

    @Query("SELECT * FROM FavouriteItem")
    List<FavouriteItem> getAllFavourites();

    @Delete
    void deleteFromFavourite(FavouriteItem item);

}
