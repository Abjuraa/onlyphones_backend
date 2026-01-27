package com.onlyphones.onlyphones.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.onlyphones.onlyphones.exceptions.cloudinary_exceptions.CloudinaryCanNotDeleteFile;
import com.onlyphones.onlyphones.exceptions.cloudinary_exceptions.CloudinaryFileCanNotBeEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {

        try{
            if (file == null || file.isEmpty()) {
                throw new CloudinaryFileCanNotBeEmptyException("el archivo no puede estar vacio");
            }

            Map params = ObjectUtils.asMap(
                    "folder", "onlyphones/productos",
                    "resource_type", "auto"
            );
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            System.out.println("CLOUDINARY RESPONSE");
            uploadResult.forEach((k, v) -> System.out.println(k + "=" + v));
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("error subiendo imagen a Cloudinary", e);
        }
    }

    public Map uploadFileWithMeta(MultipartFile file) {
        try{
            Map result = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "onlyphones/productos"));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String publicId){
        try {
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (Exception e) {
            throw new CloudinaryCanNotDeleteFile("no se puede eliminar esta foto");
        }
    }
}
