package com.xcosta.xmoney.api.activity.control;

import com.xcosta.xmoney.api.activity.entity.Activity;
import com.xcosta.xmoney.api.activity.entity.ActivityFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

public class ActivityRepositoryImpl implements ActivityRepositoryQuery {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Activity> search(ActivityFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Activity> criteria = builder.createQuery(Activity.class);
        Root<Activity> root = criteria.from(Activity.class);
        root.fetch("person", JoinType.LEFT);
        root.fetch("category", JoinType.LEFT);

        Predicate[] predicates = this.toPredicates(filter, builder, root);

        criteria.distinct(true).where(predicates);

        return manager.createQuery(criteria).getResultList();
    }

    private Predicate[] toPredicates(ActivityFilter filter, CriteriaBuilder builder, Root<Activity> root) {
        if (filter == null) {
            return new Predicate[0];
        }

        List<Predicate> predicates = new LinkedList<>();

        if (!isEmpty(filter.getDescription())) {
            predicates.add(builder.like(
                    builder.lower(root.get("description")),
                    "%" + filter.getDescription().toLowerCase() + "%"));
        }

        if (filter.getMaturityFrom() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("maturity"), filter.getMaturityFrom()));
        }

        if (filter.getMaturityTo() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("maturity"), filter.getMaturityTo()));
        }

        return predicates.toArray(new Predicate[0]);
    }
}
