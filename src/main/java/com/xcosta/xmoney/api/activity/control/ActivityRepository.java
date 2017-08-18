package com.xcosta.xmoney.api.activity.control;

import com.xcosta.xmoney.api.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryQuery {}
