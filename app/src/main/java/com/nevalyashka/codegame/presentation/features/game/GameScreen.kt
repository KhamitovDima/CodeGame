package com.nevalyashka.codegame.presentation.features.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nevalyashka.codegame.domain.models.Board
import com.nevalyashka.codegame.domain.models.CellType
import com.nevalyashka.codegame.domain.models.Command
import com.nevalyashka.codegame.domain.models.Direction
import com.nevalyashka.codegame.domain.models.GameFunction
import com.nevalyashka.codegame.domain.models.Level
import com.nevalyashka.codegame.domain.models.PlayerState
import com.nevalyashka.codegame.presentation.theme.CodeGameTheme

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val boardState by gameViewModel.boardState.collectAsState()
    val playerState by gameViewModel.playerState.collectAsState()
    val availableCommands by gameViewModel.availableCommands.collectAsState()
    val programCommands by gameViewModel.programCommands.collectAsState()
    val gameFunctions by gameViewModel.gameFunctions.collectAsState()

    // Temporary: Initialize with a test level if not already done
    // In a real app, level loading would be handled more robustly
    LaunchedEffect(Unit) {
        if (boardState == null) {
            val testLevel = Level(
                id = 1,
                board = Board(
                    grid = listOf(
                        listOf(CellType.START, CellType.PATH, CellType.WALL),
                        listOf(CellType.PATH, CellType.PATH, CellType.PATH),
                        listOf(CellType.WALL, CellType.PATH, CellType.FINISH)
                    )
                ),
                startPlayerState = PlayerState(x = 0, y = 0, direction = Direction.EAST),
                availableFunctions = listOf("F1", "F2") // Represents IDs for GameFunction
            )
            gameViewModel.initLevel(testLevel)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            boardState?.let { board ->
                playerState?.let { player ->
                    GameBoardView(board = board, playerState = player)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CommandPaletteView(
                availableCommands = availableCommands,
                onCommandClick = { command ->
                    gameViewModel.addCommandToProgram(command)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Program:")
            ProgramEditorView(
                programCommands = programCommands,
                //onCommandClick = { /* TODO: Handle click/remove from program */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Example: Displaying one function editor, could be dynamic
            gameFunctions["F1"]?.let { functionF1 ->
                Text("Function F1:")
                FunctionEditorView(
                    gameFunction = functionF1, // Передаем весь объект GameFunction
                    // modifier = Modifier, // modifier можно опустить, если не используется специфичный
                    onCommandClick = { command, index ->
                        // TODO: Handle click/remove from function,
                        // command and index are available here
                    }
                )
            }


            Spacer(modifier = Modifier.weight(1f)) // Pushes buttons to the bottom

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { gameViewModel.runProgram() }) {
                    Text("Run Program")
                }
                Button(onClick = { gameViewModel.resetPlayerState() }) {
                    Text("Reset Player")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    CodeGameTheme {
        // ViewModel instance for preview (can be a fake/mock if complex)
        val previewViewModel = GameViewModel()
        // Initialize with some test data directly or via a method in ViewModel
        val testLevel = Level(
            id = 1,
            board = Board(
                grid = listOf(
                    listOf(CellType.START, CellType.PATH, CellType.WALL),
                    listOf(CellType.PATH, CellType.PATH, CellType.PATH),
                    listOf(CellType.WALL, CellType.PATH, CellType.FINISH)
                )
            ),
            startPlayerState = PlayerState(x = 0, y = 0, direction = Direction.EAST),
            availableFunctions = listOf("F1")
        )
        previewViewModel.initLevel(testLevel)
        // Add some commands to program for preview
        previewViewModel.addCommandToProgram(Command.MoveForward)
        previewViewModel.addCommandToProgram(Command.TurnLeft)

        GameScreen(gameViewModel = previewViewModel)
    }
}
