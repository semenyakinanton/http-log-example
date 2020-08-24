package http.log.config

import http.log.filter.RequestNoContentLoggingFilter
import http.log.filter.ResponseLoggingFilter
import http.log.service.DefaultHttpLogMatcher
import http.log.service.DefaultHttpLoggingProcessor
import http.log.service.HttpLogMatcher
import http.log.service.HttpLoggingProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "http.log", name = ["enable"], havingValue = "true", matchIfMissing = true)
open class HttpLogConfig(private val logProperties: HttpLogProperties) {

    @Bean
    @ConditionalOnMissingBean
    open fun httpLogMatcher(): HttpLogMatcher {
        return DefaultHttpLogMatcher(logProperties)
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(HttpLogMatcher::class)
    open fun httpLoggingProcessor(matcher: HttpLogMatcher): HttpLoggingProcessor {
        return DefaultHttpLoggingProcessor(matcher, logProperties)
    }

    @Bean
    @ConditionalOnBean(HttpLoggingProcessor::class)
    open fun responseLoggingFilterRegistration(processor: HttpLoggingProcessor) =
            FilterRegistrationBean(ResponseLoggingFilter(processor)).apply {
                order = Ordered.HIGHEST_PRECEDENCE
            }

    @Bean
    @ConditionalOnBean(HttpLoggingProcessor::class)
    open fun requestNoContentLoggingFilterRegistration(processor: HttpLoggingProcessor) =
            FilterRegistrationBean(RequestNoContentLoggingFilter(processor)).apply {
                order = Ordered.LOWEST_PRECEDENCE - 1
            }
}