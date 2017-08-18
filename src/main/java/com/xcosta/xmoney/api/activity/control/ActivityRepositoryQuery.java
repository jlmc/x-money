package com.xcosta.xmoney.api.activity.control;

import com.xcosta.xmoney.api.activity.entity.Activity;
import com.xcosta.xmoney.api.activity.entity.ActivityFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityRepositoryQuery {
    /**
     * the name of this interface must be prefix as the normal repository.
     */

    Page<Activity> search(ActivityFilter filter, Pageable pageable);
}
