package com.example.springbatchdemo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "userSocialInfoId", callSuper = false)
@Table(name = "user_social_infos")
public class UserSocialInfo extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_social_info_id")
    private Long userSocialInfoId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "social_id")
    private Long socialId;
    @Column(name = "social_platform_name")
    private String socialPlatformName;
}
