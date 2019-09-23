package com.geekbrains.utils;

import com.geekbrains.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.servlet.http.HttpServletRequest;

public class ProductsFilter {
    private Specification<Product> specification;
    private StringBuilder filtersString;

    public Specification<Product> getSpecification() {
        return specification;
    }

    public StringBuilder getFiltersString() {
        return filtersString;
    }

    public ProductsFilter(HttpServletRequest request) {
        filtersString = new StringBuilder();
        specification = Specification.where(null);

//        if (request.getParameter("word") != null && !request.getParameter("word").isEmpty()) {
//            specification = specification.and(ProductSpecifications.titleContains(request.getParameter("word")));
//            filtersString.append("&word=" + request.getParameter("word"));
//        }
//
//        if (request.getParameter("min") != null && !request.getParameter("min").isEmpty()) {
//            specification = specification.and(ProductSpecifications.priceGreaterThanOrEq(Double.parseDouble(request.getParameter("min"))));
//            filtersString.append("&min=" + request.getParameter("min"));
//        }
//
//        if (request.getParameter("max") != null && !request.getParameter("max").isEmpty()) {
//            specification = specification.and(ProductSpecifications.priceLesserThanOrEq(Double.parseDouble(request.getParameter("max"))));
//            filtersString.append("&max=" + request.getParameter("max"));
//        }
    }
}
