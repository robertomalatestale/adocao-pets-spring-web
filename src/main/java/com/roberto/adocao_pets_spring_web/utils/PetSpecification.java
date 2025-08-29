package com.roberto.adocao_pets_spring_web.utils;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class  PetSpecification {

    public static Specification<Pet> hasOnlyAttribute(String attribute) {
        return (root, query, cb) -> {
            try {
                Path<?> path = getPath(root, attribute);
                Predicate notNull = cb.isNotNull(path);
                if (String.class.isAssignableFrom(path.getJavaType())) {
                    return cb.and(notNull, cb.notEqual(path.as(String.class), ""));
                }
                return notNull;
            } catch (IllegalArgumentException e) {
                return cb.disjunction();
            }
        };
    }

    public static Specification<Pet> hasAttributeAndValue(String atributo, String valor) {
        return (root, query, cb) -> {
            try {
                return cb.like(cb.lower(root.get(atributo).as(String.class)), "%" + valor.toLowerCase() + "%");
            } catch (IllegalArgumentException e) {
                return cb.conjunction();
            }
        };
    }

    private static Path<?> getPath(From<?, ?> root, String attribute) { //Para atributo Cidade
        Path<?> path = root;
        for (String part : attribute.split("\\.")) {
            path = path.get(part);
        }
        return path;
    }
}
