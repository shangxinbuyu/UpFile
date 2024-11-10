package io.github.sgangxinbuyu.upfile.service;

import io.github.sgangxinbuyu.upfile.domain.Result;
import io.github.sgangxinbuyu.upfile.domain.po.FileVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface ShowFileService {
    String show(FileVO fileName);
    Result<String> checkPermission();

    void download(String relativePath, HttpServletResponse response);
}
