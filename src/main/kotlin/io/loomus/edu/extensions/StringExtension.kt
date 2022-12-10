package io.loomus.edu.extensions

val String.phone: String
    get() {
        val digits = this.replace("[^0-9]".toRegex(), "")
        return "7${digits.takeLast(10)}"
    }


val String.searchQuery: String
    get() {
        return "%${lowercase()}%"
    }