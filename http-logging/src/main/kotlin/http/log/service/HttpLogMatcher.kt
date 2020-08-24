package http.log.service

interface HttpLogMatcher {
    fun matches(path: String) = true
}