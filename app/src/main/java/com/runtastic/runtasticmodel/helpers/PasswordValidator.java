package com.runtastic.runtasticmodel.helpers;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;
import com.runtastic.runtasticmodel.realm.User;

public class PasswordValidator extends Validator {

    private User user;

    public PasswordValidator(User _user) {
        super("Invalid Password");
        user = _user;
    }

    public boolean isValid(EditText et){
        return et.getText().toString().equals(user.getPassword());
    }
}
