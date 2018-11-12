package com.runtastic.runtasticmodel.helpers;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

public class PasswordsMatchValidator extends Validator {
    private String compare;

    public PasswordsMatchValidator(String _compare) {
        super("Password doesn't match.");
        compare = _compare;
    }

    public boolean isValid(EditText et){
        return et.getText().toString().equals(compare);
    }
}
