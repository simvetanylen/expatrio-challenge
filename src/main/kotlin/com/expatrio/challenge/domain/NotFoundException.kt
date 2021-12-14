package com.expatrio.challenge.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

// 404
@ResponseStatus(HttpStatus.NOT_FOUND)
open class NotFoundException : RuntimeException {
    constructor() : super("Not Found")
    constructor(throwable: Throwable) : super("Not Found", throwable)
}