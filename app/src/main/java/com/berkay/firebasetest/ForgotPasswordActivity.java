package com.berkay.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputLayout textInputEmail;
    String emailInput;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textInputEmail = findViewById(R.id.textInputEmail);

        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void sendPassword(View view){
        if (!validateEmail()){
            return;
        }
        firebaseAuth.sendPasswordResetEmail(emailInput).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,"Mail Adresinizi Kontrol Ediniz",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ForgotPasswordActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean validateEmail(){
        emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError("Bu alan boş bırakılamaz");
            return false;
        }else {
            textInputEmail.setError(null);
            //textInputEmail.setErrorEnabled(false);
            return true;
        }
    }
}