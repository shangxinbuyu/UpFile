package io.github.sgangxinbuyu.upfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class UpFIleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpFIleApplication.class, args);
    }

}
