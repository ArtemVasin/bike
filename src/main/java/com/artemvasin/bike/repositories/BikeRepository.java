package com.artemvasin.bike.repositories;

import com.artemvasin.bike.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    List<Bike> findByNameBikeStartingWith(String query);
}
