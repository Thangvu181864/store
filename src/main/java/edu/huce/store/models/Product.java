package edu.huce.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private Integer priceBuy;
    private Integer priceSell;
    private String dateManufacture;
    private String dateExpiration;
    private Integer vendorId;
    private Integer quantity;
    private String image;
    private String note;
    private Integer destroy;
}
