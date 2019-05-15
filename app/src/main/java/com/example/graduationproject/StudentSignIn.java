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
import java.util.Calendar;
import java.util.TimeZone;

public class StudentSignIn extends AppCompatActivity {
    private TextView u_name;
    private Button signin;
    protected static String sin_state = "200";
    protected static String month;
    protected static String day;
    protected static String hour;
    protected static String minute;
    protected static String second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_in);
        init();
    }
    private void init(){
        u_name = findViewById(R.id.account);
        signin = findViewById(R.id.signin);
        u_name.setText(StudentMainActivity.s_name);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                second = String.valueOf(cal.get(Calendar.SECOND));

                BDBThread dt = new BDBThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;sin_state.equals("200");){

                }
                Toast.makeText(StudentSignIn.this, "签到成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StudentSignIn.this,StudentMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
class BDBThread implements Runnable {
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
            String sql = "insert into signin (u_id,e_id,month,day,hour,minute,second) " +
                    "values( "+StudentMainActivity.u_id+", "+StudentMainActivity.e_id+", " +
                    "'"+StudentSignIn.month+"', '"+StudentSignIn.day+"','"+StudentSignIn.hour+"'," +
                    "'"+StudentSignIn.minute+"','"+StudentSignIn.second+"')";
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
        StudentSignIn.sin_state="1";
    }

}
