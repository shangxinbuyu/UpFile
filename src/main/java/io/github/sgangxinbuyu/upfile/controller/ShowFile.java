package io.github.sgangxinbuyu.upfile.controller;

import io.github.sgangxinbuyu.upfile.domain.po.FileVO;
import io.github.sgangxinbuyu.upfile.service.ShowFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/get")
public class ShowFile {

    private final ShowFileService showFileService;

    @PostMapping("/fileList")
    public String showFile(@RequestBody FileVO fileName) {
        String json = showFileService.show(fileName);
        log.info("{}{}", "获取文件列表", json);
        return json;

    }

    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestBody FileVO filePI) {
        log.info(filePI.getPath());
        return showFileService.download(filePI);
    }
}
