package org.talent.bean;

import java.util.Date;

public class ProjectAssociate {
    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", hiringManagerId=" + hiringManagerId +
                ", hiringManagerName='" + hiringManagerName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", associateId=" + associateId +
                '}';
    }

    private int projectId;
    private String projectName;
    private int hiringManagerId;
    private String hiringManagerName;
    private Date startDate;
    private Date endDate;
    private int associateId;

    public String getHiringManagerName() {
        return hiringManagerName;
    }

    public void setHiringManagerName(String hiringManagerName) {
        this.hiringManagerName = hiringManagerName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAssociateId() {
        return associateId;
    }

    public void setAssociateId(int associateId) {
        this.associateId = associateId;
    }

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


}
