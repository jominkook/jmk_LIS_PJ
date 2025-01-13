package com.yk.logistic.repository;

import com.yk.logistic.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JpaItemRepository extends JpaRepository<Item, Long> {
}