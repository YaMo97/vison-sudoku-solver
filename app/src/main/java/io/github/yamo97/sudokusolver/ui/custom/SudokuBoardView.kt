package io.github.yamo97.sudokusolver.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import io.github.yamo97.sudokusolver.model.Cell
import kotlin.math.sqrt

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val sqrtSize = 3
    private val size = 9

    // These are set in onDraw
    private var cellSizePixels = 0F
    private var noteSizePixels = 0F

    private var selectedRow = -1
    private var selectedCol = -1

    private var listener: OnTouchListener? = null

    private var cells: List<Cell>? = null

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6ead3a")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#efedef")
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    private val startingCellTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        typeface = Typeface.DEFAULT_BOLD
    }

    private val startingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#acacac")
    }

    private val noteTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = widthMeasureSpec.coerceAtMost(heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        updateMeasurements(width)

        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    private fun updateMeasurements(width: Int) {

        cellSizePixels = (width / size).toFloat()
        noteSizePixels = cellSizePixels / sqrtSize.toFloat()

        noteTextPaint.textSize = cellSizePixels / sqrtSize.toFloat()
        textPaint.textSize = cellSizePixels / 1.5F
        startingCellTextPaint.textSize = cellSizePixels / 1.5F
    }

    private fun fillCells(canvas: Canvas) {
        cells?.forEach {
            val r = it.row
            val c = it.col

            when {
                it.isStartingCell -> {
                    fillCell(canvas, r, c, startingCellPaint)
                }
                r == selectedRow && c == selectedCol -> {
                    // Selected Cell
                    fillCell(canvas, r, c, selectedCellPaint)
                }
                r == selectedRow || c == selectedCol -> {
                    // Other cells in Selected Row or Columns
                    fillCell(canvas, r, c, conflictingCellPaint)
                }
                r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize -> {
                    // Other cells in Selected Box
                    fillCell(canvas, r, c, conflictingCellPaint)
                }
            }
        }
    }

    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint) {
        canvas.drawRect(
            c * cellSizePixels,
            r * cellSizePixels,
            (c + 1) * cellSizePixels,
            (r + 1) * cellSizePixels,
            paint
        )
    }

    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)

        for (i in 1 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }

            // Draw Vertical Line
            canvas.drawLine(
                i * cellSizePixels,
                0F,
                i * cellSizePixels,
                height.toFloat(),
                paintToUse
            )

            // Draw Horizontal Line
            canvas.drawLine(
                0F,
                i  * cellSizePixels,
                width.toFloat(),
                i  * cellSizePixels,
                paintToUse
            )

        }
    }

    private fun drawText(canvas: Canvas) {
        cells?.forEach { cell ->

            val value = cell.value

            val textBounds = Rect()

            if ( value == 0 ) {
                // Draw notes

                cell.notes.forEach { note ->
                    val rowInCell = (note - 1) / sqrtSize
                    val colInCell = (note - 1) % sqrtSize

                    val valueString = note.toString()

                    noteTextPaint.getTextBounds(valueString, 0, valueString.length, textBounds)
                    val textWidth = noteTextPaint.measureText(valueString)
                    val textHeight = textBounds.height()

                    canvas.drawText(
                        valueString,
                        (cell.col * cellSizePixels) + (colInCell * noteSizePixels) + (noteSizePixels / 2) - (textWidth / 2),
                        (cell.row * cellSizePixels) + (rowInCell * noteSizePixels) + (noteSizePixels / 2) + (textHeight / 2),
                        noteTextPaint
                    )

                }

            } else {
                val row = cell.row
                val col = cell.col

                val valueString = cell.value.toString()

                val paintToUse = if (cell.isStartingCell) startingCellTextPaint else textPaint

                paintToUse.getTextBounds(valueString, 0, valueString.length, textBounds)
                val textWidth = paintToUse.measureText(valueString)
                val textHeight = textBounds.height()

                /**
                 * startX = startX of Box + half of box size - half of textWidth to be printed in center
                 * startY = startY of Box + half of box size - half of textHeight to be printed in center
                 */
                canvas.drawText(
                    valueString,
                    (col * cellSizePixels) + (cellSizePixels / 2) - (textWidth / 2),
                    (row * cellSizePixels) + (cellSizePixels / 2) + (textHeight / 2),
                    paintToUse
                )
            }

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        val possibleSelectedRow = ( y / cellSizePixels ).toInt()
        val possibleSelectedCol = ( x / cellSizePixels ).toInt()
        listener?.onCellTouched(possibleSelectedRow, possibleSelectedCol)
    }

    fun updateSelectedCellUI(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        invalidate()
    }

    fun updateCells(cells: List<Cell>) {
        this.cells = cells
        invalidate()
    }

    fun clearAllCells() {
        this.cells = null
        invalidate()
    }

    fun registerListener(listener: OnTouchListener) {
        this.listener = listener
    }

    interface OnTouchListener {
        fun onCellTouched(row: Int, col: Int)
    }
}