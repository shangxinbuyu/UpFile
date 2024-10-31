package io.github.sgangxinbuyu.upfile.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "up-file")
public class UpFileProperties {
    private String folderPath;
    private String filePath;
    private String showPath;
}
