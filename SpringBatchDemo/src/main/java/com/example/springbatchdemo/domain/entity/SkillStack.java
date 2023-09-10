package com.example.springbatchdemo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skill_stacks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_stack_id", nullable = false)
	private Long skillStackId;

	@Column(name = "skill_stack_name")
	private String skillStackName;

	@Column(name = "skill_stack_search_count")
	private Long skillStackSearchCount;
}
