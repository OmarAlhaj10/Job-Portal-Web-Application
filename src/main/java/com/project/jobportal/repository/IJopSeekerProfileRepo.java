package com.project.jobportal.repository;

import com.project.jobportal.entity.JopSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJopSeekerProfileRepo extends JpaRepository<JopSeekerProfile, Integer> {
}
