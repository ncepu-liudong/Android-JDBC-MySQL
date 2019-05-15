package com.example.graduationproject.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.graduationproject.R;
import com.example.graduationproject.TeacherMainActivity;
import com.example.graduationproject.TeacherNewNotice;
import com.example.graduationproject.TeacherUserInformation;
import com.example.graduationproject.tool.IntentTools;

public class Teachernotice extends Fragment {
    private TextView notice;
    private Button new_notice;
    private static String message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teachernotice, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }
    private void init(View view){
        notice = view.findViewById(R.id.notice);
        notice.setText(TeacherMainActivity.message);


        new_notice = view.findViewById(R.id.new_notice);
        new_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), TeacherNewNotice.class,null);
            }
        });
    }

}
