package com.yk.logistic.domain;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@QueryEntity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {
	//재고id
	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	//재고이름
	private String name;
	
	//재고 원산지
	@Embedded
	private Address origin;
	
	//재고 가격
	private int price;
	
	//물품 재고
	private int stockQuantity;
	
	//공급자 객체
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memeber_id")
	private Member suplier;
	
	@OneToMany(mappedBy = "item")
	private List<CategoryItem> categories = new ArrayList<>();
	
	@Builder
	public Item(String name,Address origin,int price, int stockQuantity,Member supplier) {
		this.name = name;
		this.origin = origin;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.suplier = supplier;
	}
	
	public void updateItem(String name, Address origin, int price, int stockQuantity) {
		this.name = name;
		this.origin = origin;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
	
	public void addStockCount(int count) {
		stockQuantity += count;
	}

	public void removeStock(int quantity) {
		int restStock = this.stockQuantity - quantity;
		if(restStock < 0) {
			//throw new NotEnoughStockException("재고량 부족");
		}
		 this.stockQuantity = restStock;
	}
}
