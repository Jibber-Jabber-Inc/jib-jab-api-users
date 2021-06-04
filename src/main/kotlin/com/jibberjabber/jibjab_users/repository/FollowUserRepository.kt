package com.jibberjabber.jibjab_users.repository

import com.jibberjabber.jibjab_users.domain.FollowUser
import com.jibberjabber.jibjab_users.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FollowUserRepository : JpaRepository<FollowUser, String> {

    fun findFirstByUserIdAndFollowUserId(userId: String, followUserId: String) : Optional<FollowUser>

    fun findAllByUserId(userId: String) : List<FollowUser>

    fun deleteByUserIdAndFollowUserId(userId: String, followUserId: String)

}