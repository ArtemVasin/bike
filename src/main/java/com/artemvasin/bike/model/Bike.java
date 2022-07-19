package com.artemvasin.bike.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "bike")
public class Bike {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "мназвание велосипеда не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название велосипеда должно быть от 2 до 50 символов длиной")
    @Column(name = "name_bike")
    private String nameBike;

    @Min(value = 2000, message = "Год должен быть больше, чем 2000")
    @Column(name = "year_of_creation")
    private int yearOfCreation;

    @ManyToOne
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private Human renter;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired; // Hibernate не будет замечать этого поля

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", nameBike='" + nameBike + '\'' +
                ", yearOfCreation=" + yearOfCreation +
                ", renter=" + renter +
                ", takenAt=" + takenAt +
                ", expired=" + expired +
                '}';
    }

    public Bike() {
    }

    public Bike(String nameBike, int yearOfCreation) {
        this.nameBike = nameBike;
        this.yearOfCreation = yearOfCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameBike() {
        return nameBike;
    }

    public void setNameBike(String nameBike) {
        this.nameBike = nameBike;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public Human getRenter() {
        return renter;
    }

    public void setRenter(Human renter) {
        this.renter = renter;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
