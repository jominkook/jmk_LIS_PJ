package com.yk.logistic.domain;

import com.querydsl.core.annotations.QueryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
//@QueryEntity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
/*public class Supplier {
	
	@Id
	@GeneratedValue
	@Column(name = "suplier_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	@OneToOne
	@JoinColumn(name = "deliver_id")
	private Delivery delivery;

}*/
