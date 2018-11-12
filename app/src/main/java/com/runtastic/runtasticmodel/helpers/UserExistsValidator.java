package com.runtastic.runtasticmodel.helpers;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;
import com.runtastic.runtasticmodel.realm.RealmController;

public class UserExistsValidator extends Validator {
    private RealmController realm;

    public UserExistsValidator(RealmController _realm) {
        super("User already exists.");
        realm = _realm;
    }

    public boolean isValid(EditText et){
        if(realm.checkUser(et.getText().toString())){
            return false;
        }
        else return true;
    }
}
