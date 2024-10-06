package com.project.jobportal.services;

import com.project.jobportal.entity.*;
import com.project.jobportal.repository.IJobPostActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JobPostActiviyService {

    private final IJobPostActivityRepo jobPostActivityRepo;

    @Autowired
    public JobPostActiviyService(IJobPostActivityRepo jobPostActivityRepo) {
        this.jobPostActivityRepo = jobPostActivityRepo;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepo.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter){

        List<IRecruiterJob> recruiterJobsDtos = jobPostActivityRepo.getRecruiterJobs(recruiter);
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        for (IRecruiterJob rec : recruiterJobsDtos) {
            JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
            JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(), rec.getJob_title(), loc, comp));
        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(int id) {
        return jobPostActivityRepo.findById(id).orElseThrow(()-> new RuntimeException("Job not, found"));
    }

    public List<JobPostActivity> getAll() {
        return jobPostActivityRepo.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate)?jobPostActivityRepo.searchWithoutDate(job, location, remote, type):jobPostActivityRepo.search(job, location, remote, type, searchDate);
    }
}
