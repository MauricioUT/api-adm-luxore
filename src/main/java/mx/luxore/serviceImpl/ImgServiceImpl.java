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
import mx.luxore.utils.CloudStorageUtils;
import mx.luxore.utils.ImagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ImgServiceImpl implements ImgService {

    @Autowired
    private TPropertyRepositoryWrapper propertyRepositoryWrapper;

    @Autowired
    private TImageRepositoryWrapper imageRepositoryWrapper;

    private static final int SIZE_MAIN = 220;
    private static final int SIZE = 700;
    private static final String PATH = "src/main/resources/tmpImg/";

    @Override
    public ResponseEntity<?> updateImg(int id, ImgReqDto img) throws IOException {
        String path = PATH + (new Date()).getTime() + "/";
        Optional<TProperty> property = propertyRepositoryWrapper.findById(id);
        if (property.isEmpty())
            throw new ResourceNotFoundException("Propiedades", " ", " ", new Throwable("updateImg()"), this.getClass().getName());
        File theDir = new File(path);
        boolean dirCreated = false;
        if (!theDir.exists())
            dirCreated = theDir.mkdirs();

        if (dirCreated) {
            if (img.isMain()) {
                if (!img.getImagePath().isBlank())
                    newImage(img, path, id + "_lista", property.get(), false, SIZE_MAIN, SIZE_MAIN);
                else
                    newImage(img, path, id + "_lista", property.get(), true, SIZE_MAIN, SIZE_MAIN);
            } else {
                if (img.getId() != null && img.getId() != 0 && !img.getImagePath().isBlank())
                    newImage(img, path, img.getId().toString(), property.get(), false, SIZE, SIZE);
                else
                    newImage(img, path, "", property.get(), true, SIZE, SIZE);

            }
        }
        ImagesUtils.dropDirectory(theDir);

        return new ResponseEntity<>(new DefaultMessage(String.valueOf(id), HttpStatus.OK.value()), HttpStatus.OK);
    }

    protected void newImage(ImgReqDto img, String path, String name, TProperty property, boolean isNew, int width, int height) throws IOException {
        TImage image = null;
        if (isNew && !img.isMain()) {
            image = new TImage();
            image.setIdPrperties(property);
            image.setImagePath("");
            image.setCreatedOn(Instant.now());
            imageRepositoryWrapper.save(image);
            name = image.getId().toString();
        }

        String output = ImagesUtils.convertWebP(img.getFile(), path, name, width, height);
        String fileName = "pruebaJAVA/" + property.getIdCategory().getShortName() + "/propiedad_" + property.getId() + "/" + name + ".webp";
        String publicUrl = CloudStorageUtils.uploadFile(fileName, output);
        if (isNew && img.isMain()) {
            property.setMainImage(publicUrl);
            property.setEnable(true);
            propertyRepositoryWrapper.save(property);
        }
        if (isNew && !img.isMain() && image != null) {
            image.setImagePath(publicUrl);
            imageRepositoryWrapper.save(image);
        }

    }


    @Override
    public ResponseEntity<?> deleteImg(int id, ImgDto img) {
        Optional<TProperty> property = propertyRepositoryWrapper.findById(id);

        if (property.isEmpty())
            throw new ResourceNotFoundException("Propiedades", " ", " ", new Throwable("deleteImg()"), this.getClass().getName());

        TImage image = property.get().getTImages().stream().filter((prop) -> Objects.equals(prop.getId(), img.getId())).findAny().orElseThrow(() ->
                new ResourceNotFoundException("Imagen", " ", " ", new Throwable("deleteImg()"), this.getClass().getName())
        );

        String fileName = "pruebaJAVA/" + property.get().getIdCategory().getCategory() + "/propiedad_" + property.get().getId() + "/" + image.getId() + ".webp";

        try {
            CloudStorageUtils.deleteFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageRepositoryWrapper.delete(image);

        return new ResponseEntity<>(new DefaultMessage(img.getId().toString(), HttpStatus.OK.value()), HttpStatus.OK);
    }
}
