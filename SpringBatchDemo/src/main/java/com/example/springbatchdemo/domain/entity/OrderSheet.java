package com.example.springbatchdemo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
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
@EqualsAndHashCode(of = "orderSheetId", callSuper = false)
@Table(name = "order_sheets")
public class OrderSheet extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_sheet_id")
    private Long orderSheetId;
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;
    @Column(name = "total_price")
    private Integer totalPrice;
    @Column(name = "is_completed")
    private Boolean isCompleted;
    @Column(name = "is_reviewed", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean is_reviewed;
    @Version
    private Integer version;    // 낙관적 락
}
