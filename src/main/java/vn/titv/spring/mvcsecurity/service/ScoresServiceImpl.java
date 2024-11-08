package vn.titv.spring.mvcsecurity.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.titv.spring.mvcsecurity.dao.ScoresRepository;
import vn.titv.spring.mvcsecurity.dao.StudentRepository;
import vn.titv.spring.mvcsecurity.entity.Scores;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScoresServiceImpl implements ScoresService{


    private ScoresRepository scoresRepository;
    private EntityManager entityManager;

    private StudentRepository studentRepository;


    @Autowired
    public ScoresServiceImpl(ScoresRepository scoresRepository, EntityManager entityManager, StudentRepository studentRepository) {
        this.scoresRepository = scoresRepository;
        this.entityManager = entityManager;
        this.studentRepository = studentRepository;
    }


    @Override
    @Transactional
    public Scores updateScores(Scores scores) {
        return scoresRepository.saveAndFlush(scores);
    }

    @Override
    @Transactional
    public Scores addScores(Scores scores) {
        return scoresRepository.save(scores);
    }

    @Override
    public List<Scores> getAllScores() {
        return scoresRepository.findAll();
    }

    @Override
    @Transactional
    public Scores getScoresById(int id) {
        return scoresRepository.getById(id);
    }

    @Override
    @Transactional
    public void deleteScoresById(int id) {
        scoresRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Long> getUsedStudentIds() {
        String queryStr = "SELECT DISTINCT s.student.id FROM Scores s"; // Truy vấn để lấy danh sách các ID đã được sử dụng
        Query query = entityManager.createQuery(queryStr);
        return query.getResultList();
    }


    public List<Double> findAllAverageScores() {
        Query query = entityManager.createQuery("SELECT AVG(s.diemToan + s.diemLy + s.diemHoa + s.diemSinh + s.diemVan + s.diemSu + s.diemDia + s.diemAnh) / 8 FROM Scores s");
        return query.getResultList();
    }

    @Override
    public Map<Integer, Scores> getScoresByStudentIds(List<Integer> studentIds) {
        List<Scores> scoresList = scoresRepository.findAllById(studentIds);
        return scoresList.stream().collect(Collectors.toMap(score -> score.getStudent().getId(), score -> score));
      }

    @Override
    public Scores getScoresByStudentId(Integer studentId) {
        return scoresRepository.findByStudentId(studentId);
    }

    @Override
    public List<Scores> getScoresSortedByAverage() {
        List<Scores> allScores = getAllScores(); // Lấy tất cả các điểm
        return allScores.stream()
                .sorted(Comparator.comparingDouble(Scores::getDiemTrungBinh).reversed()) // Sắp xếp giảm dần theo điểm trung bình
                .collect(Collectors.toList());
    }

}

