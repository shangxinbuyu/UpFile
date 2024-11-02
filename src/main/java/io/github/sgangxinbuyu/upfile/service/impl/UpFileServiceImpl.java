package io.github.sgangxinbuyu.upfile.service.impl;
import io.github.sgangxinbuyu.upfile.properties.UpFileProperties;
import io.github.sgangxinbuyu.upfile.service.UpFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpFileServiceImpl implements UpFileService {
    private final UpFileProperties properties;

    @Override
    public void acceptFolder(MultipartFile file) {
        receiveFiles(file, properties.getFolderPath());

    }

    @Override
    public void acceptFile(MultipartFile file) {
        receiveFiles(file, properties.getFilePath());
    }

    @Override
    public void list() {
    }


    private void receiveFiles(MultipartFile file, String basePath) {
        LocalDate today = LocalDate.now();
        Path datePath = Paths.get(basePath, // 初始路径
                String.valueOf(today.getYear()), // 年
                String.valueOf(today.getMonthValue()), // 月
                String.valueOf(today.getDayOfMonth()),  // 日
                String.valueOf(LocalDateTime.now().getHour()), // 时
                file.getOriginalFilename()); // 文件名

        Path directoryPath = datePath.getParent();
        try {
            // 创建目录（如果不存在）
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            // 写入文件
            Files.write(datePath, file.getBytes());
            // 获取文件大小
            long size = file.getSize();
            double toMb = (double) size / 1024 / 1024;
            // 保留后两位小数
            DecimalFormat df = new DecimalFormat("0.00");
            log.info("文件上传成功: {} ,Size: {}", datePath.toAbsolutePath(), (df.format(toMb) + "MB"));
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败", e);
        }
    }
}
