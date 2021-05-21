package com.jibberjabber.jibjab_users.repository

import com.jibberjabber.jibjab_users.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, String> {

    fun findFirstByUsername(username: String): Optional<User>

}