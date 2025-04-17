package com.shylesh.jobportal.repository;

import com.shylesh.jobportal.entity.JobPostActivity;
import com.shylesh.jobportal.entity.JobSeekerApply;
import com.shylesh.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {


    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);

}
