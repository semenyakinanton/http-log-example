package http.log.example.service

import http.log.config.HttpLogProperties
import http.log.service.DefaultHttpLoggingProcessor
import http.log.service.HttpLogMatcher
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class MyHttpLoggingProcessor(logMatcher: HttpLogMatcher,
                             logParams: HttpLogProperties) : DefaultHttpLoggingProcessor(logMatcher, logParams) {
    override fun supportsRequest(request: HttpServletRequest): Boolean {
        return super.supportsRequest(request)
    }

    override fun logRequest(request: HttpServletRequest, body: ByteArray?) {
        super.logRequest(request, body)
    }

    override fun supportsResponse(request: HttpServletRequest, response: HttpServletResponse): Boolean {
        return super.supportsResponse(request, response)
    }

    override fun logResponse(request: HttpServletRequest, response: HttpServletResponse, body: ByteArray?, start: Long) {
        super.logResponse(request, response, body, start)
    }
}