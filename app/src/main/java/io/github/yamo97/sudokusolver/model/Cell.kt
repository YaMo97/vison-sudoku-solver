package io.github.yamo97.sudokusolver.model

data class Cell (
    val row: Int,
    val col: Int,
    var value: Int,
    var isStartingCell: Boolean = false,
    var notes: MutableSet<Int> = mutableSetOf()
) {

    // Compare by only value
    override fun equals(other: Any?): Boolean {
        return if (other is Cell)
            this.value == other.value
        else super.equals(other)
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        result = 31 * result + value
        result = 31 * result + isStartingCell.hashCode()
        result = 31 * result + notes.hashCode()
        return result
    }
}