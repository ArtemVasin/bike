package com.artemvasin.bike.services;

import com.artemvasin.bike.model.Bike;
import com.artemvasin.bike.model.Human;
import com.artemvasin.bike.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BikeService {

    private BikeRepository bikeRepository;

    @Autowired
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> findAll(boolean SortByYearOfCreation) {
        if (SortByYearOfCreation)
            return bikeRepository.findAll(Sort.by("yearOfCreation"));
        else return bikeRepository.findAll();
    }

    public List<Bike> findWithPagination(Integer page, Integer booksPerPage, boolean SortByYearOfCreation) {
        if (SortByYearOfCreation)
            return bikeRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfCreation"))).getContent();
        else
            return bikeRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Bike finById(int id) {
        Optional<Bike> foundBike = bikeRepository.findById(id);
        return foundBike.orElse(null);
    }

    public List<Bike> searchByTitle(String query) {
        return bikeRepository.findByNameBikeStartingWith(query);
    }

    @Transactional
    public void saveBike(Bike newBike) {
        bikeRepository.save(newBike);
    }

    @Transactional
    public void updateBike(int id, Bike upBike) {
        Bike bikeToBeUpdated = bikeRepository.findById(id).get();
        upBike.setId(id);
        upBike.setRenter(bikeToBeUpdated.getRenter());
        bikeRepository.save(upBike);
    }

    @Transactional
    public void deleteBike(int id) {
        bikeRepository.deleteById(id);
    }

    public Human getBikeHuman(int id) {
        return bikeRepository.findById(id).map(Bike::getRenter).orElse(null);
    }

    @Transactional
    public void release(int id) {
        bikeRepository.findById(id).ifPresent(
                bike -> {
                    bike.setRenter(null);
                    bike.setTakenAt(null);
                });
    }

    @Transactional
    public void assign(int id, Human selectHuman) {
        bikeRepository.findById(id).ifPresent(
                bike -> {
                    bike.setRenter(selectHuman);
                    bike.setTakenAt(new Date());
                });
    }

}
