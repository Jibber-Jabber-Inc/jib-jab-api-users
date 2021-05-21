package com.jibberjabber.jibjab_users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class JibjabUsersApplication

fun main(args: Array<String>) {
    runApplication<JibjabUsersApplication>(*args)
}
