package com.jibberjabber.jibjab_users.service

import com.jibberjabber.jibjab_users.domain.User
import com.jibberjabber.jibjab_users.domain.UserRoleType
import com.jibberjabber.jibjab_users.dto.PasswordChangeDto
import com.jibberjabber.jibjab_users.dto.ProfileEditDto
import com.jibberjabber.jibjab_users.dto.RegisterRequestDto
import com.jibberjabber.jibjab_users.dto.UserDataDto
import com.jibberjabber.jibjab_users.exception.BadRequestException
import com.jibberjabber.jibjab_users.exception.NotFoundException
import com.jibberjabber.jibjab_users.repository.RoleRepository
import com.jibberjabber.jibjab_users.repository.UserRepository
import com.jibberjabber.jibjab_users.utils.SessionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    val userRepository: UserRepository,
    val encoder: PasswordEncoder,
    val roleRepository: RoleRepository,
    val sessionUtils: SessionUtils,
    val passwordEncoder: PasswordEncoder
) {

    fun registerUser(registerRequest: RegisterRequestDto): User {
        val user = User(
            registerRequest.username,
            registerRequest.email,
            encoder.encode(registerRequest.password),
            registerRequest.firstName,
            registerRequest.lastName,
            roleRepository.findByUserRoleType(UserRoleType.ROLE_USER)
                .orElseThrow { RuntimeException("Error: Role not found.") }
        )
        return userRepository.save(user)
    }

    fun editProfile(profileEditDto: ProfileEditDto): UserDataDto {
        val currentUser: User = sessionUtils.getTokenUserInformation()
        if (passwordEncoder.matches(profileEditDto.password, currentUser.password)) {
            if (profileEditDto.newPassword != null) {
                currentUser.password = passwordEncoder.encode(profileEditDto.newPassword)
            }
            if (!profileEditDto.email.isNullOrEmpty()) {
                currentUser.email = profileEditDto.email
            }
            if (!profileEditDto.firstName.isNullOrEmpty()) {
                currentUser.firstName = profileEditDto.firstName
            }
            if (!profileEditDto.lastName.isNullOrEmpty()) {
                currentUser.lastName = profileEditDto.lastName
            }
            userRepository.save(currentUser)
            return UserDataDto.from(currentUser)
        }
        throw BadRequestException("Invalid Password")
    }

    fun userData(): UserDataDto {
        return UserDataDto.from(sessionUtils.getTokenUserInformation())
    }

    fun getUserDataById(userId: String): UserDataDto {
        return UserDataDto.from(
            userRepository.findById(userId).orElseThrow { NotFoundException("User not found for given Id") }
        )
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun changePassword(passwordChange: PasswordChangeDto): UserDataDto {
        val currentUser: User = sessionUtils.getTokenUserInformation()
        if (passwordEncoder.matches(passwordChange.oldPassword, currentUser.password)) {
            if (passwordChange.newPassword != null) {
                currentUser.password = passwordEncoder.encode(passwordChange.newPassword)
            }
            return UserDataDto.from(userRepository.save(currentUser))
        }
        throw BadRequestException("Invalid Password")
    }
}
