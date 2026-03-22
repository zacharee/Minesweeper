package com.arkivanov.minesweeper.game

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val grid: Grid,
    val width: Int = grid.keys.maxOf { it.x } + 1,
    val height: Int = grid.keys.maxOf { it.y } + 1,
    val maxMines: Int = grid.values.count { it.value.isMine },
    val gameStatus: GameStatus = GameStatus.INITIALIZED,
    val pressMode: PressMode = PressMode.NONE,
    val timer: Int = 0,
) {
    init {
        require(grid.size == width * height) { "Grid size must be equal to width * height" }
    }
}

enum class GameStatus {
    INITIALIZED,
    STARTED,
    WIN,
    FAILED,
}

enum class PressMode {
    NONE,
    SINGLE,
    MULTIPLE,
}

val GameStatus.isOver: Boolean
    get() =
        when (this) {
            GameStatus.INITIALIZED,
            GameStatus.STARTED -> false

            GameStatus.WIN,
            GameStatus.FAILED -> true
        }

typealias Grid = Map<Location, Cell>
typealias MutableGrid = MutableMap<Location, Cell>

@Serializable
data class Cell(
    val value: CellValue = CellValue.None,
    val status: CellStatus = CellStatus.Closed(),
)

@Serializable
sealed interface CellValue {

    @Serializable
    data object None : CellValue

    @Serializable
    data object Mine : CellValue

    @Serializable
    data class Number(val number: Int) : CellValue
}

@Serializable
sealed interface CellStatus {

    @Serializable
    data class Closed(
        val isFlagged: Boolean = false,
        val isPressed: Boolean = false,
    ) : CellStatus

    @Serializable
    data object Open : CellStatus
}

val CellValue.isNone: Boolean
    get() = this is CellValue.None

val CellValue.isMine: Boolean
    get() = this is CellValue.Mine

val CellValue.isNumber: Boolean
    get() = asNumber() != null

fun CellValue.asNumber(): CellValue.Number? =
    this as? CellValue.Number

val CellStatus.isClosed: Boolean
    get() = this is CellStatus.Closed

val CellStatus.isOpen: Boolean
    get() = this is CellStatus.Open

val CellStatus.isFlagged: Boolean
    get() = (this as? CellStatus.Closed)?.isFlagged == true

fun Cell.open(): Cell =
    copy(status = CellStatus.Open)

// TODO: Consider making this a part of the state
val GameState.remainingMines: Int
    get() = (maxMines - grid.values.count { it.status.isFlagged })
