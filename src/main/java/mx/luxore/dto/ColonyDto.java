package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ColonyDto extends CatalogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4711647709622821400L;
    @NotNull
    private int cityId;
    @NotNull
    @Size(max = 5)
    private String zip;
}
