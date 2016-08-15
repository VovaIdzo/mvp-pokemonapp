package com.idzodev.pokemonapp.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public final class PermissionHelper {

    public static boolean isGrantedPermissions(Context context, String... permissions){
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public static void showRequestPermissionPopup(Activity activity, int request, String... strings){
        ActivityCompat.requestPermissions(
                activity,
                strings,
                request);
    }
}
