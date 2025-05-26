package org.talent.bean;

import java.util.Date;

public class Project {
    private int projectId;
    private String projectName;
    private int hiringManagerId;
    private Date assignmentDate;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getHiringManagerId() {
        return hiringManagerId;
    }

    public void setHiringManagerId(int hiringManagerId) {
        this.hiringManagerId = hiringManagerId;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", hiringManagerId=" + hiringManagerId +
                ", assignmentDate=" + assignmentDate +
                '}';
    }

}
