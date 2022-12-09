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
@Table(name = "ACTIVITY")
@ToString
public class Activity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_ID")
    private Long id;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

    @Column(name = "IMAGE_URL")
    @NotNull
    private String imageUrl;

}
