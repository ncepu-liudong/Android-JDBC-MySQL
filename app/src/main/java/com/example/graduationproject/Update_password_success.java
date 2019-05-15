package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Update_password_success extends AppCompatActivity implements View.OnClickListener {
    private Button return_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_success);
        init();
    }
    private void init(){
        return_login = findViewById(R.id.return_login);
        return_login.setOnClickListener(this);
    }
    public void onClick(View view){
        Intent intent=new Intent(Update_password_success.this,LogonActivity.class);
        startActivity(intent);
        Update_password_success.this.finish();
    }
}
