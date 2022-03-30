package com.example.onetouchapi.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
    private String id;
    private String name;
    private String description;
    private long price;

    @JsonIgnoreProperties("products")
    private Object categoryId;
    private MultipartFile multipartFile;
}
