package com.jibberjabber.jibjab_users.domain

import javax.persistence.*

@Table(name = "user_data")
@Entity
class User(
    var username: String,
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    @ManyToOne @JoinColumn(name = "user_role_id") val role: UserRole
) : AbstractEntity()

@Table(name = "user_role")
@Entity
class UserRole(
    @Enumerated(EnumType.STRING) val userRoleType: UserRoleType
) : AbstractEntity()

enum class UserRoleType {
    ROLE_USER,
    ROLE_ADMIN
}
