package com.xcosta.xmoney.api.activity.control;

import com.xcosta.xmoney.api.activity.entity.Activity;
import com.xcosta.xmoney.api.activity.entity.ActivityFilter;

import java.util.List;

public interface ActivityRepositoryQuery {
    /**
     * the name of this interface must be prefix as the normal repository.
     */

    List<Activity> search(ActivityFilter filter);
}
