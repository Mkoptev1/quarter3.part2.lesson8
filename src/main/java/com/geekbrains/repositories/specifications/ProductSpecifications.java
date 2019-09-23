package com.geekbrains.repositories.specifications;

import com.geekbrains.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecifications {
    public static Specification<Product> getWareByName(String filterWareName) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (filterWareName != null && !filterWareName.trim().isEmpty()) {
                    return criteriaBuilder.like(root.get("title"), "%"+ filterWareName + "%");
                } else {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
    }

    public static Specification<Product> priceGreaterThanOrEq(Long filterPrice) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (filterPrice != null && filterPrice > 0) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filterPrice);
                } else {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
    }

    public static Specification<Product> priceLesserThanOrEq(Long filterPrice) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (filterPrice != null && filterPrice > 0) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("price"), filterPrice);
                } else {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
    }
}