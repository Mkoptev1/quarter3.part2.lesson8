package com.geekbrains.controllers;

import com.geekbrains.entities.Product;
import com.geekbrains.repositories.specifications.ProductSpecifications;
import com.geekbrains.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductServiceImpl productServiceImpl;
    private String templateFolder = "product/";

    @Autowired
    public void setProductServiceImpl(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    // Список товаров
    // http://localhost:8189/app/product
    @GetMapping()
    public String getWareList
    (
        Model model,
        Pageable pageable,
        @RequestParam(name = "pages", required = false, defaultValue = "0") int pageNumber,
        @RequestParam(name = "size", required = false, defaultValue = "5") int pageSize,
        @RequestParam(name = "name", required = false) String filterWareName,
        @RequestParam(name = "maxPrice", required = false) Long filterWareMaxPrice,
        @RequestParam(name = "minPrice", required = false) Long filterWareMinPrice
    ) {
        // Пагинация начинается с 0 элемента
        if (pageNumber > 0) {
            pageNumber --;
        }
        Specification<Product> spec = Specification.where(null);
        spec = spec.and(ProductSpecifications.getWareByName(filterWareName));
        spec = spec.and(ProductSpecifications.priceGreaterThanOrEq(filterWareMinPrice));
        spec = spec.and(ProductSpecifications.priceLesserThanOrEq(filterWareMaxPrice));

        Pageable firstPageWithFiveElements = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC,"id"));
        Page<Product> productList = productServiceImpl.showAllWare(spec, firstPageWithFiveElements);

        model.addAttribute("page", productList);
        model.addAttribute("pages", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("name", filterWareName);
        model.addAttribute("maxPrice", filterWareMaxPrice);
        model.addAttribute("minPrice", filterWareMinPrice);

        List<String> pageCountList = Arrays.asList("1", "5", "10", "30", "50");
        model.addAttribute("pageCountList", pageCountList);

        return templateFolder + "product-list";
    }

    // Форма редактирования товара
    // http://localhost:8189/app/product/edit-product
    @GetMapping("/edit-product")
    public String editWare(Model model, @RequestParam(name = "id", required = false, defaultValue = "0") Long id) {
        Product editWare = new Product();

        // Редактирование существующего товара
        if (id > 0) {
            editWare = productServiceImpl.get(id);
        }
        model.addAttribute("product", editWare);
        return templateFolder + "edit-product";
    }

    // Сохранение товара
    // http://localhost:8189/app/product/save-product
    @PostMapping("/save-product")
    public String saveWare(@ModelAttribute("product") Product product) {
        productServiceImpl.save(product);
        return "redirect:/product/";
    }

    // Удаление товара
    // http://localhost:8189/app/product/del-product/1
    @GetMapping("/del-product/{id}")
    public String delWare(@PathVariable(name = "id") Long id) {
        productServiceImpl.delete(id);
        return "redirect:/product/";
    }
}