package mx.luxore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImgDto extends IdDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4612439317658227580L;
    private String imagePath;
}
