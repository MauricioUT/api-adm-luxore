package mx.luxore.utils;


import com.google.auth.oauth2.GoogleCredentials;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;


@Slf4j
@Transactional
@Service
public class CloudStorageUtils {

    private final static String PROYECT_ID = "luxore-2";
    private final static String BUCKET = "luxore-img";
    private static final String CONTENT_TYPE = "image/webp"; // Replace with appropriate content type
    private static final String GOOGLE_CLOUD_STORAGE_URL = "https://storage.googleapis.com/";

    @Value("${credentials.path}")
    private String CREDENTIALS_FILE_PATH;

    /**
     * credentials.json will be created from new account service  with Operador de Cloud Storage para copias de seguridad y DR role
     */
    public String uploadFile(String fileName, String filePath) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(CloudStorageUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH)));
        Storage storage = StorageOptions.newBuilder()
                .setProjectId(PROYECT_ID)
                .setCredentials(credentials)
                .build().getService();

        BlobId blobId = BlobId.of(BUCKET, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(CONTENT_TYPE)
                .build();
        storage.createFrom(blobInfo, Paths.get(filePath));
        String publicUrl = GOOGLE_CLOUD_STORAGE_URL + BUCKET + "/" + fileName;

        log.info("File: " + filePath + " uploaded to bucket: " + BUCKET + " as " + fileName);
        log.info("GOOGLE CLOUD STORAGE PUBLIC URL: " + publicUrl);

        return publicUrl;

    }


    public void deleteFile(String objectName) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(CloudStorageUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH)));

        BlobId blobId = BlobId.of(BUCKET, objectName);
        Storage storage = StorageOptions.newBuilder()
                .setProjectId(PROYECT_ID)
                .setCredentials(credentials)
                .build().getService();
        boolean deleted = storage.delete(blobId);

        if (deleted) {
            log.info("File: " + objectName + " was deleted from " + BUCKET);
        } else {
            log.info("File: " + objectName + " wasn't deleted from " + BUCKET);
        }
    }
}
