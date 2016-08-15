package com.idzodev.pokemonapp.ui.dvo;

/**
 * Created by vladimir on 12.08.16.
 */
public class ZombiDvo {
    long id;
    String name;
    int photo;
    int rank;

    double lat;
    double lan;

    public ZombiDvo(long id, String name, int photo, int rank, double lat, double lan) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.rank = rank;
        this.lat = lat;
        this.lan = lan;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    public int getRank() {
        return rank;
    }

    public double getLat() {
        return lat;
    }

    public double getLan() {
        return lan;
    }
}
