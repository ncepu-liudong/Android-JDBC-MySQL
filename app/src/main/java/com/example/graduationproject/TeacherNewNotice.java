package com.example.graduationproject;

import android.content.Intent;
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
import java.util.Calendar;
import java.util.TimeZone;

public class TeacherNewNotice extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private Button new_notice;
    protected static String n_title;
    protected static String n_content;
    protected static String month;
    protected static String day;
    protected static String hour;
    protected static String minute;
    protected static String n_state = "200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_new_notice);
        init();
    }
    private void init(){
        title = findViewById(R.id.n_title);
        content = findViewById(R.id.n_content);
        new_notice = findViewById(R.id.new_notice);
        new_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_title = title.getText().toString();
                n_content = content.getText().toString();
                Calendar cal;
                cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                month = String.valueOf(cal.get(Calendar.MONTH)+1);
                day = String.valueOf(cal.get(Calendar.DATE));
                if (cal.get(Calendar.AM_PM) == 0)
                    hour = String.valueOf(cal.get(Calendar.HOUR));
                else
                    hour = String.valueOf(cal.get(Calendar.HOUR)+12);
                minute = String.valueOf(cal.get(Calendar.MINUTE));
                DDBThread dt = new DDBThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;n_state.equals("200");){
                }
                Toast.makeText(TeacherNewNotice.this, "公告发布成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherNewNotice.this,TeacherMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
class DDBThread implements Runnable {
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
            String sql = "insert into notices (e_id,u_id,n_title,n_content,month,day,hour,minute) " +
                    "values( "+TeacherMainActivity.e_id+", "+TeacherMainActivity.u_id1+", '"+TeacherNewNotice.n_title+"',"+
                    "'"+TeacherNewNotice.n_content+"', '"+TeacherNewNotice.month+"','"+TeacherNewNotice.day+"'," +
                    "'"+TeacherNewNotice.hour+"','"+TeacherNewNotice.minute+"')";
            st.execute(sql);
            conn.close();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        insert();
        TeacherNewNotice.n_state="1";
    }

}
