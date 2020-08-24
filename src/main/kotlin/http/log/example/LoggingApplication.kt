package http.log.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoggingApplication

fun main(args: Array<String>) {
	runApplication<LoggingApplication>(*args)
}
