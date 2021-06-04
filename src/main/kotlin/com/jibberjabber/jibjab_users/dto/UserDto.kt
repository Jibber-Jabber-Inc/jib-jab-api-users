package com.jibberjabber.jibjab_users.dto

import com.jibberjabber.jibjab_users.domain.User

data class ProfileEditDto(
    val password: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null
)

data class UserDataDto(
    val id: String? = null,
    val username: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val role: String? = null
) {
    companion object {
        fun from(u: User): UserDataDto {
            return UserDataDto(u.id, u.username, u.email, u.firstName, u.lastName, u.userRole.userRoleType.name)
        }
    }
}

data class PasswordChangeDto(
    val oldPassword: String? = null,
    val newPassword: String? = null
)

data class UserDataDtoList(
    var userInfoDto: List<UserDataDto>
)