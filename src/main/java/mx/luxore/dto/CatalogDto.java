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
public class CatalogDto implements Serializable {

    private static final long serialVersionUID = 2482656810658307877L;

    private int id;
    private String description;
    private int page;
    private int totalPage;
}
