package http.log.service

import http.log.config.HttpLogProperties
import http.log.util.log
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class DefaultHttpLoggingProcessor(private val matcher: HttpLogMatcher,
                                       logProperties: HttpLogProperties) : HttpLoggingProcessor {

    private val maxPayloadLength = logProperties.maxPayloadLength
    private val charset = if (logProperties.charset == null) {
        Charset.defaultCharset()
    } else {
        Charset.forName(logProperties.charset)
    }

    override fun supportsRequest(request: HttpServletRequest) =
            log.isDebugEnabled && matcher.matches(request.servletPath)

    override fun logRequest(request: HttpServletRequest, body: ByteArray?) {
        val payload = getMessagePayload(body)
        logRequestInner(request, payload)
    }

    override fun supportsResponse(request: HttpServletRequest, response: HttpServletResponse) =
            log.isDebugEnabled && matcher.matches(request.servletPath)

    override fun logResponse(request: HttpServletRequest, response: HttpServletResponse, body: ByteArray?, start: Long) {
        val delta = System.currentTimeMillis() - start
        val payload = getMessagePayload(body)
        logResponseInner(request, response, payload, delta)
    }

    private fun getMessagePayload(body: ByteArray?): String {
        if (body != null) {
            val length = body.size.coerceAtMost(maxPayloadLength)
            return try {
                String(body, 0, length, charset)
            } catch (e: UnsupportedEncodingException) {
                "[unknown]"
            }
        }
        return "empty"
    }

    private fun logResponseInner(req: HttpServletRequest, resp: HttpServletResponse, payload: String, delta: Long) {
        val result = buildString {
            append("HttpResponse: ${resp.status} ($delta ms) ")
            logQueryString(req)
            resp.headerNames.forEach { toStr(it, resp.getHeaders(it), this) }
            append(PAYLOAD_HEADER)
            append(payload)
        }

        log.debug(result)
    }

    private fun logRequestInner(req: HttpServletRequest, payload: String) {
        val result = buildString {
            append("HttpRequest: ")
            logQueryString(req)
            val headerNames = req.headerNames
            if (headerNames != null) {
                for (header in req.headerNames) {
                    toStr(header, req.getHeader(header), this)
                }
            }
            append(PAYLOAD_HEADER)
            append(payload)
        }
        log.debug(result)
    }

    private fun StringBuilder.logQueryString(req: HttpServletRequest) {
        append("${req.method} ${req.requestURL}")
        val queryString = req.queryString
        if (!queryString.isNullOrEmpty()) {
            append("?").append(queryString)
        }
        append("\n")
        append(HEADERS_HEADER)
    }

    private fun toStr(key: String, values: Any?, b: StringBuilder) {
        with(b) {
            append("  $key: ")
            if (values is Collection<*>) {
                if (values.size == 1) {
                    append(values.first())
                } else {
                    append(values)
                }
            } else {
                append(values)
            }
            append("\n")
        }
    }
}

private const val HEADERS_HEADER = "------------------------- Headers --------------------------\n"
private const val PAYLOAD_HEADER = "------------------------- Content --------------------------\n"