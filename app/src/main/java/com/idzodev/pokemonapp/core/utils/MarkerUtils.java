package com.idzodev.pokemonapp.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idzodev.pokemonapp.R;
import com.idzodev.pokemonapp.ui.dvo.EntityDvo;
import com.idzodev.pokemonapp.ui.dvo.ZombiDvo;

/**
 * Created by vladimir on 12.08.16.
 */
public class MarkerUtils {

    public static MarkerOptions createMyMarker(Context context, long id, String name, double lat, double lan){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(context, 0, true)));
        markerOptions.snippet(name);
        markerOptions.title(""+0);
        markerOptions.position(new LatLng(lat, lan));
        return markerOptions;
    }

    public static MarkerOptions createEntityMarker(Context context,EntityDvo dvo){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(context, dvo.getPhoto(), false)));
        markerOptions.title(""+dvo.getId());
        markerOptions.position(new LatLng(dvo.getLat(), dvo.getLan()));
        return markerOptions;
    }

    private static Bitmap getMarkerBitmapFromView(Context context, @DrawableRes int resId, boolean isMe) {
        View marker = LayoutInflater.from(context).inflate(R.layout.marker_pokemon, null);
        ImageView markerImageView = (ImageView) marker.findViewById(R.id.marker_pokemon_icon);
        if (isMe){
            markerImageView.setVisibility(View.GONE);
            ImageView location = (ImageView) marker.findViewById(R.id.marker_pokemon_location);
            location.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            markerImageView.setImageResource(resId);
        }
        marker.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        marker.layout(0, 0, marker.getMeasuredWidth(), marker.getMeasuredHeight());
        marker.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = marker.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        marker.draw(canvas);
        return returnedBitmap;
    }
}
