package com.jibberjabber.jibjab_users.controller

import com.jibberjabber.jibjab_users.dto.LoginDto
import com.jibberjabber.jibjab_users.dto.LoginResponseDto
import com.jibberjabber.jibjab_users.dto.RegisterRequestDto
import com.jibberjabber.jibjab_users.dto.UserDataDto
import com.jibberjabber.jibjab_users.service.AuthenticationService
import com.jibberjabber.jibjab_users.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    val authenticationService: AuthenticationService,
    val userService: UserService
) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginDto, response: HttpServletResponse): LoginResponseDto {
        return authenticationService.authenticate(loginRequest, response)
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody registerRequest: RegisterRequestDto): UserDataDto {
        return UserDataDto.from(userService.registerUser(registerRequest))
    }

}
