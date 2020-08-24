package http.log.example.model

import javax.validation.constraints.NotNull

data class Person(
        @field:NotNull var name: String? = null,
        @field:NotNull var age: Int? = null
)