package mx.luxore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_PROPERTIES")
public class TProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "mainImage", nullable = false, length = 100)
    private String mainImage;

    @NotNull
    @Column(name = "price", nullable = false, precision = 16, scale = 2)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPropertyType", nullable = false)
    private CPropertyType idPropertyType;

    @Size(max = 250)
    @NotNull
    @Column(name = "title", nullable = false, length = 250)
    private String title;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Size(max = 100)
    @NotNull
    @Column(name = "addres", nullable = false, length = 100)
    private String addres;

    @NotNull
    @Column(name = "garage", nullable = false)
    private Byte garage;

    @NotNull
    @Column(name = "carsNumber", nullable = false)
    private Integer carsNumber;

    @NotNull
    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @NotNull
    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @Size(max = 3)
    @NotNull
    @Column(name = "bathrooms", nullable = false, length = 3)
    private String bathrooms;

    @NotNull
    @Column(name = "postedYear", nullable = false)
    private Instant postedYear;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCity", nullable = false)
    private CCity idCity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idState", nullable = false)
    private CState idState;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idColony", nullable = false)
    private CColony idColony;

    @Size(max = 15)
    @NotNull
    @Column(name = "metersSurface", nullable = false, length = 15)
    private String metersSurface;

    @Size(max = 15)
    @NotNull
    @Column(name = "metersBuilded", nullable = false, length = 15)
    private String metersBuilded;

    @NotNull
    @Column(name = "featuredProperty", nullable = false)
    private Byte featuredProperty;

    @NotNull
    @Column(name = "updateOn", nullable = false)
    private Instant updateOn;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCategory", nullable = false)
    private CCategory idCategory;

    @Size(max = 15)
    @NotNull
    @Column(name = "zip", nullable = false, length = 15)
    private String zip;

    @NotNull
    @Column(name = "floors", nullable = false)
    private Integer floors;

    @NotNull
    @Lob
    @Column(name = "features", nullable = false)
    private String features;

    @NotNull
    @Column(name = "enable", nullable = false)
    private Byte enable;

    @Size(max = 100)
    @NotNull
    @Column(name = "latitude", nullable = false, length = 100)
    private String latitude;

    @Size(max = 100)
    @NotNull
    @Column(name = "longitude", nullable = false, length = 100)
    private String longitude;

    @Size(max = 100)
    @NotNull
    @Column(name = "pageAddress", nullable = false, length = 100)
    private String pageAddress;

    @Column(name = "comercialValue", precision = 16, scale = 2)
    private BigDecimal comercialValue;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Size(max = 100)
    @Column(name = "credit", length = 100)
    private String credit;

    @Column(name = "sold")
    private Boolean sold;

    @Lob
    @Column(name = "slugTitle")
    private String slugTitle;

    @OneToMany(mappedBy = "idProperty")
    private Set<TAmenitiesProperty> tAmenitiesProperties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPrperties")
    private Set<TImage> tImages = new LinkedHashSet<>();

}