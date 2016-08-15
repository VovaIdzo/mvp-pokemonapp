package com.idzodev.pokemonapp.core.utils;

/**
 * Created by mmhryshchuk on 03.08.16.
 */
public final class ConverterUtils {

    public static String secToStringTime(long sec){
        int hours = (int) sec / 3600;
        int reminder = (int) sec - hours * 3600;
        int minutes = reminder / 60;
        if (hours == 0){
            return minutes + " мин";
        }
        return hours + " час " + minutes + " мин";
    }

    public static String byteToMb(long bytes,boolean si){
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " б";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "кМГT" : "кМГT").charAt(exp-1) + (si ? "" : "");
        return String.format("%.1f %sб", bytes / Math.pow(unit, exp), pre);
    }
}
