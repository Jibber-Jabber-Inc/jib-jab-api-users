package com.jibberjabber.jibjab_users.controller

import com.jibberjabber.jibjab_users.dto.UserDataDto
import com.jibberjabber.jibjab_users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    @Autowired
    var userService: UserService
) {
    @GetMapping("/authenticateUser")
    fun changeUserPassword(): UserDataDto {
        return userService.userData
    }

    @PostMapping("/userInfo")
    fun getUserInfoById(@RequestHeader("userId") userId: String): UserDataDto {
        return userService.getUserDataById(userId)
    }
}
