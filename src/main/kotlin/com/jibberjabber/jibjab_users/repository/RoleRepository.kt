package com.jibberjabber.jibjab_users.repository

import com.jibberjabber.jibjab_users.domain.UserRole
import com.jibberjabber.jibjab_users.domain.UserRoleType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<UserRole, String> {
    fun findByUserRoleType(userRoleType: UserRoleType): Optional<UserRole>
    fun existsByUserRoleType(userRoleType: UserRoleType): Boolean
}
