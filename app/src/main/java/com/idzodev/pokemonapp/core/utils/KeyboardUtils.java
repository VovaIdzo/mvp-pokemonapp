package com.idzodev.pokemonapp.core.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by on 22.02.16.
 */
public final class KeyboardUtils {

    public static void openKeyboard(View requestView){
        requestView.requestFocus();
        InputMethodManager inputMethodManager=(InputMethodManager) requestView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(requestView.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeKeyboard(View requestView){
        InputMethodManager imm = (InputMethodManager) requestView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(requestView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
