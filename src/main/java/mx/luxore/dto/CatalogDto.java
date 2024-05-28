package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogDto implements Serializable {

    private static final long serialVersionUID = 2482656810658307877L;

    private int id;
    @NotNull
    @Size(max = 100)
    private String description;
}
