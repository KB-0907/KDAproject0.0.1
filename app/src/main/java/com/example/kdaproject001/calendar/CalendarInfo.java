package com.example.kdaproject001.calendar;

public class CalendarInfo {

    //받을 것 : 작성자 , 작성글
    String writer, writingID;
    long comCreated;

    public CalendarInfo(String writer, String writingID){
        this.writer = writer;
        this.writingID = writingID;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWritingID() {return writingID;}

    public void setWritingID(String writingID) {
        this.writingID = writingID;
    }

}



