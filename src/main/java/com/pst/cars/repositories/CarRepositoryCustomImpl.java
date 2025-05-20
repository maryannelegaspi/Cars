package com.pst.cars.repositories;

import com.pst.cars.models.Car;
import com.pst.cars.models.FilterRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CarRepositoryCustomImpl implements CarRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Car> searchCars (FilterRequest filter) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Car> queryBuilder = builder.createQuery(Car.class);
        Root<Car> root = queryBuilder.from(Car.class);

        var predicates = buildFilterPredicates(builder, root, filter);
        queryBuilder.where(builder.and(predicates.toArray(new Predicate[0])));

        Optional.ofNullable(buildOrder(builder, root, SortRequest.by(filter)))
                .ifPresent(queryBuilder::orderBy);

        return entityManager.createQuery(queryBuilder)
                .getResultList();
    }

    private List<Predicate> buildFilterPredicates (
            CriteriaBuilder builder,
            Root<Car> root,
            FilterRequest filter
    ) {

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNoneBlank(filter.getBrand())) {
            predicates.add(
                    builder.equal(
                            builder.lower(root.get("brand")),
                            filter.getBrand().toLowerCase(Locale.ROOT)
                    )
            );
        }

        if (Objects.nonNull(filter.getReleaseDate())) {
            predicates.add(builder.equal(root.get("yearModel"), filter.getReleaseDate()));
        }

        return predicates;
    }

    private Order buildOrder(
            CriteriaBuilder builder,
            Root<Car> root,
            SortRequest sort
    ) {

        var columnOptional = Optional.ofNullable(sort.column());
        if (columnOptional.isEmpty()) return null;

        try {
            var column = columnOptional.get();
            var path = root.get(column);
            return switch (sort.order()) {
                case ASC -> builder.asc(path);
                case DESC -> builder.desc(path);
            };
        } catch (PathElementException e) {
            return null; // Meaning there is no such column so we return null
        }
    }

    record SortRequest(String column, com.pst.cars.models.Order order) {
        static SortRequest by(FilterRequest filter) {
            return new SortRequest(filter.getSort(), filter.getOrder());
        }
    }

}
