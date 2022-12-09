package com.probodia.challengeservice.api.entity.challenge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.DataOutputStream;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARTICIPANT")
@Builder
@ToString
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARTICIPANT_ID")
    private Long id;

//    @Column(name = "CONTENT")
//    @NotNull
//    //참가금, 현재는 사용 X
//    private Integer fee;

    @Column(name = "USER_ID")
    @NotNull
    private String userId;

    @Column(name = "ACHIEVEMENT")
    @NotNull
    private Integer achievement;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "PARTICIPANT_ID")
    @BatchSize(size = 100)
    private Set<Activity> activities = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_ID")
    @JsonIgnore
    private Challenge challenge;

}
