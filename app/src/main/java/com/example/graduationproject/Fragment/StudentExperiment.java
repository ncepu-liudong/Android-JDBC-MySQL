package com.example.graduationproject.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.graduationproject.LogonActivity;
import com.example.graduationproject.R;
import com.example.graduationproject.StudentExperimentMember;
import com.example.graduationproject.StudentExperimentScore;
import com.example.graduationproject.StudentMainActivity;
import com.example.graduationproject.StudentSignIn;
import com.example.graduationproject.TeacherMainActivity;
import com.example.graduationproject.tool.IntentTools;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentExperiment extends Fragment {
    private TextView e_id;
    private TextView e_name;
    private TextView e_teacher;
    private ImageButton signin;
    private ImageButton e_member;
    private ImageButton s_score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_experiment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }
    private void init(View view){
        e_id = view.findViewById(R.id.e_id1);
        e_name = view.findViewById(R.id.e_name);
        e_teacher = view.findViewById(R.id.e_teacher);
        signin = view.findViewById(R.id.signin);
        e_member = view.findViewById(R.id.e_member);
        s_score = view.findViewById(R.id.s_score);
        e_id.setText(StudentMainActivity.e_id1);
        e_teacher.setText(StudentMainActivity.e_teacher);
        e_name.setText(StudentMainActivity.e_name);
        listener();
    }
    private void listener(){
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), StudentSignIn.class,null);
            }
        });
        e_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), StudentExperimentMember.class,null);
            }
        });
        s_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), StudentExperimentScore.class,null);
            }
        });
    }
}
