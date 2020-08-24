package http.log.service

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface HttpLoggingProcessor {

    fun supportsRequest(request: HttpServletRequest) = true

    fun logRequest(request: HttpServletRequest, body: ByteArray? = null)

    fun supportsResponse(request: HttpServletRequest, response: HttpServletResponse) = true

    fun logResponse(request: HttpServletRequest, response: HttpServletResponse, body: ByteArray? = null, start: Long)
}