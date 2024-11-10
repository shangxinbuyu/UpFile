package io.github.sgangxinbuyu.upfile.service.impl;

import com.google.gson.Gson;
import io.github.sgangxinbuyu.upfile.context.BaseContext;
import io.github.sgangxinbuyu.upfile.domain.Result;
import io.github.sgangxinbuyu.upfile.domain.po.FileVO;
import io.github.sgangxinbuyu.upfile.domain.po.User;
import io.github.sgangxinbuyu.upfile.mapper.UserMapper;
import io.github.sgangxinbuyu.upfile.properties.UpFileProperties;
import io.github.sgangxinbuyu.upfile.service.ShowFileService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.io.file.FileUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ShowFileServiceImpl implements ShowFileService {
    private final UserMapper userMapper;
    private final UpFileProperties properties;
    private final Gson gson;

    @Override
    public String show(FileVO fileName) {
        List<FileVO> pisList = getList(fileName);
        // TODO 可行性未知
        pisList = pisList.stream()
                .sorted((s1, s2) -> {
                    boolean s1IsNumeric = isNumeric(s1.getName());
                    boolean s2IsNumeric = isNumeric(s2.getName());

                    // 如果都是数字，按数字升序排序
                    if (s1IsNumeric && s2IsNumeric) {
                        return Integer.valueOf(s1.getName()).compareTo(Integer.valueOf(s2.getName()));
                    }

                    // 如果 s1 和 s2 的类型不同（一个是数字，另一个是非数字）
                    if (s1IsNumeric != s2IsNumeric) {
                        return s1IsNumeric ? 1 : -1;  // 数字排在后面
                    }

                    // 如果都是非数字，按字母升序排序
                    return s1.getName().compareTo(s2.getName());
                })
                .toList();
        return gson.toJson(pisList);
    }


    @Override
    public Result<String> checkPermission() {
        Long id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);
        if (user.getPrivilege() != -1) {
            log.info("权限不够");
            return Result.success("权限不够");
        }
        return Result.success();
    }

    @Override
    public void download(String relativePath, HttpServletResponse response) {
        Long id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);

        // 判断用户权限
        if (user.getPrivilege() != -1) {
            return;
        }
        String[] split = relativePath.split("/");
        String fileName = split[split.length - 1];
        File file = new File(properties.getShowPath() + relativePath);

        try {
            ServletOutputStream os = response.getOutputStream();
            // 设置响应头为文件处理方式
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            // 设置响应头为二进制流
            response.setContentType("application/octet-stream");
            // 写入内容到响应体
            os.write(FileUtil.readBytes(file));
            os.close();
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 遍历文件夹内容
     *
     * @param fileName 文件夹相对路径
     * @return FileVO集合
     */
    private List<FileVO> getList(FileVO fileName) {
        // 拼接文件路径
        String filePath = properties.getShowPath() + fileName.getPath();
        File[] files = new File(filePath).listFiles();
        List<FileVO> pisList = new ArrayList<>();
        // 判断是否为空
        if (files != null) {
            // 遍历文件夹
            for (File f : files) {
                // 创建 VO 对象
                FileVO fpi = new FileVO();
                fpi.setName(f.getName());
                fpi.setPath(filePath);

                // 设置文件类型
                if (f.isFile()) {
                    fpi.setType("file");
                    fpi.setSize(f.length() + "");
                } else {
                    fpi.setType("folder");
                    fpi.setSize(" ");
                }
                // 添加到集合
                pisList.add(fpi);
            }
        }
        return pisList;
    }

    /**
     * 判断是否为纯数字
     *
     * @param str 被判断的字符串
     * @return 是否为纯数字
     */
    private static boolean isNumeric(String str) {
        return str != null && str.matches("^\\d+$");
    }
}