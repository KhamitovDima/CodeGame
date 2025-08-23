package com.nevalyashka.codegame.presentation.features.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nevalyashka.codegame.domain.models.Command
import com.nevalyashka.codegame.domain.models.GameFunction

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CommandPaletteView(
    availableCommands: List<Command>,
    modifier: Modifier = Modifier,
    onCommandClick: (Command) -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Доступные команды",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 4
            ) {
                availableCommands.forEach { command ->
                    TextButton(
                        onClick = { onCommandClick(command) },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(commandToString(command))
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramEditorView(
    programCommands: List<Command>,
    modifier: Modifier = Modifier,
    onCommandClick: (Command, Int) -> Unit = { _, _ -> } // For potential reordering/deletion
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Основная программа",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (programCommands.isEmpty()) {
                Text("Перетащите команды сюда", style = MaterialTheme.typography.bodySmall)
            } else {
                programCommands.forEachIndexed { index, command ->
                    TextButton(onClick = { onCommandClick(command, index) }) {
                        Text("${index + 1}. ${commandToString(command)}")
                    }
                }
            }
        }
    }
}

@Composable
fun FunctionEditorView(
    gameFunction: GameFunction,
    modifier: Modifier = Modifier,
    onCommandClick: (Command, Int) -> Unit = { _, _ -> } // For potential reordering/deletion
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Функция ${gameFunction.id}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (gameFunction.commands.isEmpty()) {
                Text("Перетащите команды сюда", style = MaterialTheme.typography.bodySmall)
            } else {
                gameFunction.commands.forEachIndexed { index, command ->
                    TextButton(onClick = { onCommandClick(command, index) }) {
                        Text("${index + 1}. ${commandToString(command)}")
                    }
                }
            }
        }
    }
}

// Helper function to convert Command to String for display
fun commandToString(command: Command): String {
    return when (command) {
        is Command.MoveForward -> "Вперед"
        is Command.TurnLeft -> "Влево"
        is Command.TurnRight -> "Вправо"
        is Command.FunctionCall -> "Вызвать ${command.functionId}"
    }
}

@Preview(showBackground = true)
@Composable
fun CommandPalettePreview() {
    // Assuming you have CodeGameTheme in your theme package
    com.nevalyashka.codegame.presentation.theme.CodeGameTheme {
        CommandPaletteView(
            availableCommands = listOf(
                Command.MoveForward,
                Command.TurnLeft,
                Command.TurnRight,
                Command.FunctionCall("F1")
            ),
            onCommandClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgramEditorPreview() {
    com.nevalyashka.codegame.presentation.theme.CodeGameTheme {
        Column {
            ProgramEditorView(
                programCommands = listOf(
                    Command.MoveForward,
                    Command.TurnLeft,
                    Command.MoveForward,
                    Command.FunctionCall("F1")
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProgramEditorView(programCommands = emptyList())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FunctionEditorPreview() {
    com.nevalyashka.codegame.presentation.theme.CodeGameTheme {
        Column {
            FunctionEditorView(
                gameFunction = GameFunction(
                    id = "F1",
                    commands = listOf(
                        Command.MoveForward,
                        Command.TurnRight,
                        Command.MoveForward
                    )
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            FunctionEditorView(
                gameFunction = GameFunction(id = "F2", commands = emptyList())
            )
        }
    }
}