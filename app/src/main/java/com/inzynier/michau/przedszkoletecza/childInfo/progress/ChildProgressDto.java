package com.inzynier.michau.przedszkoletecza.childInfo.progress;

public class ChildProgressDto {
    private final String category;
    private final String task;
    private final String grade;

    public ChildProgressDto(String category, String task, String grade) {
        this.category = category;
        this.task = task;
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public String getTask() {
        return task;
    }

    public String getGrade() {
        return grade;
    }
}
