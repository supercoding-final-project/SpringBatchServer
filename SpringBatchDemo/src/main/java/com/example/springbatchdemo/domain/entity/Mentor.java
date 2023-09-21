package com.example.springbatchdemo.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mentors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "mentorId", callSuper = false)
@Builder
public class Mentor extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_id", nullable = false)
	private Long mentorId;

	@OneToMany(mappedBy = "mentor")
	private List<MentorSkillStack> mentorSkillStacks = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "introduction")
	private String introduction;

	@Column(name = "company")
	private String company;

	@Column(name = "searchable")
	private Boolean searchable;

	@Column(name = "current_duty")
	private String currentDuty;

	@Column(name = "current_period")
	private String currentPeriod;

	@OneToMany(mappedBy = "mentor")
	private List<MentorCareer> mentorCareerList = new ArrayList<>();

	@Column(name = "star")
	private Float star;

	public void updateStar(Float star) {
		this.star = star;
	}
}
