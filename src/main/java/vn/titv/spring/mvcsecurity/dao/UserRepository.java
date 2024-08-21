package vn.titv.spring.mvcsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    void deleteByUsername(String username);
    User findByEmail(String email);

    boolean existsByUsername (String username);


}