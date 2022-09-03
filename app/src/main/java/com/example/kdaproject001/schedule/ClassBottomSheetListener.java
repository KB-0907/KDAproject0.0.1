package com.example.kdaproject001.schedule;

import java.util.ArrayList;

public interface ClassBottomSheetListener {
    void onSubmitClassClick(ArrayList<Schedule> schedules);
    void onEditClassClick(ArrayList<Schedule> schedules, int idx);
    void onDeleteClassClick(int idx);
}
