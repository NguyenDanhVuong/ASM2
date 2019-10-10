package t1708e.assigment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t1708e.assigment.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);
}
