package mx.luxore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "C_COLONIES")
public class CColony {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_city", nullable = false)
    private CCity idCity;

    @Size(max = 5)
    @NotNull
    @Column(name = "postal_code", nullable = false, length = 5)
    private String postalCode;

    @Size(max = 100)
    @NotNull
    @Column(name = "colony", nullable = false, length = 100)
    private String colony;

    @OneToMany(mappedBy = "idColony")
    private Set<TProperty> tProperties = new LinkedHashSet<>();

}