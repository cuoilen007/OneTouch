package com.example.onetouchapi.controller;

import com.example.onetouchapi.model.Category;
import com.example.onetouchapi.model.Product;
import com.example.onetouchapi.payload.request.ProductRequest;
import com.example.onetouchapi.payload.request.ProductUpdateRequest;
import com.example.onetouchapi.payload.response.MessageResponse;
import com.example.onetouchapi.payload.response.ProductResponse;
import com.example.onetouchapi.repository.IProductRepository;
import com.example.onetouchapi.service.CloudinaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private IProductRepository productRepository;

    private Map<String, String> options = new HashMap<>();

    @Value("${javadocfast.cloudinary.folder.image}")
    private String imagefront;

    @Value("${javadocfast.cloudinary.folder.image}")
    private String imageback;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/list")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "") String keyword
    ) {

        Pageable pageable = PageRequest.of(
                page - 1, pageSize,
                "asc".equals(sortDir) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending()
        );

        Page<Product> products = "".equals(keyword) ?
                productRepository.findAll(pageable) :
                productRepository.findProductsByNameLike("%" + keyword + "%", pageable);

        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductRequest productRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMyyyyhhmmss");

        MultipartFile multipartFile = productRequest.getMultipartFile();
        if (multipartFile != null) {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            if (bufferedImage == null) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Invalid image"));
            }
        }

        Map<String, String> options = new HashMap<>();
        options.put("folder", imagefront);
        options.put("folder", imageback);

        Map result = cloudinaryService.upload(multipartFile, options);
        String linkImgFront = result.get("url").toString();
        String nameImgFront = result.get("public_id").toString();

        String id = "P" + formatter.format(new Date());
        String name = productRequest.getName();
        Long price = productRequest.getPrice();
        String description = productRequest.getDescription();
        Category category = objectMapper.readValue(productRequest.getCategoryId().toString(), Category.class);

        Product product = new Product(id, name, price,description,linkImgFront,nameImgFront,nameImgFront,nameImgFront, category);

        productRepository.save(product);

        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductUpdateRequest productRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        String id = productRequest.getId();

        Product product = productRepository.findById(id).get();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        Category category = objectMapper.readValue(productRequest.getCategoryId().toString(), Category.class);
        product.setCategoryId(category);


        Map<String, String> options = new HashMap<>();
        options.put("folder", imagefront);
        options.put("folder", imageback);

        MultipartFile multipartFile = productRequest.getMultipartFile();
        if (multipartFile != null) {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            if (bufferedImage == null) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Invalid image"));
            }

            Map result = cloudinaryService.upload(multipartFile, options);

            if (multipartFile != null) {
                String linkImgBack = result.get("url").toString();
                String nameImgBack = result.get("public_id").toString();
                String linkImgFront = result.get("url").toString();
                String nameImgFront = result.get("public_id").toString();
                product.setLinkImageBack(linkImgBack);
                product.setLinkImageFront(linkImgFront);
                product.setNameImageBack(nameImgBack);
                product.setNameImageFront(nameImgFront);
            }
        }

        productRepository.save(product);

        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProductById(@PathVariable String id) {
        Product product = productRepository.findById(id).get();
        if (product.getDeletedAt() == null) {
            product.setDeletedAt(new Date());
        } else {
            product.setDeletedAt(null);
        }
        productRepository.save(product);
        return new ResponseEntity(product, HttpStatus.OK);
    }


    @GetMapping("/showAll")
    public ResponseEntity<?> showAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/saleoff")
    public ResponseEntity<?> getProductsBySaleOff(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "list") String saleOff  // list: show product saleOff !== null, add: === null
    ) {
        Date date = new Date();
        Timestamp timeNow = new Timestamp(date.getTime());

        Pageable pageable = PageRequest.of(
                page - 1, pageSize,
                "asc".equals(sortDir) ? Sort.by(sortField).descending() : Sort.by(sortField).ascending()
        );

        Page<Product> products = productRepository.findAll(pageable);


        return ResponseEntity.ok(products);
    }
}
