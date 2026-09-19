package com.huynhvohoangnam.giuaky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.huynhvohoangnam.giuaky.model.Product;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ProductService() {
        // Dữ liệu mẫu (Virtual data)
        save(new Product(null, "Laptop Dell Inspiron 15", 15500000.0, "Laptop học tập văn phòng"));
        save(new Product(null, "iPhone 15 Pro Max", 29990000.0, "Điện thoại Apple cao cấp"));
        save(new Product(null, "Bàn phím cơ DareU", 850000.0, "Bàn phím cơ giá rẻ sinh viên"));
        save(new Product(null, "Chuột Logitech G102", 420000.0, "Chuột chơi game chính xác"));
    }

    public List<Product> getAll() {
        return products;
    }

    public List<Product> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return products;
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    public Product getById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idCounter.getAndIncrement());
            products.add(product);
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(product.getId())) {
                    products.set(i, product);
                    return product;
                }
            }
        }
        return product;
    }

    public boolean delete(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }
}
