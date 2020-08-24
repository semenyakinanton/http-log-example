package http.log.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "http.log")
class HttpLogProperties {
    var urlPatternsInclude: List<String> = listOf("/**")
    var urlPatternsExclude: List<String> = listOf()
    var maxPayloadLength: Int = 10_000
    var charset: String? = null
    var enable = true
}