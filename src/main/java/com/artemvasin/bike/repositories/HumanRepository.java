package com.artemvasin.bike.repositories;

import com.artemvasin.bike.model.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HumanRepository extends JpaRepository <Human,Integer> {

    Optional <Human>  findByFullName(String fullName);
}
