// MainActivity.java
package com.example.loginn;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button teacherLoginBtn, adminLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherLoginBtn = findViewById(R.id.teacherLoginBtn);
        adminLoginBtn = findViewById(R.id.adminLoginBtn);

        teacherLoginBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TeacherLoginActivity.class)));

        adminLoginBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class)));
    }
}
