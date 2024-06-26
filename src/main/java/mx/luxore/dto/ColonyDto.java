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
public class ColonyDto extends PageableDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4711647709622821400L;
    private int idCity;
    private String postalCode;

}
