package com.demo.producer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router


@Configuration
class UserWebConfig(val repo: UserRepository) {

    @Bean
    fun userRoutes(): RouterFunction<ServerResponse> = router {
            accept(MediaType.TEXT_PLAIN).nest {
                (GET("/user") or GET("/all")) {
                    ok()
                            .body(repo.findAll(), User::class.java)
                }
            }
        }

}