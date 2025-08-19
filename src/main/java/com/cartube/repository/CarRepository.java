package com.cartube.repository;

import com.cartube.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByCarBrandContainingIgnoreCase(String carBrand);
}
