package com.onesquad.adapter.persistence;
import fr.onesquad.domain.user.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;
}
