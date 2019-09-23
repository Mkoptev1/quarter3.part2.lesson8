package com.geekbrains.repositories.specifications;

import com.geekbrains.entities.Client;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;

public class ClientSpecifications {
    public static Specification<Client> getClientByName(String filterClientName) {
        return new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                if (filterClientName != null && !filterClientName.trim().isEmpty()) {
                    return criteriaBuilder.equal(root.get("name"), filterClientName);
                } else {
                    return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                }
            }
        };
    }
}