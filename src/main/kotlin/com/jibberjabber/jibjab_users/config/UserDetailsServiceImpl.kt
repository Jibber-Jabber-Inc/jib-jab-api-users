package com.jibberjabber.jibjab_users.config

import com.jibberjabber.jibjab_users.domain.User
import com.jibberjabber.jibjab_users.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    @Autowired val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findFirstByUsername(username)
            .orElseThrow { UsernameNotFoundException("User Not Found with username: $username") }
        return UserDetailsImpl.build(user)
    }
}
