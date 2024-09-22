package org.example.aab.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Basetime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private Long kakaoId;
    @Column
    private String email;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean roles;

}
