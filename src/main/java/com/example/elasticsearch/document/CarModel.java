package com.example.elasticsearch.document;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Document(indexName = "carindex")
public class CarModel {
    private String id;

    @Field(type = FieldType.Text, name = "model")
    private String model;

    @Field(type = FieldType.Integer, name = "year")
    private Integer yearOfManufacture;

    @Field(type = FieldType.Text, name = "brand")
    private String brand;

    @Field(type = FieldType.Nested, name = "owners")
    private List<OwnerModel> owners;
}
