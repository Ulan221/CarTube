package com.cartube.service;

import com.cartube.entity.Car;
import com.cartube.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> findCarById(final Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void saveCar(final Car car) {
        carRepository.save(car);
    }

    public void deleteCarById(final Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> searchByBrand(final String carBrand) {
        return carRepository.findByCarBrandContainingIgnoreCase(carBrand);
    }
}
