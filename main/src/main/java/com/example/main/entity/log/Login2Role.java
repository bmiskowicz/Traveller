package com.example.main.entity.log;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema="security",name="login_roles")
public class Login2Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="login_id", nullable = false)
    private Login login;

    @ManyToOne(optional = false)
    @JoinColumn(name="role_id", nullable = false)
    private Role role;
}