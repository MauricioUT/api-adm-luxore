package mx.luxore.utils;


import io.github.mojtabaJ.cwebp.CWebp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Component
@Slf4j
public class ImagesUtils {


    public static String convertWebP(String imgs, String path, String name, int width, int height) throws IOException {
        String base64Image = imgs.split(",")[1];
        byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
        FileOutputStream fos;
        String input = path + name + ".png";
        String output = path + name + ".webp";
        fos = new FileOutputStream(input);
        fos.write(imageBytes);
        fos.close();
        convertImageFile(input, 100, width, height, output);
        return output;
    }

    public static void convertImageFile(String input, int quality, int width, int height, String output) {
        CWebp cwebp = new CWebp()
                .input(input) // specify the input file.
                .quality(quality) //factor for RGB channels
                .resize(width, height) // resize the source to a rectangle with size width x height.
                .output(output); // specify the output WebP file "src/main/resources/546436333.webp"
        cwebp.execute();
    }

    public static void dropDirectory(File dir) throws IOException {
        log.info("Dropping directory {}", dir.getAbsolutePath());
        String[] entries = dir.list();
        assert entries != null;
        for (String s : entries) {
            File currentFile = new File(dir.getPath(), s);
            currentFile.delete();
        }
        FileUtils.deleteDirectory(dir);
    }
}
