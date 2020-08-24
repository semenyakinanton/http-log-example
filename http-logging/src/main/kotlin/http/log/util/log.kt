package http.log.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

val Any.log: Logger get() = LoggerFactory.getLogger(this.javaClass)