package com.sigran0.sendreceive.views;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.sigran0.sendreceive.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-05-18.
 */

public class ACEditText extends AppCompatEditText {

    private Context context;

    public ACEditText(Context context) {
        this(context, null);
    }

    public ACEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ACEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isFocused()) {
                    setHint(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
