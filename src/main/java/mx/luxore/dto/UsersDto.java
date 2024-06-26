package mx.luxore.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link mx.luxore.entity.TUsers}
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsersDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 8207974166093264768L;

    private Integer id;
    private String username;
    private String password;
    private String email;
    private Boolean enable;
    private Set<String> roles;
}