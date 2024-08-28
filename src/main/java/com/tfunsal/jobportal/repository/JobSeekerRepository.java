package com.tfunsal.jobportal.repository;

import com.tfunsal.jobportal.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker , Long> {
}
