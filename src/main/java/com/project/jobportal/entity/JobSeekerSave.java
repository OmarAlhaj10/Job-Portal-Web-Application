package com.project.jobportal.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "job"})
})
public class JobSeekerSave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobPostId")
    private JobPostActivity job;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private JopSeekerProfile userId;

    public JobSeekerSave() {
    }

    public JobSeekerSave(Integer id, JobPostActivity job, JopSeekerProfile userId) {
        this.id = id;
        this.job = job;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    public JopSeekerProfile getUserId() {
        return userId;
    }

    public void setUserId(JopSeekerProfile userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "JobSeekerSave{" +
                "id=" + id +
                ", jobPostId=" + job.toString() +
                ", userId=" + userId.toString() +
                '}';
    }
}
