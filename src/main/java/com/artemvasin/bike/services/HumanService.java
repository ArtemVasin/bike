package com.artemvasin.bike.services;

import com.artemvasin.bike.model.Bike;
import com.artemvasin.bike.model.Human;
import com.artemvasin.bike.repositories.HumanRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HumanService {

    private HumanRepository humanRepository;

    @Autowired
    public HumanService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }


    public List<Human> findAll() {

        return humanRepository.findAll();
    }

    public Human findById(int id) {
        Optional<Human> foundHuman = humanRepository.findById(id);
        return foundHuman.orElse(null);
    }

    @Transactional
    public void saveHuman(Human newHuman) {
        humanRepository.save(newHuman);
    }

    @Transactional
    public void updateHuman(int id, Human upHuman) {
        upHuman.setId(id);
        humanRepository.save(upHuman);
    }
    @Transactional
    public void deleteHuman(int id) {
        humanRepository.deleteById(id);
    }

    public Optional<Human> getHumanByFullName(String fullName) {

        return humanRepository.findByFullName(fullName);
    }

    public List<Bike> getBikeByHumanId(int id) {
        Optional<Human> human = humanRepository.findById(id);
        if (human.isPresent()) {
            Hibernate.initialize(human.get().getBikes());
            human.get().getBikes().forEach(bike -> {
                long timeDifference = Math.abs(bike.getTakenAt().getTime() - new Date().getTime());
                // 86400000 милисекунд = 1 сутки
                if (timeDifference > 86400000)
                    bike.setExpired(true);
            });
            return human.get().getBikes();
        } else {
            return Collections.emptyList();
        }
    }
}
