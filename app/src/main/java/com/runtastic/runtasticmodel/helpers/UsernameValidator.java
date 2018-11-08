package com.runtastic.runtasticmodel.helpers;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;
import com.runtastic.runtasticmodel.realm.RealmController;


public class UsernameValidator extends Validator {
    private RealmController realm;

    public UsernameValidator(RealmController _realm) {
        super("Invalid Username");
        realm = _realm;
    }

    public boolean isValid(EditText et){
        return realm.checkUser(et.getText().toString());
    }
}
