package com.tfunsal.jobportal.service;

import com.tfunsal.jobportal.entity.JobSeeker;
import com.tfunsal.jobportal.entity.Recruiter;
import com.tfunsal.jobportal.entity.User;
import com.tfunsal.jobportal.repository.JobSeekerRepository;
import com.tfunsal.jobportal.repository.RecruiterRepository;
import com.tfunsal.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final RecruiterRepository recruiterRepository;

    @Autowired
    public UserService(UserRepository userRepository, JobSeekerRepository jobSeekerRepository, RecruiterRepository recruiterRepository) {
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterRepository = recruiterRepository;
    }

    public User create(User user){
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        User savedUser = userRepository.save(user);

        Long userTypeId = user.getUserTypeId().getId();
        if (userTypeId == 1){
            recruiterRepository.save(new Recruiter(savedUser));
        }else {
            jobSeekerRepository.save(new JobSeeker(savedUser));
        }
        return savedUser;
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
