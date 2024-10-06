package com.project.jobportal.repository;

import com.project.jobportal.entity.JobPostActivity;
import com.project.jobportal.entity.JobSeekerSave;
import com.project.jobportal.entity.JopSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobSeekerSaveRepo extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JopSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);
}
