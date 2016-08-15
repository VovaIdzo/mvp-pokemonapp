package com.idzodev.pokemonapp.ui.dvo;

import java.util.List;

/**
 * Created by vladimir on 12.08.16.
 */
public class MapDataDvo {
    List<EntityDvo> mapData;

    public MapDataDvo(List<EntityDvo> mapData) {
        this.mapData = mapData;
    }

    public List<EntityDvo> getEntities() {
        return mapData;
    }
}
