package com.base.admin.controller;

import com.base.common.result.Result;
import com.base.service.entity.User;
import com.base.service.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

@Api(value = "测试controller", tags = {"1"})
@RestController
@RequestMapping("/admin/test")
public class TestController extends BaseController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", required = true)
    })
    @PostMapping(path = "/getUser")
    public Result getUser(@ApiIgnore @RequestAttribute("user") User user,
                          @RequestParam(value = "name", defaultValue = "") String name) {
        if (null != user) {
            return success().put("user", user);
        }
        return success().put("user", userService.singleTableQuery(name));
    }

    @ApiOperation(value = "查询用户信息2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", required = true)
    })
    @PostMapping(path = "/getUser2")
    public Result getUser2(@RequestParam(value = "name", defaultValue = "") String name) {
        return success().put("user", userService.singleTableQuery(name));
    }


}
