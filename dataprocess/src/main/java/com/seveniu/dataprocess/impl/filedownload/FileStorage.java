package com.seveniu.dataprocess.impl.filedownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by seveniu on 5/24/16.
 * ImageFileSave
 */
@Service
public class FileStorage {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Path storagePath;

    @Autowired
    public FileStorage(@Value("${fileDownloader.storagePath}") String storagePath) throws IOException {
        this.storagePath = this.getStoragePath(storagePath);
        logger.info("storage path : {}", storagePath);
        if (!Files.exists(this.storagePath)) {
            Files.createDirectory(this.storagePath);
        }
        if (Files.exists(this.storagePath) && !Files.isDirectory(this.storagePath)) {
            throw new IllegalArgumentException("error storage path ,is not dir");
        }
    }

    public void save(byte[] bytes, String fileName) throws IOException {
        Path path = Paths.get(this.storagePath.toString(), fileName);
        Files.write(path, bytes);
    }

    private Path getStoragePath(String pathString) {
        Path path = Paths.get(pathString);
        if (!path.isAbsolute()) {
            path = Paths.get(System.getProperty("user.dir")).resolve(path).normalize();
        }
        return path;
    }

}
