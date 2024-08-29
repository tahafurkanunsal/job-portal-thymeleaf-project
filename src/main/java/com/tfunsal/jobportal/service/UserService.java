package com.tfunsal.jobportal.service;

import com.tfunsal.jobportal.entity.JobSeeker;
import com.tfunsal.jobportal.entity.Recruiter;
import com.tfunsal.jobportal.entity.User;
import com.tfunsal.jobportal.repository.JobSeekerRepository;
import com.tfunsal.jobportal.repository.RecruiterRepository;
import com.tfunsal.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final RecruiterRepository recruiterRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JobSeekerRepository jobSeekerRepository,
                       RecruiterRepository recruiterRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterRepository = recruiterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user){
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
            Long userId = user.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                Recruiter recruiter = recruiterRepository.findById(userId).orElse(new Recruiter());
                return recruiter;
            }else {
                JobSeeker jobSeeker = jobSeekerRepository.findById(userId).orElse(new JobSeeker());
                return jobSeeker;
            }
        }
        return null;
    }
}
