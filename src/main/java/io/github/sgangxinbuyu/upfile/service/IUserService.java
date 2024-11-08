package io.github.sgangxinbuyu.upfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sgangxinbuyu.upfile.domain.dto.UserLoginDTO;
import io.github.sgangxinbuyu.upfile.domain.po.User;
import io.github.sgangxinbuyu.upfile.domain.vo.UserLoginVO;

public interface IUserService extends IService<User> {
    UserLoginVO login(UserLoginDTO userLoginDTO);
}
