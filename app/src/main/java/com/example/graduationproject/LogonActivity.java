package com.example.graduationproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LogonActivity extends AppCompatActivity implements View.OnClickListener{

    private List<String> list;
    private ArrayAdapter adapter;
    private Spinner spinner;
    private Button login;
    private EditText user_account;
    private EditText password;
    private TextView forget_password;
    private CheckBox checkBox1,checkBox2;
    private Context context;
    protected static String u_account;
    protected static String u_password;
    protected static String u_id;
    protected static String u_logon="200";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        init();

    }
    private void initData(){
        list=new ArrayList<>();
        list.add("请选择登陆者身份");
        list.add("管理员");
        list.add("教师");
        list.add("学生");
    }
    //初始化控件
    private void init(){
        initData();

        spinner = findViewById(R.id.user_id);
        login = findViewById(R.id.login);
        user_account = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        forget_password = findViewById(R.id.login_forget_password);
        checkBox1 = findViewById(R.id.remember);
        checkBox2 = findViewById(R.id.self_login);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        login.setOnClickListener(this);
        forget_password.setOnClickListener(this);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login:
                u_account = user_account.getText().toString();
                u_password = password.getText().toString();
                u_id=String.valueOf(spinner.getSelectedItemPosition());
                if(u_id.equals("0")){
                    Toast.makeText(this, "请选择用户身份！", Toast.LENGTH_SHORT).show();
                    break;
                }
                login();
                break;
            case R.id.login_forget_password:
                Intent intent=new Intent(LogonActivity.this,ForgetActivity.class);
                intent.putExtra("account",user_account.getText().toString());
                startActivity(intent);
                LogonActivity.this.finish();
                break;
        }
    }
    private void login(){
        DBThread dt = new DBThread();
        Thread thread = new Thread(dt);
        thread.start();

        for(;u_logon.equals("200");){
            Toast.makeText(LogonActivity.this,"登陆中......",Toast.LENGTH_SHORT).show();
        }
        if(u_logon.equals("0")){
            Toast.makeText(LogonActivity.this,"用户名或密码输入错误",Toast.LENGTH_SHORT).show();
            u_logon="200";
        }
        else if(u_logon.equals("1")){
            u_logon="200";
            Intent intent;
            if (u_id.equals("1")){
                intent=new Intent(LogonActivity.this,Admin_List_Activity.class);
            }
            else if (u_id.equals("2")){
                intent=new Intent(LogonActivity.this,TeacherLogon.class);
            }
            else{
                intent=new Intent(LogonActivity.this,StudentLogon.class);
            }
            //用户名传到MainActivity
            intent.putExtra("account",user_account.getText().toString());
            startActivity(intent);
            LogonActivity.this.finish();
        }
    }
}
class DBThread implements Runnable {
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
            String sql ="select * from user where u_id="+LogonActivity.u_account+" and u_password='"+LogonActivity.u_password+"' and u_identity="+LogonActivity.u_id;
            ResultSet rt = st.executeQuery(sql);
            int count=0;
            while (rt.next()){
                count++;
            }
            if (count==0){
               LogonActivity.u_logon="0";
            }else if(count==1){
                LogonActivity.u_logon="1";
                conn.close();
                st.close();
                rt.close();
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

