package com.karpen.simpleEffects.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "player_types")
public class PlayerTypeEntity {

    @Id
    @Column(name = "player_uuid", length = 36)
    private String playerUUid;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Version
    private int version;
}
