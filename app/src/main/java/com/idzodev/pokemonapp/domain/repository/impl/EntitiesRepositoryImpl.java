package com.idzodev.pokemonapp.domain.repository.impl;

import android.database.Cursor;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.idzodev.pokemonapp.R;
import com.idzodev.pokemonapp.core.utils.CoordinateUtils;
import com.idzodev.pokemonapp.domain.entities.DaoMaster;
import com.idzodev.pokemonapp.domain.entities.DaoSession;
import com.idzodev.pokemonapp.domain.entities.MainEntity;
import com.idzodev.pokemonapp.domain.entities.MainEntityDao;
import com.idzodev.pokemonapp.domain.repository.EntitiesRepository;
import com.idzodev.pokemonapp.ui.dvo.EntityDvo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import rx.Observable;

/**
 * Created by vladimir on 12.08.16.
 */
public class EntitiesRepositoryImpl implements EntitiesRepository {


    private final static String SELECT_DATA =
            String.format("SELECT * FROM %s WHERE %s= ? AND %s > ? AND %s < ? AND %s < ? AND %s > ?",
                    MainEntityDao.TABLENAME,
                    MainEntityDao.Properties.Type.columnName,
                    MainEntityDao.Properties.Latitude.columnName,
                    MainEntityDao.Properties.Latitude.columnName,
                    MainEntityDao.Properties.Longitude.columnName,
                    MainEntityDao.Properties.Longitude.columnName);

    private final static int[] POKEMON_IMAGES = new int[]{
            R.drawable.pokemon1,
            R.drawable.pokemon2,
            R.drawable.pokemon3,
            R.drawable.pokemon4,
            R.drawable.pokemon5,
            R.drawable.pokemon6,
            R.drawable.pokemon7,
            R.drawable.pokemon8,
            R.drawable.pokemon9,
            R.drawable.pokemon10,
            R.drawable.pokemon11,
            R.drawable.pokemon12,
            R.drawable.pokemon13,
            R.drawable.pokemon14,
            R.drawable.pokemon15,
            R.drawable.pokemon16,
            R.drawable.pokemon17,
            R.drawable.pokemon18,
            R.drawable.pokemon19,
    };

    private DaoSession daoSession;

    public EntitiesRepositoryImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Observable<List<MainEntity>> getEntities(int type,double lat, double lon) {


        return Observable.create(subscriber -> {

            final double mult = 1; // mult = 1.1; is more reliable
            LatLng center = new LatLng(lat, lon);
            LatLng p1 = CoordinateUtils.calculateDerivedPosition(center, mult * USER_RADIUS, 0);
            LatLng p2 = CoordinateUtils.calculateDerivedPosition(center, mult * USER_RADIUS, 90);
            LatLng p3 = CoordinateUtils.calculateDerivedPosition(center, mult * USER_RADIUS, 180);
            LatLng p4 = CoordinateUtils.calculateDerivedPosition(center, mult * USER_RADIUS, 270);

            Cursor cursor = daoSession.getDatabase().rawQuery(SELECT_DATA, new String[]{
                    String.valueOf(type),
                    String.valueOf(p3.latitude),
                    String.valueOf(p1.latitude),
                    String.valueOf(p2.longitude),
                    String.valueOf(p4.longitude),
            });
            List<MainEntity> mainEntities = new LinkedList<MainEntity>();
            if (cursor.moveToFirst()){
                do {
                    MainEntity mainEntity = daoSession.getMainEntityDao().readEntity(cursor, 0);
                    if (CoordinateUtils.pointIsInCircle(lat, lon, mainEntity.getLatitude(), mainEntity.getLongitude(),  USER_RADIUS)){
                        mainEntities.add(mainEntity);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            subscriber.onNext(mainEntities);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<Boolean> generateEntities(double myLat, double myLon) {
        return Observable.create(subscriber -> {
            DaoMaster.dropAllTables(daoSession.getDatabase(), true);
            DaoMaster.createAllTables(daoSession.getDatabase(), true);

            List<MainEntity> entityDvos = new ArrayList<>(2000);
            int i = 0;
            while (entityDvos.size() != 2000){
                MainEntity mainEntity = new MainEntity();
                int type = MainEntity.TYPE_POKEMON;
                mainEntity.setName("Pokemon");
                if (entityDvos.size() <= 1000){
                    mainEntity.setName("Zombi");
                    type = MainEntity.TYPE_ZOMBI;
                    mainEntity.setIcon(R.drawable.pokemon20);
                } else {
                    if (i == POKEMON_IMAGES.length){
                        i = 0;
                    }
                    mainEntity.setIcon(POKEMON_IMAGES[i]);
                    i++;
                }
                mainEntity.setPower((int)(Math.random()*10));
                mainEntity.setType(type);
                LatLng randomLocation = CoordinateUtils.getRandomLocation(new LatLng(myLat, myLon), MAX_RADIUS);
                mainEntity.setLatitude(randomLocation.latitude);
                mainEntity.setLongitude(randomLocation.longitude);

                entityDvos.add(mainEntity);
            }

            daoSession.getMainEntityDao().insertInTx(entityDvos);
            subscriber.onNext(true);
            subscriber.onCompleted();
        });
    }

}
