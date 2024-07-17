package com.example.elasticsearch.repository;

import com.example.elasticsearch.document.CarModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CarRepository extends ElasticsearchRepository<CarModel, String> {
    List<CarModel> findAllByBrand(String brand);
}
