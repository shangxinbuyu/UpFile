package io.github.sgangxinbuyu.upfile;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;


class UpFIleApplicationTests {

    @Test
    void contextLoads() {
        File file = new File("C:\\Users\\lx305\\Desktop\\fsdownload");

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    System.out.println("文件" + f.getName());
                }else {
                    System.out.println("文件夹" + f.getName());
                }
            }
        }
    }

}
