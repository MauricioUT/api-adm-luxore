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
public class PropertyReqDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1557043805084571493L;

    private int idCategory;
    private String wildCard;
    private int totalPage;
    private int page;
}
