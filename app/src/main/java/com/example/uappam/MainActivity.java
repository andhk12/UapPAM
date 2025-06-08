package com.example.uappam; // Ganti dengan nama package Anda

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvRegisterLink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi Views
        btnLogin = findViewById(R.id.btn_login);
        tvRegisterLink = findViewById(R.id.tv_register_link);

        // Listener untuk tombol Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Listener untuk link Register
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cek jika user sudah login, langsung pindah ke HomeActivity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Jika Anda ingin user yang sudah login langsung ke halaman utama
            // Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            // startActivity(intent);
            // finish();
        }
    }
}