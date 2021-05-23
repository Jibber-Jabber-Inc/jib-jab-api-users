package com.jibberjabber.jibjab_users.utils

import com.jibberjabber.jibjab_users.config.UserDetailsImpl
import com.jibberjabber.jibjab_users.domain.User
import com.jibberjabber.jibjab_users.exception.NotFoundException
import com.jibberjabber.jibjab_users.exception.UnauthorizedException
import com.jibberjabber.jibjab_users.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SessionUtils(
    private val userRepository: UserRepository
) {
    fun getTokenUserInformation(): User {
        val jwt = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("Error while getting session token")
        val user: UserDetailsImpl = jwt.principal as UserDetailsImpl
        val found: Optional<User> = userRepository.findFirstByUsername(user.username)
        return found.orElseThrow { throw NotFoundException("Token user not found") }
    }
}
