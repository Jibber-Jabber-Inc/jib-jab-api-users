package com.jibberjabber.jibjab_users.domain

import javax.persistence.*

@Table(name = "user_data")
@Entity
class User(
    @Column(unique = true) var username: String,
    @Column(unique = true) var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    @ManyToOne val userRole: UserRole
) : AbstractEntity()

@Table(name = "follow_user")
@Entity
class FollowUser(
    override var id: String,
    @ManyToOne var followUser: User
) : AbstractEntityId(id)


@Table(name = "user_role")
@Entity
class UserRole(
    @Enumerated(EnumType.STRING) val userRoleType: UserRoleType
) : AbstractEntity()

enum class UserRoleType {
    ROLE_USER,
    ROLE_ADMIN
}
