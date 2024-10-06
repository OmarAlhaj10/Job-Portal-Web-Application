package com.project.jobportal.repository;

import com.project.jobportal.entity.JobPostActivity;
import com.project.jobportal.entity.JobSeekerApply;
import com.project.jobportal.entity.JopSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IJobSeekerApplyRepo extends JpaRepository<JobSeekerApply,Integer> {

    List<JobSeekerApply> findByUserId(JopSeekerProfile userId);
    List<JobSeekerApply> findByJob(JobPostActivity job);
}
