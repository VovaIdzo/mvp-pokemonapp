package com.idzodev.pokemonapp.core.utils;

/**
 * Created by vladimir on 07.06.16.
 */
public final class EmailValidator {
    public static boolean isValid(String email){
        return !StringUtils.isNullEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
