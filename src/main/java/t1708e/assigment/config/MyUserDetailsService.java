package t1708e.assigment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import t1708e.assigment.entity.Student;
import t1708e.assigment.repository.StudentRepository;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username).orElse(null);
        if (student == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        UserDetails user =
                User.builder()
                        .username(student.getEmail())
                        .password(student.getPassword())
                        .roles("USER")
                        .build();
//        UserDetails user1 = user;
        return user;
    }
}
