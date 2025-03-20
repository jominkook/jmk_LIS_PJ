package com.yk.logistic.domain.categoryItem;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.item.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "categoryItem")
//JSON 직렬화 중 순환 참조 또는 깊은 객체 그래프로 인해 발생합니다. 
//이 문제는 Jackson이 객체를 JSON으로 직렬화할 때, 엔티티 간의 양방향 관계로 인해 무한 루프가 발생하거나
//, 중첩된 객체의 깊이가 1000을 초과했을 때 발생합니다.
public class CategoryItem {
    @Id
    @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference // 직렬화의 끝점
    private Category category;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonBackReference // 직렬화의 끝점
    private Item item;

    public CategoryItem(Category category, Item item) {
        this.category = category;
        this.item = item;
    }
}