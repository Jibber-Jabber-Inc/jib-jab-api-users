package com.jibberjabber.jibjab_users.config

import com.fasterxml.jackson.annotation.JsonIgnore
import com.jibberjabber.jibjab_users.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val id: String?,
    private val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    @JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    companion object {
        private const val serialVersionUID: Long = 1L

        fun build(user: User): UserDetailsImpl {
            return UserDetailsImpl(
                user.id,
                user.username,
                user.email,
                user.firstName,
                user.lastName,
                user.password,
                listOf(SimpleGrantedAuthority(user.userRole.userRoleType.name))
            )
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
