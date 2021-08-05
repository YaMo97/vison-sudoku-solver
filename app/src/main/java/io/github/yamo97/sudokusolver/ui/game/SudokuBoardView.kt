package io.github.yamo97.sudokusolver.ui.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val sqrtSize = 3
    private val size = 9

    private var cellSizePixels = 0F

    private var selectedRow = 4
    private var selectedCol = 4

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = widthMeasureSpec.coerceAtMost(heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        cellSizePixels = (width / size).toFloat()

        fillCells(canvas)
        drawLines(canvas)
    }

    private fun fillCells(canvas: Canvas) {
        if (selectedRow == -1 || selectedCol == -1) return

        for (r in 0..size) {
            for (c in 0..size) {
                if ( r == selectedRow && c == selectedCol ) {
                    // Selected Cell
                    fillCell(canvas, r, c, selectedCellPaint)
                } else if (r == selectedRow || c == selectedCol) {
                    // Other cells in Selected Row or Columns
                    fillCell(canvas, r, c, conflictingCellPaint)
                } else if ( r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize ) {
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
        selectedRow = ( y / cellSizePixels ).toInt()
        selectedCol = ( x / cellSizePixels ).toInt()
        invalidate()
    }
}