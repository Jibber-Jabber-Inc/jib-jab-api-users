package com.jibberjabber.jibjab_users.dto

class LoginDto(
    val username: String? = null,
    val password: String? = null
)

class LoginResponseDto(
    val id: String? = null,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val role: String? = null
)

class RegisterRequestDto(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String
)
