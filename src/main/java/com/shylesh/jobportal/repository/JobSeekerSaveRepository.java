package com.shylesh.jobportal.repository;

import com.shylesh.jobportal.entity.JobPostActivity;
import com.shylesh.jobportal.entity.JobSeekerProfile;
import com.shylesh.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);
    
    List<JobSeekerSave> findByJob(JobPostActivity job);

}
