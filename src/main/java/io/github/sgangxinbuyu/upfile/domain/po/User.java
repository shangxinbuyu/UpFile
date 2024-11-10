package io.github.sgangxinbuyu.upfile.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String createdTime;
    private String updateTime;
    private String status;
    private Integer privilege;
}
