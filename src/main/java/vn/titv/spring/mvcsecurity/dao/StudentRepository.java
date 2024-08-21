package vn.titv.spring.mvcsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import vn.titv.spring.mvcsecurity.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByLop(String lop);

    Student findByEmail(String email);

    void deleteStudentByEmail(String email);

}
