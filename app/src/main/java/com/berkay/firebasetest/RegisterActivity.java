package com.berkay.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout textInputEmail,textInputPassword;
    String emailInput,passwordInput;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void register(View view){

        if (!validateEmail() | !validatePassword()){
            return;
        }
        registerUser(emailInput,passwordInput);
    }

    private void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Bir hata oluştu! Kayıt Başarısız",Toast.LENGTH_LONG).show();
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
            return true;
        }
    }

    private boolean validatePassword(){
        passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputEmail.setError("Bu alan boş bırakılamaz");
            return false;
        }else if (passwordInput.length()<6){
            textInputPassword.setError("Parola 6 karakterden küçük olamaz");
            return false;
        }
        else {
            textInputEmail.setError(null);
            return true;
        }
    }
}