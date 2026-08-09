package com.BuilderBadge.HHframeGenerator.repository;

import com.BuilderBadge.HHframeGenerator.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository  extends JpaRepository<Badge,String> {

}
