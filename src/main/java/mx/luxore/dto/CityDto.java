package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CityDto extends CatalogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6886394635440354624L;
    private int idState;
}
