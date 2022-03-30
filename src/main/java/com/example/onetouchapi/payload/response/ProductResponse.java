package com.example.onetouchapi.payload.response;

import com.example.onetouchapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<Product> product;
    private String newProductId;
}
