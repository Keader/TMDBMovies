package dev.keader.tmdbmovies.api.tmdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Genre {
    @PrimaryKey
    private int id;
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
