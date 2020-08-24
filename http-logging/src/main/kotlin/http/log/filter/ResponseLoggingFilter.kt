package http.log.filter

import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import http.log.service.HttpLoggingProcessor
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ResponseLoggingFilter(private val processor: HttpLoggingProcessor) : OncePerRequestFilter() {

    override fun doFilterInternal(httpServletRequest: HttpServletRequest,
                                  httpServletResponse: HttpServletResponse,
                                  filterChain: FilterChain) {
        if (!processor.supportsResponse(httpServletRequest, httpServletResponse)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse)
            return
        }
        val responseWrapper = ContentCachingResponseWrapper(httpServletResponse)
        val start = System.currentTimeMillis()
        try {
            filterChain.doFilter(httpServletRequest, responseWrapper)
        } finally {
            val nativeResponse = WebUtils.getNativeResponse(responseWrapper, ContentCachingResponseWrapper::class.java)
            val body = nativeResponse?.contentAsByteArray
            responseWrapper.copyBodyToResponse()
            processor.logResponse(httpServletRequest, responseWrapper, body, start)
        }
    }
}