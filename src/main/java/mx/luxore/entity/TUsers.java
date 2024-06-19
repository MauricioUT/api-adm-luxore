package mx.luxore.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_USERS")
public class TUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @NotNull
    @NotBlank
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @NotNull
    @NotBlank
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Email
    @NotNull
    @NotBlank
    @Size(max = 100)
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "idUser")
    private Set<TUserRoles> tUserRoles = new LinkedHashSet<>();
}
