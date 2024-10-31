package io.github.sgangxinbuyu.upfile.service;

import io.github.sgangxinbuyu.upfile.domain.FileVO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface ShowFileService {
    String show(FileVO fileName);

    ResponseEntity<InputStreamResource> download(FileVO filePI);
}
