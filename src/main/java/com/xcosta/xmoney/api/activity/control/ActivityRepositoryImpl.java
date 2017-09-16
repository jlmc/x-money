package com.xcosta.xmoney.api.activity.control;

import com.xcosta.xmoney.api.activity.entity.Activity;
import com.xcosta.xmoney.api.activity.entity.ActivityFilter;
import com.xcosta.xmoney.api.activity.entity.ActivitySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

public class ActivityRepositoryImpl implements ActivityRepositoryQuery {

    @PersistenceContext
    EntityManager manager;

    @Override
    public Page<Activity> search(ActivityFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Activity> criteria = builder.createQuery(Activity.class);
        Root<Activity> root = criteria.from(Activity.class);
        root.fetch("person", JoinType.LEFT);
        root.fetch("category", JoinType.LEFT);

        Predicate[] predicates = this.toPredicates(filter, builder, root);

        criteria.distinct(true).where(predicates);


        TypedQuery<Activity> query = manager.createQuery(criteria)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());

        // List<T> content, Pageable pageable, long total
        long total = this.total(filter);
        return new PageImpl<>(query.getResultList(), pageable, total);

    }

    @Override
    public Page<ActivitySummary> summary(ActivityFilter filter, Pageable pageable) {


        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<ActivitySummary> criteria = builder.createQuery(ActivitySummary.class);
        Root<Activity> root = criteria.from(Activity.class);
        //root.fetch("person", JoinType.LEFT);
        //root.fetch("category", JoinType.LEFT);

        criteria.select(
                builder.construct(ActivitySummary.class,
                        root.get("code"),
                        root.get("description"),
                        root.get("observation"),
                        root.get("payday"),
                        root.get("maturity"),
                        root.get("value"),
                        root.get("type"),
                        root.get("category").get("name"),
                        root.get("person").get("name")));

        Predicate[] predicates = this.toPredicates(filter, builder, root);
        criteria.distinct(true).where(predicates);


        TypedQuery<ActivitySummary> query = manager.createQuery(criteria);
        addPagination(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
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

    private long total(ActivityFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Activity> root = criteria.from(Activity.class);
        root.join("person", JoinType.LEFT);
        root.join("category", JoinType.LEFT);

        criteria.select(builder.count(root.get("code"))).where(this.toPredicates(filter, builder, root));

        TypedQuery<Long> query = manager.createQuery(criteria);

        // List<T> content, Pageable pageable, long total
        return query.getSingleResult();
    }

    private void addPagination(TypedQuery<?> query, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstRecordOfCurrentPage = currentPage * pageSize;

        query.setFirstResult(firstRecordOfCurrentPage);
        query.setMaxResults(pageSize);
    }
}
