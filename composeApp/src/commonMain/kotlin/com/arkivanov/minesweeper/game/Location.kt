package com.arkivanov.minesweeper.game

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val x: Int,
    val y: Int,
)

infix fun Int.by(other: Int): Location =
    Location(x = this, y = other)

inline fun Location.forEachAdjacent(block: (Location) -> Unit) {
    forEachAround { location ->
        if (location != this) {
            block(location)
        }
    }
}

inline fun Location.forEachAround(block: (Location) -> Unit) {
    for (xx in x - 1..x + 1) {
        for (yy in y - 1..y + 1) {
            block(xx by yy)
        }
    }
}

inline fun Location.countAdjacent(predicate: (Location) -> Boolean): Int {
    var count = 0
    forEachAdjacent {
        if (predicate(it)) {
            count++
        }
    }

    return count
}
