package com.demo.producer

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.*

interface  PeachRepository : ReactiveMongoRepository<Peach, UUID>
