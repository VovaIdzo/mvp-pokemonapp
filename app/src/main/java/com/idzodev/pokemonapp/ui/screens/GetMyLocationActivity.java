package com.idzodev.pokemonapp.ui.screens;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsStates;
import com.idzodev.pokemonapp.R;
import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.android.BaseActivity;
import com.idzodev.pokemonapp.core.utils.PermissionUtils;
import com.idzodev.pokemonapp.services.location.FusedLocationProvider;

/**
 * Created by vladimir on 15.08.16.
 */
public class GetMyLocationActivity extends BaseActivity {
    public final static String EXTRA_LAT = "EXTRA_LAT";
    public final static String EXTRA_LON = "EXTRA_LON";

    private static final int PERMISSION_LOCATION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;

    private FusedLocationProvider fusedLocationProvider;

    public static void start(Activity activity, int request){
        activity.startActivityForResult(new Intent(activity, GetMyLocationActivity.class), request);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_my_location_activity);

        fusedLocationProvider = App.getApp(this)
                .getAppComponent()
                .getFusedLocationProvider();

        if (!PermissionUtils.isLocationPermissionGranted(this)) {
            PermissionUtils.requestLocationPermission(this, PERMISSION_LOCATION);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
                // All required changes were successfully made
                fusedLocationProvider.register(listener);
            } else {
                // The user was asked to change settings, but chose not to
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                fusedLocationProvider.register(listener);
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fusedLocationProvider.register(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationProvider.isRegister()){
            fusedLocationProvider.unregister();
        }
    }

    private final FusedLocationProvider.OnLocationChangeListener listener = new FusedLocationProvider.OnLocationChangeListener() {
        @Override
        public void onLocationChange(Location location) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_LAT, location.getLatitude());
            intent.putExtra(EXTRA_LON, location.getLongitude());
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        public void onShowSetupLocationSettingsPopup() {
            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(viewIntent);
        }

        @Override
        public void onResolutionRequired(Status status) {
            try {
                // Show the dialog by calling startResolutionForResult(),
                // and check the result in onActivityResult().
                status.startResolutionForResult(
                        GetMyLocationActivity.this,
                        REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException e) {
                // Ignore the error.
            }

        }
    };

    @Override
    public void onBackPressed() {
    }
}
