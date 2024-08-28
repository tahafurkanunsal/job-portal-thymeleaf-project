package com.tfunsal.jobportal.repository;

import com.tfunsal.jobportal.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill , Long> {
}
