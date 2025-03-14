package com.yk.logistic.repository.item;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
public interface ItemRepository extends JpaRepository<Item,Long>{
	List<Item> findBySellerId(Long memberId);
}
