package com.sena.lavadero.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileUploadService {
    String uploadFile(MultipartFile multipartFile) throws IOException;
}
