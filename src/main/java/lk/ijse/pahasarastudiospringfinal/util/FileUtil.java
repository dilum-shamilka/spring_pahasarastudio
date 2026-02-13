package lk.ijse.pahasarastudiospringfinal.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

public class FileUtil {

    private static final String UPLOAD_DIR = "uploads/";

    public static String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}