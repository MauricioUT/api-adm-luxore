package mx.luxore.serviceImpl;

import mx.luxore.dto.DefaultMessage;
import mx.luxore.dto.ImgDto;
import mx.luxore.dto.ImgReqDto;
import mx.luxore.entity.TImage;
import mx.luxore.entity.TProperty;
import mx.luxore.exception.ResourceNotFoundException;
import mx.luxore.repositorywrapper.TImageRepositoryWrapper;
import mx.luxore.repositorywrapper.TPropertyRepositoryWrapper;
import mx.luxore.service.ImgService;
import mx.luxore.utils.MagicImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImgServiceImpl implements ImgService {

    @Autowired
    private TPropertyRepositoryWrapper propertyRepositoryWrapper;

    @Autowired
    private TImageRepositoryWrapper imageRepositoryWrapper;

    @Override
    public ResponseEntity<?> updateImg(int id, List<ImgReqDto> img) throws IOException {
        List<String> files = img.stream().map(ImgReqDto::getFile).collect(Collectors.toList());
        File theDir = MagicImage.convertWebP(files, 700, 700);
        MagicImage.dropDirectory(theDir);
        Optional<TProperty> property = propertyRepositoryWrapper.findById(id);
        if (property.isEmpty())
            throw new ResourceNotFoundException("Propiedades", " ", " ", new Throwable("updateImg()"), this.getClass().getName());

        img.forEach(i -> {
            if (i.getId() != null) {
                //edita una imagen existente
                // resize
                // se elimina en gstorage y se actualiza por la nueva
                // se edita la imagen actual y gurda en db
            } else {
                //agrega una imagen nueva
                //resize
                //guardar en gstorage
                //guardar path de gstorage
            }
        });
        return null;
    }

    @Override
    public ResponseEntity<?> deleteImg(int id, ImgDto img) {
        Optional<TProperty> property = propertyRepositoryWrapper.findById(id);
        if (property.isEmpty())
            throw new ResourceNotFoundException("Propiedades", " ", " ", new Throwable("deleteImg()"), this.getClass().getName());

        TImage image = property.get().getTImages().stream().filter((prop) -> prop.getId() == img.getId()).findAny().orElseThrow(() ->
                new ResourceNotFoundException("Imagen", " ", " ", new Throwable("deleteImg()"), this.getClass().getName())
        );

        // eliminar de google Storage
        imageRepositoryWrapper.delete(image);

        return new ResponseEntity<>(new DefaultMessage(img.getId().toString(), HttpStatus.OK.value()), HttpStatus.OK);
    }
}
