package com.yk.logistic.repository.item;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.yk.logistic.domain.item.Item;
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
	List<Item> findBySellerId(Long memberId);

}
