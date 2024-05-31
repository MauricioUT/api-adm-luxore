package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObjectPageableDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 4503830363218388492L;
    private long total;
    private List<?> list;
}
