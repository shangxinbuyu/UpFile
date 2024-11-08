package io.github.sgangxinbuyu.upfile.controller;

import io.github.sgangxinbuyu.upfile.domain.Result;
import io.github.sgangxinbuyu.upfile.domain.dto.UserLoginDTO;
import io.github.sgangxinbuyu.upfile.domain.vo.UserLoginVO;
import io.github.sgangxinbuyu.upfile.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录 {}",userLoginDTO);
        UserLoginVO loginVO = userService.login(userLoginDTO);
        return Result.success(loginVO);
    }
}
