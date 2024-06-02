package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogDto extends IdDto implements Serializable {

    private static final long serialVersionUID = 2482656810658307877L;

    private String description;
}
