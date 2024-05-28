package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DefaultMessage implements Serializable {

    private static final long serialVersionUID = 5045866970974046880L;

    private String defaultMessage;
    private int status;

}
