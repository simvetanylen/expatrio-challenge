package com.expatrio.challenge.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

// 403
@ResponseStatus(HttpStatus.FORBIDDEN)
open class ForbiddenException : RuntimeException {
    constructor() : super("Forbidden")
    constructor(throwable: Throwable) : super("Forbidden", throwable)
}