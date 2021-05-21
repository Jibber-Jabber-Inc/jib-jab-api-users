package com.jibberjabber.jibjab_users.service

import com.jibberjabber.jibjab_users.config.JwtUtils
import com.jibberjabber.jibjab_users.config.UserDetailsImpl
import com.jibberjabber.jibjab_users.domain.UserRole
import com.jibberjabber.jibjab_users.domain.UserRoleType
import com.jibberjabber.jibjab_users.dto.LoginDto
import com.jibberjabber.jibjab_users.dto.LoginResponseDto
import com.jibberjabber.jibjab_users.exception.NotFoundException
import com.jibberjabber.jibjab_users.repository.RoleRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletResponse


@Service
class AuthenticationService(
    val authenticationManager: AuthenticationManager,
    val jwtUtils: JwtUtils,
    val roleRepository: RoleRepository
) {

    fun authenticate(loginDto: LoginDto, response: HttpServletResponse): LoginResponseDto {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils.generateJwtToken(authentication)
        response.setHeader("Set-Cookie", "jwt=$jwt; HttpOnly; SameSite=strict; Path=/api; Secure")
        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
        val role: String = userDetails.authorities.first()?.authority ?: throw NotFoundException("Role Not found")
        return LoginResponseDto(
            userDetails.id,
            userDetails.username,
            userDetails.email,
            role,
            userDetails.firstName,
            userDetails.lastName
        )
    }

    @PostConstruct
    fun addRoles() {
        if (!roleRepository.existsByUserRoleType(UserRoleType.ROLE_USER)) {
            val userRole = UserRole(UserRoleType.ROLE_USER)
            roleRepository.save(userRole)
        }
        if (!roleRepository.existsByUserRoleType(UserRoleType.ROLE_ADMIN)) {
            val userRole = UserRole(UserRoleType.ROLE_ADMIN)
            roleRepository.save(userRole)
        }
    }

}
