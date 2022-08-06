package com.example.domain;

public class OldStudy {

    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;

    private String name;

    public OldStudy(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public OldStudy(int limit) {

        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야한다.");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
