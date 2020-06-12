package com.example.topmovies.dataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavouriteItem {

    public FavouriteItem(String title, String overView, String date, String posterUri, double vote) {
        this.title = title;
        this.overView = overView;
        this.date = date;
        this.posterUri = posterUri;
        this.vote = vote;
    }

    @PrimaryKey(autoGenerate = true)
    int id = 0;
    @ColumnInfo(name = "Item_Title")
    public String title;
    @ColumnInfo(name = "Item_Overview")
    public String overView;
    @ColumnInfo(name = "Item_Date")
    public String date;
    @ColumnInfo(name = "Item_poster")
    public String posterUri;
    @ColumnInfo(name = "vote")
    public double vote;
}
