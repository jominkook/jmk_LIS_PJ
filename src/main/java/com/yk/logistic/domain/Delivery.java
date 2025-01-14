package com.yk.logistic.domain;

import com.querydsl.core.annotations.QueryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@QueryEntity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery {
	@Id
	@GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
//	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
//	Order order;
	
	@Embedded
	Address address;
	
	@Enumerated(EnumType.STRING)
	DeliveryStatus status;
	
//	LocalDateTime sendDate;
//	
//	LocalDateTime receiveDate;
	
//	@Builder
//	public Delivery(Address adress,Order order) {
//		this.address = address;
//		if(order != null) {
//			changeOrder(order);
//		}
//	}
//	
//	public void changeOrder(Order order) {
//		this.order = order;
//		order.changeDelivery(this);
//	}
//	
//	public void changeStatus(DeliveryStatus status) {
//		this.status = status;
//	}
	

}
