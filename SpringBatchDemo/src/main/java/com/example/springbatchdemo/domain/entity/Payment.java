package com.example.springbatchdemo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id", nullable = false)
	private Long paymentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_sheet_id")
	private OrderSheet orderSheet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_abstract_account_id")
	private UserAbstractAccount sellerAbstractAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer_abstract_account_id")
	private UserAbstractAccount consumerAbstarctAccount;
}
