package com.probodia.challengeservice.api.entity.challenge;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.probodia.challengeservice.api.entity.base.BaseTime;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHALLENGE")
@ToString
public class Challenge extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_ID")
    private Long id;

    @Column(name = "OFFICIAL", length = 1, columnDefinition = "BOOLEAN")
    @NotNull
    private Boolean official;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

    @Column(name = "TOTAL_CNT")
    @NotNull
    private Integer totalCnt;

    @Column(name = "EARN_POINT")
    @NotNull
    private Integer earnPoint;

    @Column(name = "ST_DATE",length = 20)
    @NotNull
    @Size(max = 20)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "Asia/Seoul")
    private String stDate;

    @Column(name = "EN_DATE",length = 20)
    @NotNull
    @Size(max = 20)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "Asia/Seoul")
    private String enDate;

    @Column(name = "FREQUENCY")
    @NotNull
    @Embedded
    private Frequency frequency;

    @Column(name = "PARTICIPANT_CNT")
    @NotNull
    private Integer participantCnt;

    @Column(name = "ACTIVE", length = 1, columnDefinition = "BOOLEAN")
    @NotNull
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "CHALLENGE_ID")
    @BatchSize(size = 100)
    private Set<Caution> cautions = new HashSet<>();


}
