package mx.luxore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_IMAGES")
public class TImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPrperties", nullable = false)
    private TProperty idPrperties;

    @Size(max = 700)
    @NotNull
    @Column(name = "imagePath", nullable = false, length = 700)
    private String imagePath;

    @NotNull
    @Column(name = "createdOn", nullable = false)
    private Instant createdOn;

}