package vn.titv.spring.mvcsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.titv.spring.mvcsecurity.entity.Scores;
import vn.titv.spring.mvcsecurity.entity.Student;

import java.util.List;

@Repository
public interface ScoresRepository extends JpaRepository<Scores,Integer> {

    Scores findByStudentId(Integer studentId);
}
