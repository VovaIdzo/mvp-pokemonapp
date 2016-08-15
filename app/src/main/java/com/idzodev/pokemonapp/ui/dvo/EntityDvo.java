package com.idzodev.pokemonapp.ui.dvo;

import com.idzodev.pokemonapp.domain.entities.MainEntity;

/**
 * Created by vladimir on 12.08.16.
 */
public class EntityDvo {
    MainEntity mainEntity;

    public EntityDvo(MainEntity mainEntity) {
        this.mainEntity = mainEntity;
    }

    public long getId() {
        return mainEntity.getId();
    }

    public String getName() {
        return mainEntity.getName();
    }

    public int getPhoto() {
        return mainEntity.getIcon();
    }

    public int getRank() {
        return mainEntity.getPower();
    }

    public double getLat() {
        return mainEntity.getLatitude();
    }

    public double getLan() {
        return mainEntity.getLongitude();
    }
}
