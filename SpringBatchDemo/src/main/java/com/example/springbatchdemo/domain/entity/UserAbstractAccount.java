package com.example.springbatchdemo.domain.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "abstractAccountId", callSuper = false)
@Table(name = "user_abstract_accounts")
public class UserAbstractAccount extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "abstract_account_id")
    private Long abstractAccountId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "paymoney")
    private Long paymoney;
}
