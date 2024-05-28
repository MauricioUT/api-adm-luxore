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
@Table(name = "C_CATEGORIES")
public class CCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "category", nullable = false, length = 45)
    private String category;

    @Size(max = 80)
    @NotNull
    @Column(name = "tag", nullable = false, length = 80)
    private String tag;

    @NotNull
    @Column(name = "ordering", nullable = false)
    private Integer ordering;

    @OneToMany(mappedBy = "idCategory")
    private Set<TProperty> tProperties = new LinkedHashSet<>();

}