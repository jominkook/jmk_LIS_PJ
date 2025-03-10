package com.yk.logistic.service.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;

@Component
public class ValidationCheck {

  

    //유효한 상품인지 검증
    public Item getItem(Optional<Item> item) {
        return item.orElseThrow(() -> new IllegalArgumentException("없는 상품 입니다"));
    }

    //유효한 주문인지 검증
    /*public Order getOrder(Optional<Order> order){
        return order.orElseThrow(() -> new IllegalArgumentException("없는 주문 입니다"));
    }*/


}
