package com.example.hhhhhh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.hhhhhh.FirebaseID.email;
import static com.example.hhhhhh.FirebaseID.password;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText Email, Password;
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.login_email);
        Password = findViewById(R.id.login_password);

        findViewById(R.id.login_success).setOnClickListener(this);
        findViewById(R.id.login_signup).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = Auth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_signup:
                startActivity(new Intent(this, SignupActivity.class));
                break;

            case R.id.login_success:
                Auth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = Auth.getCurrentUser();
                                    if (user != null) {
                                        Toast.makeText(LoginActivity.this, "Login success." + user.getUid(),
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }

                                } else {
                                    Toast.makeText(LoginActivity.this, "Login error.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                break;

        }
    }
}