package vn.titv.spring.mvcsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.titv.spring.mvcsecurity.dao.MoneyRepository;
import vn.titv.spring.mvcsecurity.entity.Money;

import java.util.List;

@Service
public class MoneyServiceImpl implements MoneyService{

    private MoneyRepository moneyRepository;


    @Autowired
    public MoneyServiceImpl(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    @Override
    public List<Money> getAllMoney() {
        return moneyRepository.findAll();
    }

    @Override
    public Money updateMoney(Money money) {
        return moneyRepository.saveAndFlush(money);
    }

    @Override
    public boolean existsByKhoiHoc(String khoiHoc) {
        return moneyRepository.existsByKhoiHoc(khoiHoc);
    }

    @Override
    public Money findByKhoiHoc(String khoiHoc) {
        return moneyRepository.findByKhoiHoc(khoiHoc);
    }
}
