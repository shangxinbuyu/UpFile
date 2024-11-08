package io.github.sgangxinbuyu.upfile.controller;


import io.github.sgangxinbuyu.upfile.domain.Result;
import io.github.sgangxinbuyu.upfile.service.UpFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/upFile")
public class UpFile {
    private final UpFileService upFileService;

    @PostMapping("/accept_folder")
    public Result<Integer> acceptFolder(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            log.error("上传失败,目录为空");
            return Result.error("上传失败");
        }
        upFileService.acceptFolder(file);
        return Result.success();
    }

    @PostMapping("/accept_file")
    public Result<Integer> acceptFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            log.error("上传失败,文件为空");
            return Result.error("上传失败");
        }
        upFileService.acceptFile(file);
        return Result.success();
    }

}
