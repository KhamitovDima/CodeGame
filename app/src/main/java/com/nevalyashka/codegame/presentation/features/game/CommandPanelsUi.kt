package com.nevalyashka.codegame.presentation.features.game

import androidx.compose.runtime.Composable
import com.nevalyashka.codegame.domain.models.Command

@Composable
fun CommandPaletteView(
    availableCommands: List<Command>
) {
}

@Composable
fun ProgramEditorView(
    programCommands: List<Command>
) {
}

@Composable
fun FunctionEditorView(
    functionId: String,
    functionCommands: List<Command>
) {
}
