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
import com.example.graduationproject.R;
import com.example.graduationproject.TeacherExperimentCheck;
import com.example.graduationproject.TeacherExperimentMember;
import com.example.graduationproject.TeacherMainActivity;
import com.example.graduationproject.TeacherSignIn;
import com.example.graduationproject.tool.IntentTools;

public class TeacherExperiment extends Fragment {
    private TextView e_id;
    private TextView e_name;
    private TextView e_teacher;
    private ImageButton signin;
    private ImageButton e_member;
    private ImageButton e_check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_experiment, container, false);
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
        e_check = view.findViewById(R.id.e_check);
        e_id.setText(TeacherMainActivity.e_id1);
        e_teacher.setText(TeacherMainActivity.e_teacher);
        e_name.setText(TeacherMainActivity.e_name);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), TeacherSignIn.class,null);
            }
        });
        e_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), TeacherExperimentMember.class,null);
            }
        });
        e_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), TeacherExperimentCheck.class,null);
            }
        });
    }
}
