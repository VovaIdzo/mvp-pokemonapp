package com.idzodev.pokemonapp.core.utils;


public final class Xor {
    public static final String KEY = "idzodev";

    public static String encode(String s) {
            char[] buf = s.toCharArray(), key = KEY.toCharArray();
            for (int i = 0, a = 0; i < buf.length; i++, a++) {
                buf[i] = (char) (buf[i] ^ key[a]);
                if (a == key.length - 1) {
                    a = 0;
                }
            }
            s = new String(buf);
            return s;
    }
}