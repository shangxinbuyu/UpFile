package io.github.sgangxinbuyu.upfile.controller;

import io.github.sgangxinbuyu.upfile.domain.Result;
import io.github.sgangxinbuyu.upfile.domain.po.FileVO;
import io.github.sgangxinbuyu.upfile.service.ShowFileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


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

    @GetMapping("/checkPermission")
    public Result<String> checkPermission() {
        log.info("权限校验");
        return showFileService.checkPermission();
    }

    @GetMapping("/download")
    public void download(@RequestParam String relativePath, HttpServletResponse response) throws IOException {
        log.info("文件下载:{}", relativePath);
        showFileService.download(relativePath,response);
//        String[] split = relativePath.split("/");
//        String fileName = split[split.length - 1];
//
//        File file = new File("E:\\project\\UpFIle\\src\\main\\resources\\" + fileName);
//
//        ServletOutputStream out = response.getOutputStream();
//        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
//        response.setContentType("application/octet-stream");
//        out.write(FileUtil.readBytes(file));
//        out.flush();
//        out.close();
    }
}
