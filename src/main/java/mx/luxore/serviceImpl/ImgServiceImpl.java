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
    public ResponseEntity<?> updateImg(int id, List<ImgReqDto> img) throws IOException {
        String path = PATH + (new Date()).getTime() + "/";
        Optional<TProperty> property = propertyRepositoryWrapper.findById(id);
        if (property.isEmpty())
            throw new ResourceNotFoundException("Propiedades", " ", " ", new Throwable("updateImg()"), this.getClass().getName());
        File theDir = new File(path);
        boolean dirCreated = false;
        if (!theDir.exists())
            dirCreated = theDir.mkdirs();

        if (dirCreated) {
            for (int i = 0; i < img.size(); i++) {
                if (img.get(i).isMain()) {
                    if (!img.get(i).getImagePath().isBlank())
                        newImage(img.get(i), path, "lista", property.get(), false, SIZE_MAIN, SIZE_MAIN);
                    else
                        newImage(img.get(i), path, "lista", property.get(), true, SIZE_MAIN, SIZE_MAIN);
                } else {
                    if (img.get(i).getId() != null && img.get(i).getId() != 0 && !img.get(i).getImagePath().isBlank())
                        newImage(img.get(i), path, img.get(i).getId().toString(), property.get(), false, SIZE, SIZE);
                    else
                        newImage(img.get(i), path, String.valueOf(i + 1), property.get(), true, SIZE, SIZE);
                }
            }
        }
        ImagesUtils.dropDirectory(theDir);

        return null;
    }

    /**
     * que pasa si genero una imagen nueva, siempre le pondrá el 1 de inicio hay que cambiar por el id de la db  en el nombre de url publica
     **/
    protected void newImage(ImgReqDto img, String path, String name, TProperty property, boolean isNew, int width, int height) throws IOException {
        String output = ImagesUtils.convertWebP(img.getFile(), path, name, width, height);
        String fileName = "pruebaJAVA/" + property.getIdCategory().getCategory() + "/propiedad_" + property.getId() + "/" + name + ".webp";
        String publicUrl = CloudStorageUtils.uploadFile(fileName, output);
        if (isNew && !img.isMain()) {
            TImage image = new TImage();
            image.setIdPrperties(property);
            image.setImagePath(publicUrl);
            image.setCreatedOn(Instant.now());
            imageRepositoryWrapper.save(image);
        }
        if (isNew && img.isMain()) {
            property.setMainImage(publicUrl);
            propertyRepositoryWrapper.save(property);
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
