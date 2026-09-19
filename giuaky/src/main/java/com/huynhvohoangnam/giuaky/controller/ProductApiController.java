package com.huynhvohoangnam.giuaky.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.huynhvohoangnam.giuaky.model.Product;
import com.huynhvohoangnam.giuaky.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    // 1. Lấy tất cả hoặc tìm kiếm: GET /api/products hoặc GET /api/products?keyword=laptop
    @GetMapping
    public List<Product> getAll(@RequestParam(required = false) String keyword) {
        return productService.search(keyword);
    }

    // 2. Lấy chi tiết: GET /api/products/1
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product p = productService.getById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    // 3. Thêm mới: POST /api/products
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        product.setId(null);
        Product created = productService.save(product);
        return ResponseEntity.ok(created);
    }

    // 4. Cập nhật: PUT /api/products/1
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        if (productService.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        return ResponseEntity.ok(productService.save(product));
    }

    // 5. Xóa: DELETE /api/products/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
