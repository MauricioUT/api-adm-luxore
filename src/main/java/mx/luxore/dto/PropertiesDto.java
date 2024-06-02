package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PropertiesDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -3090862784955268045L;

    private int id;
    private String title;
    private boolean enabled;
    private boolean sold;
    private Instant postedYear;
    private BigDecimal price;
}
