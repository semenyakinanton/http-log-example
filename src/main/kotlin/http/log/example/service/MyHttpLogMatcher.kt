package http.log.example.service

import http.log.config.HttpLogProperties
import http.log.service.DefaultHttpLogMatcher
import org.springframework.stereotype.Component

@Component
class MyHttpLogMatcher(logParams: HttpLogProperties) : DefaultHttpLogMatcher(logParams) {
    override fun matches(path: String): Boolean {
        return super.matches(path)
    }
}