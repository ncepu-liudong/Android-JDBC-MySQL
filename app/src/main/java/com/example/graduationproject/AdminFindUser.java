package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminFindUser extends AppCompatActivity {

    private TextView user_account;
    private Button finduser;
    protected static String u_id;
    protected static String u_name;
    protected static String u_identity;
    protected static String f_state = "200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_find_user);
        init();
    }
    private void init(){
        user_account = findViewById(R.id.user_account);
        finduser = findViewById(R.id.finduser);
        finduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_id = user_account.getText().toString();
                ADBThread dt = new ADBThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;f_state.equals("200");){
                }
                if (f_state.equals("0")){
                    Toast.makeText(AdminFindUser.this, "无该用户,请核对！", Toast.LENGTH_SHORT).show();
                    user_account.setText("");
                }else if (f_state.equals("1")){
                    Intent intent = new Intent(getApplicationContext(),AdminModify.class);
                    intent.putExtra("name",u_name);
                    intent.putExtra("account",u_id);
                    intent.putExtra("identity",u_identity);
                    startActivity(intent);
                    AdminFindUser.this.finish();
                }
            }
        });
    }
}
class ADBThread implements Runnable {
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
    public void query(){
        Connection conn = getConnection();
        try {
            Statement st = conn.createStatement();
            String sql ="select * from user where u_id="+AdminFindUser.u_id;
            ResultSet rt = st.executeQuery(sql);
            int count=0;
            if (rt.next()){
                AdminFindUser.u_name = rt.getString("u_name");
                String id = rt.getString("u_identity");
                if (id.equals("1")){
                    AdminFindUser.u_identity="管理员";
                }else if (id.equals("2")){
                    AdminFindUser.u_identity="教师";
                }else
                {
                    AdminFindUser.u_identity="学生";
                }
                AdminFindUser.f_state="1";
            }
            else {
                AdminFindUser.f_state="0";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        query();
    }

}

