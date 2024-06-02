package mx.luxore.dto;

import lombok.*;
import mx.luxore.entity.CColony;
import mx.luxore.entity.CState;
import mx.luxore.entity.TProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link TProperty}
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TPropertyDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5358941325821821380L;

    private Integer id;
    private String mainImage;
    private BigDecimal price;
    private String title;
    private String description;
    private String addres;
    private Byte garage;
    private Integer carsNumber;
    private Integer rooms;
    private Integer bedrooms;
    private String bathrooms;
    private Instant postedYear;
    private String metersSurface;
    private String metersBuilded;
    private Byte featuredProperty;
    private Instant updateOn;
    private String zip;
    private Integer floors;
    private String features;
    private Byte enable;
    private String latitude;
    private String longitude;
    private String pageAddress;
    private BigDecimal comercialValue;
    private String notes;
    private String credit;
    private Boolean sold;
    private String slugTitle;
    private IdDto idPropertyType;
    private IdDto idCity;
    private IdDto idState;
    private IdDto idColony;
    private IdDto idCategory;
    private List<CatalogDto> amenities;
    private List<ImgDto> images;

}