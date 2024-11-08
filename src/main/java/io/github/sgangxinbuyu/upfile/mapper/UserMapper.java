package io.github.sgangxinbuyu.upfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sgangxinbuyu.upfile.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE name = #{name}")
    User getByName(String name);
}
