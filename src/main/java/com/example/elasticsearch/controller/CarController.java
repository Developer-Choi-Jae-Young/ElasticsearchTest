package com.example.elasticsearch.controller;

import com.example.elasticsearch.document.CarModel;
import com.example.elasticsearch.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarController {
    private final CarRepository carRepository;
    @PostMapping
    public void save(@RequestBody CarModel car) {
        carRepository.save(car);
    }

    @GetMapping("/{id}")
    public CarModel findById(@PathVariable String id) {
        return carRepository.findById(id).orElse(null);
    }

    @GetMapping
    public Iterable<CarModel> findAll() {
        return carRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        carRepository.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody CarModel carModel) {
        carRepository.save(carModel);
    }

    @GetMapping("/find")
    public List<CarModel> findAllByBrand(@RequestParam String brand) {
        return carRepository.findAllByBrand(brand);
    }
}
