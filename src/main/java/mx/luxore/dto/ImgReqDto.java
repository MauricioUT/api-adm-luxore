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
public class ImgReqDto extends ImgDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 646762761913947814L;

    private String file;

    private boolean isMain;
}
