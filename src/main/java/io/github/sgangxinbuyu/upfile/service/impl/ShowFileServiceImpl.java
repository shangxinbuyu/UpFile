package io.github.sgangxinbuyu.upfile.service.impl;

import com.google.gson.Gson;
import io.github.sgangxinbuyu.upfile.domain.FileVO;
import io.github.sgangxinbuyu.upfile.properties.UpFileProperties;
import io.github.sgangxinbuyu.upfile.service.ShowFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowFileServiceImpl implements ShowFileService {
    private final UpFileProperties properties;
    private final Gson gson;
    @Override
    public String show(FileVO fileName) {
        String filePath = properties.getShowPath() + fileName.getPath();
        File[] files = new File(filePath).listFiles();
        //TODO 按照文件名称排序
        List<FileVO> pisList = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                FileVO fpi = new FileVO();
                fpi.setName(f.getName());
                fpi.setPath(filePath);

                if (f.isFile()) {
                    fpi.setType("file");
                    fpi.setSize(f.length() + "");
                } else {
                    fpi.setType("folder");
                    fpi.setSize(" ");
                }
                pisList.add(fpi);
            }
        }
        return gson.toJson(pisList);
    }

    @Override
    public ResponseEntity<InputStreamResource> download(FileVO filePI) {
        File file = new File(filePI.getPath());

        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                    .body(resource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
