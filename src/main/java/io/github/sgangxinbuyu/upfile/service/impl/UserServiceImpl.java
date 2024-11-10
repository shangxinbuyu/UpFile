package io.github.sgangxinbuyu.upfile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sgangxinbuyu.upfile.domain.dto.UserLoginDTO;
import io.github.sgangxinbuyu.upfile.domain.po.User;
import io.github.sgangxinbuyu.upfile.domain.vo.UserLoginVO;
import io.github.sgangxinbuyu.upfile.mapper.UserMapper;
import io.github.sgangxinbuyu.upfile.service.IUserService;
import io.github.sgangxinbuyu.upfile.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final JwtUtil jwtUtil;

    private final UserMapper userMapper;
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        String name = userLoginDTO.getName();
        String password = DigestUtils.md5Hex(userLoginDTO.getPassword());

        User user = userMapper.getByName(name);

        //TODO 自定义异常
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }

        if (user.getStatus().equals("0")) {
            throw new RuntimeException("账号被锁定");
        }


        String jwt = jwtUtil.generateToken(user.getId().toString());

        return UserLoginVO.builder()
                .name(user.getName())
                 .id(user.getId())
                 .token(jwt)
                 .build();
    }
}
