package com.demo.flowchart.auth.result;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

public class AuthSuccess extends AuthResult {

    public FirebaseUser user;

    public AuthSuccess(@NonNull FirebaseUser user) {
        this.user = user;
    }

}
