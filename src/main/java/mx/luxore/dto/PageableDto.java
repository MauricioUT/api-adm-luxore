package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageableDto extends CatalogDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7159948575169179902L;

    private int page;

    private int totalPage;

}
