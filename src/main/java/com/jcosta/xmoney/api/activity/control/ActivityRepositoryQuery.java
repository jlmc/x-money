package com.jcosta.xmoney.api.activity.control;

import com.jcosta.xmoney.api.activity.entity.Activity;
import com.jcosta.xmoney.api.activity.entity.ActivityFilter;
import com.jcosta.xmoney.api.activity.entity.ActivitySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityRepositoryQuery {
    /**
     * the name of this interface must be prefix as the normal repository.
     */

    Page<Activity> search(ActivityFilter filter, Pageable pageable);

    Page<ActivitySummary> searchSummary(ActivityFilter filter, Pageable pageable);
}
