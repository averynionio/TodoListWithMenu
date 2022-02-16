package com.example.todolistmenu.bean;

public class RecordBean {
    private String id;
    private String todoRecord;
    private String time;

    public RecordBean() {
    }

    public RecordBean(String id, String todoRecord, String time) {
        this.todoRecord = todoRecord;
        this.id = id;
        this.time = time;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getTodoRecord() { return this.todoRecord; }

    public void setTodoRecord(String todoRecord) { this.todoRecord = todoRecord; }

    public String getTime() { return this.time; }

    public void setTime(String time) { this.time = time; }
}
