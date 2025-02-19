package com.yk.logistic.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yk.logistic.domain.item.Item;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}