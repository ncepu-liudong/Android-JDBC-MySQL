package com.example.graduationproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sure_change;
    private EditText user_account;
    private EditText user_name;
    private EditText password;
    private EditText sure_password;

    protected static String s_user_account;
    protected static String s_user_name;
    protected static String s_password;
    protected static String s_sure_password;
    protected static String s_update="100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        init();
    }
    private void init(){
        sure_change = findViewById(R.id.sure_change);
        user_account = findViewById(R.id.user_account);
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        sure_password = findViewById(R.id.sure_password);

        Intent intent = getIntent();
        user_account.setText(intent.getStringExtra("account"));
        Log.v("tag",intent.getStringExtra("account"));

        sure_change.setOnClickListener(this);
    }
    public void onClick(View view){
        s_user_account = user_account.getText().toString();
        s_user_name = user_name.getText().toString();
        s_password = password.getText().toString();
        s_sure_password = sure_password.getText().toString();
        if(s_password.equals(s_sure_password)){
            updata();
        }
        else {
            Toast.makeText(this, "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            password.setText("");
            sure_password.setText("");
        }
    }
    private void updata() {
        FDBThread dt = new FDBThread();
        Thread thread = new Thread(dt);
        thread.start();
        for (;s_update.equals("100");){
        }
        if (s_update=="0"){
            Toast.makeText(this, "账号或姓名错误，请重新输入", Toast.LENGTH_SHORT).show();
        }
        else if (s_update=="1"){
            //界面跳转
            Intent intent=new Intent(ForgetActivity.this,Update_password_success.class);
            startActivity(intent);
            ForgetActivity.this.finish();
        }
    }
}
class FDBThread implements Runnable {
    private static String driver = DateSet.getDriver();
    private static String url = DateSet.getUrl();
    private static String user = DateSet.getUser();
    private static String password = DateSet.getPassword();

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }
    public void update(){
        Connection conn = getConnection();
        Log.i("message",ForgetActivity.s_user_account);
        Log.i("message",ForgetActivity.s_password);
        Log.i("message",ForgetActivity.s_user_name);
        try {
            Statement st = conn.createStatement();
            String sql ="select * from user where u_id="+ForgetActivity.s_user_account+" and u_name='"+ForgetActivity.s_user_name+"'";
            ResultSet rt = st.executeQuery(sql);
            int count=0;
            while (rt.next()){
                count++;
            }
            String counts = String.valueOf(count);
            Log.i("count",counts);
            if (count==0){
                ForgetActivity.s_update="0";
            }else if(count==1){
                String sql2 = "update user set u_password='"+ForgetActivity.s_password+"' where u_id="+ForgetActivity.s_user_account;
                st.execute(sql2);
                ForgetActivity.s_update="1";
                conn.close();
                st.close();
                rt.close();
            }
            Log.i("s_update",ForgetActivity.s_update);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        update();
    }

}

