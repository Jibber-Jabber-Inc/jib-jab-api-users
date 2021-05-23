package com.jibberjabber.jibjab_users.controller

import com.jibberjabber.jibjab_users.dto.PasswordChangeDto
import com.jibberjabber.jibjab_users.dto.ProfileEditDto
import com.jibberjabber.jibjab_users.dto.UserDataDto
import com.jibberjabber.jibjab_users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController @Autowired constructor(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): List<UserDataDto> {
        return userService.getAllUsers().map(UserDataDto::from)
    }

    @GetMapping("/loggedUser")
    fun getLoggedUser(): UserDataDto {
        return userService.userData()
    }

    @PostMapping("/userInfoById/{userId}")
    fun getUserInfoById(@PathVariable("userId") userId: String): UserDataDto {
        return userService.getUserDataById(userId)
    }

    @PutMapping("/editProfile")
    fun changeUserProfile(@RequestBody profileEditDto: ProfileEditDto): UserDataDto {
        return userService.editProfile(profileEditDto)
    }

    @PutMapping("/editPassword")
    fun changeUserPassword(@RequestBody passwordChange: PasswordChangeDto): UserDataDto {
        return userService.changePassword(passwordChange)
    }

}
