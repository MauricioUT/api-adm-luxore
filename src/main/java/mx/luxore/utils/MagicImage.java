package mx.luxore.utils;


import io.github.mojtabaJ.cwebp.CWebp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class MagicImage {

    private static final String PATH = "src/main/resources/tmpImg/";


    public static File convertWebP(List<String> imgs, int width, int height) throws IOException {
        int name = 0;
        File theDir = null;
        for (String img : imgs) {
            name++;
            String base64Image = img.split(",")[1];
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
            FileOutputStream fos;
            String time = "" + (new Date()).getTime() + "/";
            theDir = new File(PATH + time);
            boolean dirCreated = false;
            if (!theDir.exists()) {
                dirCreated = theDir.mkdirs();
            }
            if (dirCreated) {
                String input = PATH + time + name + ".png";
                String output = PATH + time + name + ".webp";
                fos = new FileOutputStream(input);
                fos.write(imageBytes);
                fos.close();
                convertImageFile(input, 100, width, height, output);
            }
        }
        return theDir;
    }

    public static void convertImageFile(String input, int quality, int width, int height, String output) {
        CWebp cwebp = new CWebp()
                .input(input) // specify the input file.
                .quality(quality) //factor for RGB channels
                .resize(width, height) // resize the source to a rectangle with size width x height.
                .output(output); // specify the output WebP file "src/main/resources/546436333.webp"
        cwebp.execute();
    }

    public static void dropDirectory(File dir) {
        log.info("Dropping directory {}", dir.getAbsolutePath());
        String[] entries = dir.list();
        assert entries != null;
        for (String s : entries) {
            File currentFile = new File(dir.getPath(), s);
            currentFile.delete();
        }
    }

}
