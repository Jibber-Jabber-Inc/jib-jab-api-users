package com.jibberjabber.jibjab_users.service

import com.jibberjabber.jibjab_users.domain.FollowUser
import com.jibberjabber.jibjab_users.domain.User
import com.jibberjabber.jibjab_users.domain.UserRoleType
import com.jibberjabber.jibjab_users.dto.*
import com.jibberjabber.jibjab_users.exception.BadRequestException
import com.jibberjabber.jibjab_users.exception.NotFoundException
import com.jibberjabber.jibjab_users.repository.FollowUserRepository
import com.jibberjabber.jibjab_users.repository.RoleRepository
import com.jibberjabber.jibjab_users.repository.UserRepository
import com.jibberjabber.jibjab_users.utils.SessionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
    val userRepository: UserRepository,
    val encoder: PasswordEncoder,
    val roleRepository: RoleRepository,
    val sessionUtils: SessionUtils,
    val followUserRepository: FollowUserRepository,
    val passwordEncoder: PasswordEncoder
) : UserService {

    override
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

    override
    fun editProfile(profileEditDto: ProfileEditDto): UserDataDto {
        val currentUser: User = sessionUtils.getTokenUserInformation()
        if (passwordEncoder.matches(profileEditDto.password, currentUser.password)) {
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

    override
    fun userData(): UserDataDto {
        return UserDataDto.from(sessionUtils.getTokenUserInformation())
    }

    override
    fun getUserDataById(userId: String): UserDataDto {
        return UserDataDto.from(
            userRepository.findById(userId).orElseThrow { NotFoundException("User not found for given Id") }
        )
    }

    override
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override
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

    override
    fun followUser(userId: String) {
        val currentUser: User = sessionUtils.getTokenUserInformation()
        val followUser: User = userRepository.findById(userId).orElseThrow()
        val optional = followUserRepository.findFirstByUserIdAndFollowUserId(currentUser.id!!, userId)
        if (optional.isPresent) followUserRepository.deleteById(optional.get().id!!)
        else followUserRepository.save(FollowUser(currentUser.id!!, followUser))
    }

    override
    fun getFollowedUsersInfo(): UserDataDtoList {
        val currentUser: User = sessionUtils.getTokenUserInformation()
        val userInfoDto: List<User> = followUserRepository.findAllByUserId(currentUser.id!!).map { fu -> fu.followUser }
        return UserDataDtoList(userInfoDto.map { u -> UserDataDto.from(u) })
    }
}
