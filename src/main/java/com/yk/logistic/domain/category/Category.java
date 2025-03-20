package com.yk.logistic.domain.category;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yk.logistic.domain.categoryItem.CategoryItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "category")
//JSON 직렬화 중 순환 참조 또는 깊은 객체 그래프로 인해 발생합니다. 
//이 문제는 Jackson이 객체를 JSON으로 직렬화할 때, 엔티티 간의 양방향 관계로 인해 무한 루프가 발생하거나
//, 중첩된 객체의 깊이가 1000을 초과했을 때 발생합니다.
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference // 직렬화의 시작점
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore // 순환 참조 방지
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore // 순환 참조 방지
    private List<Category> child = new ArrayList<>();

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}
