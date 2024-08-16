package vn.titv.spring.mvcsecurity.service;

import vn.titv.spring.mvcsecurity.entity.Money;
import vn.titv.spring.mvcsecurity.entity.Scores;

import java.util.List;

public interface MoneyService {
    public List<Money> getAllMoney();

    public Money updateMoney(Money money);

    public boolean existsByKhoiHoc(String khoiHoc);

    public Money findByKhoiHoc(String khoiHoc);
}
