package io.github.sgangxinbuyu.upfile.domain.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileVO {
    private String path;
    private String name;
    private String type;
    private String size;
}
