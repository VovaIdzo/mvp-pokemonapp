package com.idzodev.pokemonapp.services.location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.icu.util.TimeUnit;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.utils.CoordinateUtils;
import com.idzodev.pokemonapp.core.utils.PermissionUtils;

import javax.inject.Inject;

/**
 * Created by on 02.03.16.
 */
public class FusedLocationProvider {
    private final GoogleApiClient mClient;
    private final OnLocationListener onLocationListener = new OnLocationListener();
    private OnLocationChangeListener listener;
    private Location lastLocation;
    LocationRequest mLocationRequest;
    private boolean isGoogleApiSupporting = true;
    private boolean isRegisterLocationManagerProvider = false;
    private App app;

    @Inject
    public FusedLocationProvider(App app) {
        this.app = app;
        mClient = new GoogleApiClient.Builder(app)
                .addConnectionCallbacks(onLocationListener)
                .addOnConnectionFailedListener(onLocationListener)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10*1000);
        mLocationRequest.setFastestInterval(5*1000);
        mLocationRequest.setSmallestDisplacement(3);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void register(OnLocationChangeListener listener) {
        this.listener = listener;
        if (!PermissionUtils.isLocationPermissionGranted(app) ) {
            return;
        }

        if (!isAllSettingsGood()){
            return;
        }

        if (isGoogleApiSupporting){
            mClient.connect();
        }
        else {
            LocationManager locationManager = getLocationManager();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10*1000,
                    10,
                    onLocationListener
            );
            isRegisterLocationManagerProvider = true;
        }
    }

    public void unregister() {
        if (isGoogleApiSupporting && mClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mClient, onLocationListener);
            mClient.disconnect();
        } else {
            isRegisterLocationManagerProvider = false;
            LocationManager locationManager = getLocationManager();
            locationManager.removeUpdates(onLocationListener);
        }
    }

    public boolean isRegister(){
        if (isGoogleApiSupporting){
            return mClient.isConnected();
        } else {
            return isRegisterLocationManagerProvider;
        }
    }

    public Location getLastLocation(Context context) {
        if (!PermissionUtils.isLocationPermissionGranted(context)){
            return lastLocation;
        }

            if (isGoogleApiSupporting){
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);
            } else {

                LocationManager locationManager = getLocationManager();
                lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        return lastLocation;
    }

    private LocationManager getLocationManager(){
        return (LocationManager) app.getSystemService(Context.LOCATION_SERVICE);
    }

    public interface OnLocationChangeListener {
        void onLocationChange(Location location);
        void onShowSetupLocationSettingsPopup();
        void onResolutionRequired(Status status);
    }

    private boolean isAllSettingsGood(){
        LocationManager locationManager = getLocationManager();
        // Location settings are not satisfied. However, we have no way to fix the
        // settings so we won't show the dialog.
        final boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPS) {
            listener.onShowSetupLocationSettingsPopup();
            return false;
        }
        return true;
    }

    private class OnLocationListener implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            LocationListener,
            android.location.LocationListener
             {
                 @Override
                 public void onConnected(Bundle bundle) {
                     LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                             .addLocationRequest(mLocationRequest);

                     PendingResult<LocationSettingsResult> result =
                             LocationServices.SettingsApi.checkLocationSettings(mClient,
                                     builder.build());
                     result.setResultCallback(locationSettingsResult -> {
                         final Status status = locationSettingsResult.getStatus();
                         final LocationSettingsStates locationSettingsStates = locationSettingsResult.getLocationSettingsStates();
                         switch (status.getStatusCode()) {
                             case LocationSettingsStatusCodes.SUCCESS:
                                 isGoogleApiSupporting = true;
                                 // All location settings are satisfied. The client can
                                 // initialize location requests here.
                                 startTrackLocation();
                                 break;
                             case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                 isGoogleApiSupporting = true;
                                 // Location settings are not satisfied, but this can be fixed
                                 // by showing the user a dialog.

                                 // Show the dialog by calling startResolutionForResult(),
                                 // and check the result in onActivityResult().
                                 unregister();
                                 listener.onResolutionRequired(status);
                                 break;
                             case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                 // Location settings are not satisfied. However, we have no way
                                 // to fix the settings so we won't show the dialog.
                                 unregister();
                                 isGoogleApiSupporting = false;
                                 register(listener);
                                 break;
                         }
                     });
                 }

                 @Override
                 public void onConnectionSuspended(int i) {

                 }

                 @Override
                 public void onConnectionFailed(ConnectionResult connectionResult) {

                 }


                 @Override
                 public void onLocationChanged(Location location) {
                         lastLocation = location;
                         if (listener != null)
                             listener.onLocationChange(location);
                 }

                 @Override
                 public void onStatusChanged(String s, int i, Bundle bundle) {

                 }

                 @Override
                 public void onProviderEnabled(String s) {

                 }

                 @Override
                 public void onProviderDisabled(String s) {

                 }
    }

    private void startTrackLocation(){
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mClient, mLocationRequest, onLocationListener);
        } catch (SecurityException e){

        }
    }
}
