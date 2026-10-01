package hu.bme.aut.tictactoe.ui.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.tictactoe.R
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import kotlin.collections.plus


@Composable
fun TicTacToeGameScreen(modifier: Modifier,
    viewModel: TicTacToeViewModel = viewModel()
) {
    val zoomState = rememberZoomState()

    Column(
        modifier = modifier.fillMaxSize()
            .zoomable(zoomState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tic Tac Toe Game")

        Button(
            onClick = {
                viewModel.resetGame()
            }
        ) {
            Text(stringResource(R.string.button_reset))
        }

        Text(text = "Current player: ${viewModel.currentPlayer}")

        TicTacToeBoard(
            board = viewModel.board,
            onCellClicked = {
                viewModel.onCellClicked(it)
            }
        )
    }
}


@Composable
fun TicTacToeBoard(
    board: Array<Array<Player?>>,
    onCellClicked: (BoardCell) -> Unit
) {
    val imageBitmap: ImageBitmap
        = ImageBitmap.imageResource(R.drawable.grass)

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(1.0f) // adjust height to match with the width
            .pointerInput(key1 = Unit) {
                detectTapGestures { offSet ->
                    //Log.d("TAG_TAP",
                    //    "${offSet.x} - ${offSet.y}")

                    val row = (offSet.y / (size.height / 3)).toInt()
                    val col = (offSet.x / (size.width / 3)).toInt()
                    onCellClicked(BoardCell(row, col))
                }
            }
    ) {
        val gridSize = size.minDimension
        val thirdSize = gridSize / 3

        // --- Draw an image
        drawImage(
            imageBitmap,
            srcOffset = IntOffset(0,0),
            dstOffset = IntOffset(2*thirdSize.toInt(),
                thirdSize.toInt()),
            srcSize = IntSize(imageBitmap.width,
                imageBitmap.height),
            dstSize = IntSize(thirdSize.toInt(),
                thirdSize.toInt())
        )

        // --- Draw the grid
        for (i in 1..2) {
            drawLine(
                color = Color.Black,
                strokeWidth = 5f,
                start = Offset(thirdSize * i, 0f),
                end = Offset(thirdSize * i, gridSize)
            )

            drawLine(
                color = Color.Black,
                strokeWidth = 5f,
                start = Offset(0f, thirdSize * i),
                end = Offset(gridSize, thirdSize * i)
            )
        }

        // --- Draw text
        val textLayoutResult: TextLayoutResult =
            textMeasurer.measure(
                text = "3",
                style = TextStyle(fontSize = thirdSize.toSp(),
                    fontWeight = FontWeight.Bold)
            )
        val textSize = textLayoutResult.size
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                x  = thirdSize/2 - textSize.width/2,
                y = thirdSize/2 - textSize.height/2
            ),
        )

        for (i in 0..2) {
            for (j in 0..2) {
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x  = (thirdSize/2 + i*thirdSize) - textSize.width/2,
                        y = (thirdSize/2 + j*thirdSize) - textSize.height/2
                    ),
                )
            }
        }


        // --- Draw the players
        for (row in 0..2) {
            for (col in 0..2) {
                val player = board[row][col]
                if (player != null) {
                    val centerX = col * thirdSize + thirdSize / 2
                    val centerY = row * thirdSize + thirdSize / 2
                    if (player == Player.X) {
                        drawLine(
                            color = Color.Black,
                            strokeWidth = 8f,
                            pathEffect = PathEffect.cornerPathEffect(4f),
                            start = androidx.compose.ui.geometry.Offset(centerX - thirdSize / 4, centerY - thirdSize / 4),
                            end = androidx.compose.ui.geometry.Offset(centerX + thirdSize / 4, centerY + thirdSize / 4),
                        )
                        drawLine(
                            color = Color.Black,
                            strokeWidth = 8f,
                            pathEffect = PathEffect.cornerPathEffect(4f),
                            start = androidx.compose.ui.geometry.Offset(centerX + thirdSize / 4, centerY - thirdSize / 4),
                            end = androidx.compose.ui.geometry.Offset(centerX - thirdSize / 4, centerY + thirdSize / 4),
                        )
                    } else {
                        drawCircle(
                            color = Color.Black,
                            style = Stroke(width = 8f),
                            center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                            radius = thirdSize / 4,
                        )
                    }
                }
            }
        }

    }
}