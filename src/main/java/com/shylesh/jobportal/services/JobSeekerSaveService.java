package com.shylesh.jobportal.services;


import com.shylesh.jobportal.entity.JobPostActivity;
import com.shylesh.jobportal.entity.JobSeekerProfile;
import com.shylesh.jobportal.entity.JobSeekerSave;
import com.shylesh.jobportal.repository.JobSeekerSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    @Autowired
    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }

    @Transactional
    public void addNew(JobSeekerSave jobSeekerSave) {
       
        jobSeekerSaveRepository.save(jobSeekerSave);
    }
}
