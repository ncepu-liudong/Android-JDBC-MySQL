package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddUsers extends AppCompatActivity {

    private List<String> list;
    private ArrayAdapter adapter;
    private Spinner spinner;
    private Button u_create;
    private ImageButton returns;
    private EditText u_name;
    private EditText u_account;
    private EditText u_password;
    private EditText u_s_password;
    protected static String username;
    protected static String useraccount;
    protected static String userpassword;
    protected static String usersurepassword;
    protected static int uidentity;
    protected static String c_state = "200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_users);
        init();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add("请选择用户身份");
        list.add("管理员");
        list.add("教师");
        list.add("学生");
    }

    //初始化控件
    private void init() {
        initData();
        spinner = findViewById(R.id.user_identity);
        u_create = findViewById(R.id.u_create);
        u_name = findViewById(R.id.user_name);
        u_account = findViewById(R.id.user_account);
        u_password = findViewById(R.id.password);
        u_s_password = findViewById(R.id.sure_password);
        returns = findViewById(R.id.returns);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        u_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useraccount = u_account.getText().toString();
                username = u_name.getText().toString();
                userpassword = u_password.getText().toString();
                usersurepassword = u_s_password.getText().toString();
                String uid = String.valueOf(spinner.getSelectedItemId());
                uidentity = Integer.valueOf(uid).intValue();
                if (userpassword.equals(usersurepassword)) {
                    insertuser();
                } else {
                    Toast.makeText(AdminAddUsers.this, "请两次密码输入一直！", Toast.LENGTH_SHORT).show();
                    u_password.setText("");
                    u_s_password.setText("");
                }
            }
        });
        returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddUsers.this,Admin_List_Activity.class);
                startActivity(intent);
                AdminAddUsers.this.finish();
            }
        });
    }

    public void insertuser() {
        CDBThread dt = new CDBThread();
        Thread thread = new Thread(dt);
        thread.start();
        for (;c_state.equals("200");){
        }
        if(c_state.equals("0")){
            Toast.makeText(AdminAddUsers.this, "已有该用户！", Toast.LENGTH_SHORT).show();
            u_account.setText("");
            c_state = "200";
        }else if (c_state.equals("1")){
            Toast.makeText(AdminAddUsers.this, "创建用户成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Admin_List_Activity.class);
            startActivity(intent);
            AdminAddUsers.this.finish();
        }
        //Toast.makeText(AdminAddUsers.this, "创建用户成功！", Toast.LENGTH_SHORT).show();
    }
}
class CDBThread implements Runnable {
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
    public void insert(){
        Connection conn = getConnection();
        try{
            Statement st = conn.createStatement();
            Statement stt = conn.createStatement();
            String sql ="select * from user where u_id='"+AdminAddUsers.useraccount+"'";
            String sql2 = "insert into user (u_id,u_name,u_password,u_identity) values( "+AdminAddUsers.useraccount+", '"+AdminAddUsers.username+"', '"+AdminAddUsers.userpassword+"', '"+AdminAddUsers.uidentity+"')";
            ResultSet rt = st.executeQuery(sql);
            if (rt.next()){
                AdminAddUsers.c_state="0";
            }else {
                stt.execute(sql2);
                AdminAddUsers.c_state="1";
            }
            conn.close();
            st.close();
            rt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
       insert();
    }

}

