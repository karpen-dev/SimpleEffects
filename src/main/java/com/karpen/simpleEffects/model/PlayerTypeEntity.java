package com.karpen.simpleEffects.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "player_types")
public class PlayerTypeEntity {

    @Id
    @Column(name = "playerName", length = 100)
    private String playerName;

    @Column(name = "type", nullable = false, length = 50)
    private String type;
}
