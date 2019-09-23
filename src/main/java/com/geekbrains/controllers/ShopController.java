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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private ProductServiceImpl productServiceImpl;

    @Autowired
    public void setProductsService(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

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
        Page<Product> wareList = productServiceImpl.showAllWare(spec, firstPageWithFiveElements);

        model.addAttribute("page", wareList);
        model.addAttribute("pages", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("name", filterWareName);
        model.addAttribute("maxPrice", filterWareMaxPrice);
        model.addAttribute("minPrice", filterWareMinPrice);

        List<String> pageCountList = Arrays.asList("1", "5", "10", "30", "50");
        model.addAttribute("pageCountList", pageCountList);

        return "shop";
    }
}