package com.shylesh.jobportal.services;

import com.shylesh.jobportal.entity.JobSeekerProfile;
import com.shylesh.jobportal.entity.RecruiterProfile;
import com.shylesh.jobportal.entity.Users;
import com.shylesh.jobportal.repository.JobSeekerProfileRepository;
import com.shylesh.jobportal.repository.RecruiterProfileRepository;
import com.shylesh.jobportal.repository.UsersRepository;
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
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository theUsersRepository,
                        JobSeekerProfileRepository thejobSeekerProfileRepository,
                        RecruiterProfileRepository therecruiterProfileRepository, PasswordEncoder passwordEncoder) {
        usersRepository = theUsersRepository;
        jobSeekerProfileRepository = thejobSeekerProfileRepository;
        recruiterProfileRepository = therecruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users theUser) {
        theUser.setActive(true);
        theUser.setRegistrationDate(new Date(System.currentTimeMillis()));
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
        Users savedUser = usersRepository.save(theUser);

        int userId = theUser.getUserTypeId().getUserTypeId();

        if (userId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }

    public Optional<Users> getUserByEmail(String theEmail) {
        return usersRepository.findByEmail(theEmail);
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            int userId = users.getUserId();

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
                return recruiterProfile;
            } else {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }

        }

        return null;
    }

    public Users getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return users;

        }
        return null;
    }

    public Users findByEmail(String currentUsername) {
        return usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
