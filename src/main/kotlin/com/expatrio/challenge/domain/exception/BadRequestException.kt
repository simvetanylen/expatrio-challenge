package com.expatrio.challenge.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

// 400
@ResponseStatus(HttpStatus.BAD_REQUEST)
open class BadRequestException : RuntimeException {
    constructor() : super("Bad Request")
    constructor(throwable: Throwable) : super("Bad Request", throwable)
}