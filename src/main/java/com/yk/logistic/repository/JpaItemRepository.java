package com.yk.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yk.logistic.domain.Item;

public interface JpaItemRepository extends JpaRepository<Item,Long> {

}
