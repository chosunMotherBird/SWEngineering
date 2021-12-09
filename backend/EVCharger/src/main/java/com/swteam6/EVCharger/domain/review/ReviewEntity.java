package com.swteam6.EVCharger.domain.review;

import com.swteam6.EVCharger.domain.charger.ChargerEntity;
import com.swteam6.EVCharger.domain.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "Review")
public class ReviewEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "CHARGER_ID")
    private ChargerEntity charger;
}
