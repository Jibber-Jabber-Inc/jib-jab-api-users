package com.jibberjabber.jibjab_users.service

import com.jibberjabber.jibjab_users.domain.User
import com.jibberjabber.jibjab_users.dto.PasswordChangeDto
import com.jibberjabber.jibjab_users.dto.ProfileEditDto
import com.jibberjabber.jibjab_users.dto.RegisterRequestDto
import com.jibberjabber.jibjab_users.dto.UserDataDto
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun registerUser(registerRequest: RegisterRequestDto): User

    fun editProfile(profileEditDto: ProfileEditDto): UserDataDto

    fun userData(): UserDataDto

    fun getUserDataById(userId: String): UserDataDto

    fun getAllUsers(): List<User>

    fun changePassword(passwordChange: PasswordChangeDto): UserDataDto

    fun followUser(userId: String)
}
