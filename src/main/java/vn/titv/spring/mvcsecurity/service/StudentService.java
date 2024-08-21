package vn.titv.spring.mvcsecurity.service;



import org.springframework.security.crypto.password.PasswordEncoder;
import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.entity.User;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudents();

    public Student getStudentById(int id);

    public Student addStudent(Student student);

    public Student updateStudent(Student student);

    public void deleteStudentById(int id);

    List<Student> findStudentsByClass(String className);

    public void addStudent(User user);

    public Student findByEmail(String email);

    public void deleteStudentByEmail(String email);

}
