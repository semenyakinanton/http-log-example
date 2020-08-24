package http.log.filter

import org.springframework.web.filter.OncePerRequestFilter
import http.log.service.HttpLoggingProcessor
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestNoContentLoggingFilter(private val processor: HttpLoggingProcessor) : OncePerRequestFilter() {

    override fun doFilterInternal(httpServletRequest: HttpServletRequest,
                                  httpServletResponse: HttpServletResponse,
                                  filterChain: FilterChain) {
        if (httpServletRequest.contentLength <= 0 && processor.supportsRequest(httpServletRequest)) {
            processor.logRequest(httpServletRequest)
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }
}