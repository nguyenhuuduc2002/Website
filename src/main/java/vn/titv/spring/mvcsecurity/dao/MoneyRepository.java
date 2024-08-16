package vn.titv.spring.mvcsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.titv.spring.mvcsecurity.entity.Money;
import vn.titv.spring.mvcsecurity.entity.Scores;

@Repository
public interface MoneyRepository extends JpaRepository<Money,Long> {
    public boolean existsByKhoiHoc(String khoiHoc);

    Money findByKhoiHoc(String khoiHoc);
}
