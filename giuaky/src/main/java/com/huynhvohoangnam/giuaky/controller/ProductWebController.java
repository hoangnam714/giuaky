package com.huynhvohoangnam.giuaky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.huynhvohoangnam.giuaky.model.Product;
import com.huynhvohoangnam.giuaky.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductWebController {

    private final ProductService productService;

    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    // Dashboard kiêm Quản lý sản phẩm: Thống kê, Tìm kiếm, Bảng danh sách & Form sửa
    @GetMapping({"/dashboard", "/products"})
    public String dashboard(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false) Long editId,
                            HttpSession session, Model model) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }

        model.addAttribute("totalProducts", productService.getAll().size());
        model.addAttribute("products", productService.search(keyword));
        model.addAttribute("keyword", keyword);

        // Nếu bấm Sửa -> load dữ liệu lên Form, ngược lại để trống
        Product product = (editId != null) ? productService.getById(editId) : new Product();
        model.addAttribute("product", product != null ? product : new Product());

        return "dashboard";
    }

    // Lưu sản phẩm (thêm mới hoặc sửa)
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/login";
        productService.save(product);
        return "redirect:/dashboard";
    }

    // Xóa sản phẩm
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/login";
        productService.delete(id);
        return "redirect:/dashboard";
    }
}
