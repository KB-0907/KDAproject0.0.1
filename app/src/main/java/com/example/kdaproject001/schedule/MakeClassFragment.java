package com.example.kdaproject001.schedule;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kdaproject001.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class MakeClassFragment extends BottomSheetDialogFragment {
    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_OK_EDIT = 2;
    public static final int RESULT_OK_DELETE = 3;
    private Context context;
    private Button deleteBtn;
    private Button submitBtn;
    private EditText subjectEdit;
    private EditText classroomEdit;
    private EditText professorEdit;
    private Spinner daySpinner;
    private TextView startTv;
    private TextView endTv;
    private int mode;
    private Schedule schedule;
    private int editIdx;

    private View view;
    private ClassBottomSheetListener classListener;

    public MakeClassFragment(Context context){
        this.context = context;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ClassBottomSheetListener){
            classListener = (ClassBottomSheetListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        classListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_make_class, container, false);
        init();
        return view;
    }


    private void init(){
        this.context = context;
        deleteBtn = view.findViewById(R.id.delete_class_fg);
        submitBtn = view.findViewById(R.id.submit_class_fg);
        subjectEdit = view.findViewById(R.id.subject_edit_fg);
        classroomEdit = view.findViewById(R.id.classroom_edit_fg);
        professorEdit = view.findViewById(R.id.professor_edit_fg);
        daySpinner = view.findViewById(R.id.day_spinner);
        startTv = view.findViewById(R.id.start_time);
        endTv = view.findViewById(R.id.end_time);
        deleteBtn.setOnClickListener(onMakeClassListener);

        //set the default time
        schedule = new Schedule();
        schedule.setStartTime(new Time(10,0));
        schedule.setEndTime(new Time(13,30));

        checkMode();
        initView();
    }

    private void checkMode(){
        Bundle bundle = getArguments();
        mode = bundle.getInt("mode");

        if(mode == ScheduleActivity.REQUEST_EDIT){
            deleteBtn.setText("삭제");
            loadScheduleData();
        } else if (mode == ScheduleActivity.REQUEST_ADD){
            deleteBtn.setText("취소");
        }
    }
    private void initView(){
        submitBtn.setOnClickListener(onMakeClassListener);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schedule.setDay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), listener, schedule.getStartTime().getHour(), schedule.getStartTime().getMinute(), false);
                dialog.show();

            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTv.setText(hourOfDay + ":" + minute);
                    schedule.getStartTime().setHour(hourOfDay);
                    schedule.getStartTime().setMinute(minute);
                }
            };
        });
        endTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(getActivity(),listener,schedule.getEndTime().getHour(), schedule.getEndTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTv.setText(hourOfDay + ":" + minute);
                    schedule.getEndTime().setHour(hourOfDay);
                    schedule.getEndTime().setMinute(minute);
                }
            };
        });
    }

    View.OnClickListener onMakeClassListener = v -> {
        switch (v.getId()){
            case R.id.submit_class_fg:
                if (mode == ScheduleActivity.REQUEST_ADD){
                    inputDataProcessing();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    //you can add more schedules to ArrayList
                    schedules.add(schedule);
                    classListener.onSubmitClassClick(schedules);
                    dismiss();
                } else if (mode == ScheduleActivity.REQUEST_EDIT) {
                    inputDataProcessing();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    schedules.add(schedule);
                    classListener.onEditClassClick(schedules, editIdx);
                    dismiss();
                }
                break;
            case R.id.delete_class_fg:
                if(mode == ScheduleActivity.REQUEST_EDIT){
                    classListener.onDeleteClassClick(editIdx);
                    loadScheduleData();
                    dismiss();
                } else if (mode == ScheduleActivity.REQUEST_ADD){
                    dismiss();
                }
                break;
        }
    };

    private void loadScheduleData(){
        Bundle bundle = getArguments();
        editIdx = bundle.getInt("idx");
        ArrayList<Schedule> schedules = (ArrayList<Schedule>) bundle.getSerializable("schedules");
        schedule = schedules.get(0);
        subjectEdit.setText(schedule.getClassTitle());
        classroomEdit.setText(schedule.getClassPlace());
        professorEdit.setText(schedule.getProfessorName());
        daySpinner.setSelection(schedule.getDay());
    }


    private void inputDataProcessing(){
        schedule.setClassTitle(subjectEdit.getText().toString());
        schedule.setClassPlace(classroomEdit.getText().toString());
        schedule.setProfessorName(professorEdit.getText().toString());
    }

}
