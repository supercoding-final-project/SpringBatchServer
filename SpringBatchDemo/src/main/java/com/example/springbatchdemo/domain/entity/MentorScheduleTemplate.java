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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "mentor_schedule_templates")
public class MentorScheduleTemplate {

    @Id
    @Column(name = "schedule_template_id",nullable = false) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleTemplateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Column(name = "schedule_week")
    private String scheduleWeek;

    @Column(name = "valid_time")
    private Integer validTime;
}
