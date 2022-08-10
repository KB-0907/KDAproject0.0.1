package com.example.kdaproject001.todo;

public class TodoInfo {
    private String title;
    private String deadline;
    private long created;
    private String todoID;
    private String sort;
    private String UID;

    public TodoInfo(String title, String deadline, long created, String todoID, String sort, String UID){
        this.title = title;
        this.deadline = deadline;
        this.created = created;
        this.todoID = todoID;
        this.sort = sort;
        this.UID = UID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getTodoID() {
        return todoID;
    }

    public void setTodoID(String todoID) {
        this.todoID = todoID;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
