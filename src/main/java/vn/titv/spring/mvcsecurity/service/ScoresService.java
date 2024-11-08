package vn.titv.spring.mvcsecurity.service;

import org.springframework.stereotype.Service;
import vn.titv.spring.mvcsecurity.entity.Scores;
import vn.titv.spring.mvcsecurity.entity.Student;

import java.util.List;
import java.util.Map;


public interface ScoresService {

    public Scores updateScores(Scores scores);

    public Scores addScores(Scores scores);

    public List<Scores> getAllScores();

    public Scores getScoresById(int id);

    public void deleteScoresById(int id);

    public List<Long> getUsedStudentIds();

    public Map<Integer,Scores> getScoresByStudentIds(List<Integer> studentIds);


    public Scores getScoresByStudentId(Integer studentId);

    public List<Scores> getScoresSortedByAverage();
}
