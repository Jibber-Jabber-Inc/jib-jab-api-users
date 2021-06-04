package com.jibberjabber.jibjab_users.dto

import com.jibberjabber.jibjab_users.domain.User

class ProfileEditDto(
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null
)

class UserDataDto(
    val id: String? = null,
    val username: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val role: String? = null
) {
    companion object {
        fun from(u: User): UserDataDto {
            return UserDataDto(u.id, u.username, u.email, u.firstName, u.lastName, u.role.userRoleType.name)
        }
    }
}

class PasswordChangeDto(
    val oldPassword: String? = null,
    val newPassword: String? = null
)
