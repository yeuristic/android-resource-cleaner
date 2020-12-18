package com.yeuristic

data class ResourceData(
    val folder: String,
    val file: String
) {
    override fun toString(): String {
        return "$folder/$file"
    }
}