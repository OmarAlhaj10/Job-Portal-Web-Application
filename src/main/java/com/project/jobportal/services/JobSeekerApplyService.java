package com.project.jobportal.services;

import com.project.jobportal.entity.JobPostActivity;
import com.project.jobportal.entity.JobSeekerApply;
import com.project.jobportal.entity.JopSeekerProfile;
import com.project.jobportal.repository.IJobSeekerApplyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyService {

    private final IJobSeekerApplyRepo jobSeekerApplyRepo;

    @Autowired
    public JobSeekerApplyService(IJobSeekerApplyRepo jobSeekerApplyRepo) {
        this.jobSeekerApplyRepo = jobSeekerApplyRepo;
    }

    public List<JobSeekerApply> getCandidatesJobs(JopSeekerProfile userAccountId) {
        return jobSeekerApplyRepo.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepo.findByJob(job);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepo.save(jobSeekerApply);
    }
}
