package com.demo.capetown.consumer

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = ["com.demo:capetown-producer:+:8090"],
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ConsumerClientTests(@Autowired private val webClient: WebClient) {

    private val demoClient by lazy {
        DemoClient(webClient)
    }

    private val generalUserMatcher = Matchers.allOf(
            Matchers.notNullValue(),
            Matchers.hasProperty("name", Matchers.notNullValue()),
            Matchers.hasProperty("id", Matchers.notNullValue())
    )!!

    @Test
    fun `should consume service all endpoint`() {
        //val users: Supplier<Publisher<User>> = Supplier { userClient.getAll() }
        StepVerifier
                .create(demoClient.getAll())
                .expectSubscription()
                .assertNext {
                    MatcherAssert
                            .assertThat("user state", it, generalUserMatcher)
                }
                .verifyComplete()
    }

}