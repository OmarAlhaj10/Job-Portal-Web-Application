package com.project.jobportal.services;

import com.project.jobportal.entity.JobPostActivity;
import com.project.jobportal.entity.JobSeekerSave;
import com.project.jobportal.entity.JopSeekerProfile;
import com.project.jobportal.repository.IJobSeekerSaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final IJobSeekerSaveRepo jobSeekerSaveRepo;

    @Autowired
    public JobSeekerSaveService(IJobSeekerSaveRepo jobSeekerSaveRepo) {
        this.jobSeekerSaveRepo = jobSeekerSaveRepo;
    }

    public List<JobSeekerSave> getCandidatesJobs(JopSeekerProfile userAccountId){
        return jobSeekerSaveRepo.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
        return jobSeekerSaveRepo.findByJob(job);
    }

    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepo.save(jobSeekerSave);
    }
}
