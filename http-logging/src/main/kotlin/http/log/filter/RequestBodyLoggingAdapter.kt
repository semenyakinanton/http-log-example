package http.log.filter

import com.google.common.io.ByteStreams
import http.log.service.HttpLoggingProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.io.ByteArrayInputStream
import java.lang.reflect.Type
import javax.servlet.http.HttpServletRequest

@ControllerAdvice(annotations = [RestController::class])
@ConditionalOnBean(HttpLoggingProcessor::class)
class RequestBodyLoggingAdapter(private val request: HttpServletRequest,
                                private val processor: HttpLoggingProcessor) : RequestBodyAdviceAdapter() {

    override fun supports(methodParameter: MethodParameter,
                          targetType: Type,
                          converterType: Class<out HttpMessageConverter<*>>) =
            processor.supportsRequest(request)

    override fun beforeBodyRead(inputMessage: HttpInputMessage,
                                parameter: MethodParameter,
                                targetType: Type,
                                converterType: Class<out HttpMessageConverter<*>>): HttpInputMessage {
        val body = ByteStreams.toByteArray(inputMessage.body)
        processor.logRequest(request, body)
        return object : HttpInputMessage {
            override fun getHeaders() = inputMessage.headers
            override fun getBody() = ByteArrayInputStream(body)
        }
    }
}