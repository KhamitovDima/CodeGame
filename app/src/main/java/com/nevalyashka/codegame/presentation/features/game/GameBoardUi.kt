package com.nevalyashka.codegame.presentation.features.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
// Removed: import androidx.compose.ui.graphics.Color - It's not directly used, colors come from theme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nevalyashka.codegame.domain.models.Board
import com.nevalyashka.codegame.domain.models.CellType
import com.nevalyashka.codegame.domain.models.Direction
import com.nevalyashka.codegame.domain.models.PlayerState
import com.nevalyashka.codegame.presentation.theme.* // Import all colors and Theme

@Composable
fun GameBoardView(
    board: Board,
    playerState: PlayerState,
    modifier: Modifier = Modifier,
    cellSize: Int = 32 // Size of each cell in dp
) {
    Column(modifier = modifier) {
        board.grid.forEachIndexed { y, rowList ->
            Row {
                rowList.forEachIndexed { x, cellType ->
                    val cellColor = when (cellType) {
                        CellType.PATH -> PathColor
                        CellType.WALL -> WallColor
                        CellType.START -> StartColor
                        CellType.FINISH -> FinishColor
                    }
                    Box(
                        modifier = Modifier
                            .size(cellSize.dp)
                            .background(cellColor),
                        contentAlignment = Alignment.Center
                    ) {
                        if (playerState.x == x && playerState.y == y) {
                            val playerRotation = when (playerState.direction) {
                                Direction.NORTH -> -90f
                                Direction.EAST -> 0f
                                Direction.SOUTH -> 90f
                                Direction.WEST -> 180f
                            }
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "Player",
                                tint = PlayerColor, // PlayerColor from theme
                                modifier = Modifier.rotate(playerRotation)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameBoardPreview() {
    CodeGameTheme { // Assuming your theme is CodeGameTheme, imported via wildcard
        val previewBoard = Board(
            grid = listOf(
                listOf(CellType.START, CellType.PATH, CellType.WALL),
                listOf(CellType.PATH, CellType.PATH, CellType.FINISH),
                listOf(CellType.WALL, CellType.PATH, CellType.PATH)
            )
        )
        val previewPlayerState = PlayerState(x = 0, y = 0, direction = Direction.EAST)
        GameBoardView(board = previewBoard, playerState = previewPlayerState)
    }
}

@Preview(showBackground = true, name = "Player at Finish")
@Composable
fun GameBoardPlayerAtFinishPreview() {
    CodeGameTheme {
        val previewBoard = Board(
            grid = listOf(
                listOf(CellType.START, CellType.PATH, CellType.WALL),
                listOf(CellType.PATH, CellType.PATH, CellType.FINISH),
                listOf(CellType.WALL, CellType.PATH, CellType.PATH)
            )
        )
        // Player at (2,1) which is FINISH, facing SOUTH
        val previewPlayerState = PlayerState(x = 2, y = 1, direction = Direction.SOUTH)
        GameBoardView(board = previewBoard, playerState = previewPlayerState, cellSize = 48)
    }
}

@Preview(showBackground = true, name = "Larger Board")
@Composable
fun GameBoardLargerPreview() {
    CodeGameTheme {
        val previewBoard = Board(
            grid = listOf(
                listOf(CellType.START, CellType.PATH, CellType.PATH, CellType.WALL, CellType.PATH),
                listOf(CellType.PATH, CellType.WALL, CellType.PATH, CellType.PATH, CellType.PATH),
                listOf(CellType.PATH, CellType.PATH, CellType.PATH, CellType.WALL, CellType.FINISH),
                listOf(CellType.WALL, CellType.PATH, CellType.WALL, CellType.PATH, CellType.PATH),
                listOf(CellType.PATH, CellType.PATH, CellType.PATH, CellType.PATH, CellType.PATH)
            )
        )
        val previewPlayerState = PlayerState(x = 0, y = 0, direction = Direction.EAST)
        GameBoardView(board = previewBoard, playerState = previewPlayerState, cellSize = 24)
    }
}