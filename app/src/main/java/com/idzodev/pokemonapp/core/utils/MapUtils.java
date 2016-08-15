package com.idzodev.pokemonapp.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created 25.01.2016.
 */
public class MapUtils {
    public static void cameraGoTo(GoogleMap map, LatLng lng, float zoom){
        CameraPosition.Builder builder = new CameraPosition.Builder()
                .target(lng)
                .bearing(45)
                .tilt(20);
        builder.zoom(zoom);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(builder.build());
        map.animateCamera(cameraUpdate);
    }

    @Nullable
    public static double[] getMyLocation(Context context) throws SecurityException {
        if (!PermissionUtils.isLocationPermissionGranted(context)){
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        final double[] myLocation = new double[2];
        myLocation[0] = location.getLatitude();
        myLocation[1] = location.getLongitude();
        return myLocation;
    }
}
