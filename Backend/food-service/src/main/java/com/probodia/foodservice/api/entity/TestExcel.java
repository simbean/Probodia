package com.probodia.foodservice.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEST_EXCEL")
public class TestExcel {

    @Id
    private String id;

    @Column(name = "STUDENT_NAME")
    private String studentName;

}
