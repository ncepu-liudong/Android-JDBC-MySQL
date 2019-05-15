package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminStudentList extends AppCompatActivity {

    private ListView listView;
    private ImageButton new_user;
    private SimpleAdapter adapter;
    protected static String u_account;
    protected static String u_name;
    protected static String u_identity;
    protected static String u_id="3";
    protected static String u_state="200";

    protected static List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
    protected static int[] to = new int[]{R.id.u_name,R.id.u_account,R.id.u_identity};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_list);
        listView = findViewById(R.id.student_item);
        new_user = findViewById(R.id.new_user);
        SDBThread dt = new SDBThread();
        Thread thread = new Thread(dt);
        thread.start();
        for(;u_state.equals("200");) {
        }
        //u_state="200";
        adapter = new SimpleAdapter(this,list,R.layout.admin_user_list_item,new String[]{"name","account","identity"},to);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView uname = (TextView)view.findViewById(R.id.u_name);
                TextView uaccount = (TextView)view.findViewById(R.id.u_account);
                u_name = uname.getText().toString();
                u_account = uaccount.getText().toString();
                u_identity = "学生";
                Intent intent = new Intent(getApplicationContext(),AdminModify.class);
                intent.putExtra("name",u_name);
                intent.putExtra("account",u_account);
                intent.putExtra("identity",u_identity);
                startActivity(intent);
                AdminStudentList.list.clear();
                AdminStudentList.this.finish();
            }
        });
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminStudentList.this,AdminAddUsers.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        AdminStudentList.this.finish();
        super.onBackPressed();
    }
}
class SDBThread implements Runnable {
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
            String sql ="select * from user where u_identity="+AdminStudentList.u_id+"";
            ResultSet rt = st.executeQuery(sql);
            AdminStudentList.list.clear();
            while (rt.next()){
                Map<String,String> keyvalue = new HashMap<String,String>();
                keyvalue.put("name",rt.getString("u_name"));
                keyvalue.put("account",rt.getString("u_id"));
                keyvalue.put("identity","学生");
                AdminStudentList.list.add(keyvalue);
            }
           /* conn.close();
            st.close();
            rt.close();*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        query();
        AdminStudentList.u_state="1";
    }

}

