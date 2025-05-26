package org.talent.bean;

import java.util.Date;

public class Associate {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    private int id;

    @Override
    public String toString() {
        return "Associate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availableDate=" + availableDate +
                ", lockStatus='" + lockStatus + '\'' +
                '}';
    }

    private String name;
    private Date availableDate;
    private String lockStatus;
}