package http.log.example.controller

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import http.log.example.model.Person
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/person")
class PersonController {

    @GetMapping
    fun get() = Person("Alice", 19)

    @PostMapping
    fun create(@RequestBody @Valid request: Person) {
    }

    @PutMapping
    fun update(@RequestBody @Valid request: Person) = request

}