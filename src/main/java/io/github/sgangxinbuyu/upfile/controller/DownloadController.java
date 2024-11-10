package io.github.sgangxinbuyu.upfile.controller;

import io.github.sgangxinbuyu.upfile.properties.UpFileProperties;
import io.github.sgangxinbuyu.upfile.service.ShowFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {
    private final UpFileProperties properties;
    @PostMapping
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = properties.getShowPath() + request.getHeader("filePath");

        File file = new File(path);
        request.setCharacterEncoding("utf-8");

        try (InputStream in = new BufferedInputStream(new FileInputStream(file));
             OutputStream out = new BufferedOutputStream(response.getOutputStream())) {
            // 分片下载
            Long fileSize = file.length();
            String fileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setHeader("Accept-Ranges", "bytes");

            response.setHeader("FileSize", String.valueOf(fileSize));
            response.setHeader("FileName", fileName);

            long start;
            long end = fileSize - 1;
            long sum = 0;

            if (request.getHeader("Range") != null) {
                // 分片下载
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

                String rangeNum = request.getHeader("Range").replaceAll("Range=", "");
                String[] split = rangeNum.split("-");
                if (split.length == 2) {
                    start = Long.parseLong(split[0].trim());
                    end = Long.parseLong(split[1].trim());
                    // 如果结束位置超出文件大小则调整为文件大小
                    if (end > fileSize) {
                        end = fileSize;
                    }
                } else {
                    start = Long.parseLong(rangeNum.replaceAll("-", "").trim());
                }
                // 读取的字节书
                long rangerLen = end - start + 1;
                String contentRange = "bytes=" + start + "-" + end + "-"+"/"+fileSize;
                response.setHeader("Content-Range", contentRange);
                response.setHeader("Content-Length", String.valueOf(rangerLen));

                in.skip(start);
                byte[] buffer = new byte[1024];
                int len;
                while (sum < rangerLen) {
                    len = in.read(buffer, 0, (rangerLen-sum) <= buffer.length ? (int) (rangerLen - sum) : buffer.length);
                    sum += len;
                    out.write(buffer, 0, len);
                }
                log.info("碎片{}{}下载完成",start,end);
                System.out.println("下载完成");

            }
        }
    }
}
