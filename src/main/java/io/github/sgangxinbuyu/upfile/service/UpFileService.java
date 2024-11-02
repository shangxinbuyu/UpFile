package io.github.sgangxinbuyu.upfile.service;

import org.springframework.web.multipart.MultipartFile;

public interface UpFileService {
    void acceptFolder(MultipartFile file);
    void acceptFile(MultipartFile file);
}
