package com.example.kdaproject001.todo;

import com.example.kdaproject001.board.PostInfo;

public interface ItemTouchHelperListener {
    boolean onItemMove(int from_position, int to_position);
    void onItemSwipe(int position);
}