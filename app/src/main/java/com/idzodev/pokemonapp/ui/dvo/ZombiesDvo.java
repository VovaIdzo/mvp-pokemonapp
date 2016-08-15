package com.idzodev.pokemonapp.ui.dvo;

import java.util.List;

/**
 * Created by vladimir on 12.08.16.
 */
public class ZombiesDvo {
    List<ZombiDvo> zombis;

    public ZombiesDvo(List<ZombiDvo> zombis) {
        this.zombis = zombis;
    }

    public List<ZombiDvo> getZombis() {
        return zombis;
    }
}
