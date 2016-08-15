package com.idzodev.pokemonapp.core.rx;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import rx.Observable;

/**
 * Created by mmhryshchuk on 27.07.16.
 */
public class TextWatcherObservable {

  public static Observable<String> create(EditText editText){
      return Observable.create((Observable.OnSubscribe<String>) subscriber -> {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    subscriber.onNext(charSequence.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        });
    }
}
