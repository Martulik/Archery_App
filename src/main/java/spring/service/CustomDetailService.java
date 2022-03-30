package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.entity.Student;
import spring.repositories.StudentRepository;

import java.util.Optional;

@Service
public class CustomDetailService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Student> d = studentRepository.findUserByEmail(login);
        if (d.isPresent()) {
            return studentRepository.findUserByEmail(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        return studentRepository.findUserByPhone_number(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
