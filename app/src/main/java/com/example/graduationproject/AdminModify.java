package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminModify extends AppCompatActivity implements View.OnClickListener {

    private static String u_name;
    private static String u_account;
    private static String u_identity;
    protected static String username;
    protected static int userid;
    protected static String m_state ="200";
    private EditText uname;
    private TextView uaccount;
    private TextView uidentity;
    private ImageButton delete;
    private ImageButton update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify);
        Intent intent = getIntent();
        u_name = intent.getStringExtra("name");
        u_account = intent.getStringExtra("account");
        u_identity = intent.getStringExtra("identity");
        init();
    }
    private void init(){
        uname = findViewById(R.id.user_name);
        uaccount = findViewById(R.id.user_account);
        uidentity = findViewById(R.id.user_identity);
        delete = findViewById(R.id.u_delete);
        update = findViewById(R.id.u_update);
        uname.setText(u_name);
        uaccount.setText(u_account);
        uidentity.setText(u_identity);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
    }
    public void onClick(View view){
        String u_id = uaccount.getText().toString();
        userid = Integer.valueOf(u_id).intValue();
        username = uname.getText().toString();
        switch (view.getId()){
            case R.id.u_update:
                UBThread dt = new UBThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;m_state.equals("200");){
                }
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                Intent intents = new Intent(AdminModify.this,Admin_List_Activity.class);
                startActivity(intents);
                AdminModify.this.finish();
                break;
            case R.id.u_delete:
                DEBThread dtt = new DEBThread();
                Thread tthread = new Thread(dtt);
                tthread.start();
                for (;m_state.equals("200");){
                }
                Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminModify.this,Admin_List_Activity.class);
                startActivity(intent);
                AdminModify.this.finish();
                break;
        }
    }
    public void onBackPressed(){
        AdminModify.this.finish();
        super.onBackPressed();
    }
}
class UBThread implements Runnable {
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
    public void updates(){
        Connection conn = getConnection();
        try {
            Statement st = conn.createStatement();
            String sql = "update user set u_name='"+AdminModify.username+"' where u_id="+AdminModify.userid+"";
            st.execute(sql);
            conn.close();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        updates();
        AdminModify.m_state = "1";
    }
}
class DEBThread implements Runnable {
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
    public void deletes(){
        Connection conn = getConnection();
        try {
            Statement st = conn.createStatement();
            String sql = "delete from user where u_id="+AdminModify.userid+"";
            st.execute(sql);
            conn.close();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        deletes();
        AdminModify.m_state = "1";
    }
}

