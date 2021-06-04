package com.jibberjabber.jibjab_users.controller

import com.jibberjabber.jibjab_users.dto.PasswordChangeDto
import com.jibberjabber.jibjab_users.dto.ProfileEditDto
import com.jibberjabber.jibjab_users.dto.UserDataDto
import com.jibberjabber.jibjab_users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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

    @GetMapping("/info/{userId}")
    fun getUserInfoById(@PathVariable("userId") userId: String): UserDataDto {
        return userService.getUserDataById(userId)
    }

    @PutMapping("/editProfile")
    fun changeUserProfile(@RequestBody @Valid profileEditDto: ProfileEditDto): UserDataDto {
        return userService.editProfile(profileEditDto)
    }

    @PutMapping("/editPassword")
    fun changeUserPassword(@RequestBody @Valid passwordChange: PasswordChangeDto): UserDataDto {
        return userService.changePassword(passwordChange)
    }

    @PostMapping("/follow/{userId}")
    fun followUser(@PathVariable("userId") userId: String) {
        userService.followUser(userId)
    }
}
