package com.expatrio.challenge.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

// 401
@ResponseStatus(HttpStatus.UNAUTHORIZED)
open class UnauthorizedException : RuntimeException {
    constructor() : super("Unauthorized")
    constructor(throwable: Throwable) : super("Unauthorized", throwable)
}
