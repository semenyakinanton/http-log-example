package http.log.service

import http.log.config.HttpLogProperties
import org.springframework.http.server.PathContainer
import org.springframework.web.util.pattern.PathPatternParser

open class DefaultHttpLogMatcher(properties: HttpLogProperties) : HttpLogMatcher {
    private val urlInclude = properties.urlPatternsInclude.map { PathPatternParser().parse(it) }
    private val urlExclude = properties.urlPatternsExclude.map { PathPatternParser().parse(it) }

    override fun matches(path: String): Boolean {
        val excluded = urlExclude.any { it.matches(PathContainer.parsePath(path)) }
        return if (excluded) {
            false
        } else {
            urlInclude.any { it.matches(PathContainer.parsePath(path)) }
        }
    }
}