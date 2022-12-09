package com.probodia.challengeservice.api.entity.challenge;


import com.probodia.challengeservice.api.entity.base.BaseTime;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CAUTION")
@ToString
public class Caution extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAUTION_ID")
    private Long id;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

}
