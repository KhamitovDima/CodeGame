
package com.nevalyashka.codegame.presentation.features.game

import androidx.lifecycle.ViewModel
import com.nevalyashka.codegame.domain.engine.CommandProcessor
import com.nevalyashka.codegame.domain.models.Board
import com.nevalyashka.codegame.domain.models.Command
import com.nevalyashka.codegame.domain.models.GameFunction
import com.nevalyashka.codegame.domain.models.Level
import com.nevalyashka.codegame.domain.models.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private val _boardState = MutableStateFlow<Board?>(null)
    val boardState: StateFlow<Board?> = _boardState.asStateFlow()

    private val _playerState = MutableStateFlow<PlayerState?>(null)
    val playerState: StateFlow<PlayerState?> = _playerState.asStateFlow()

    private val _availableCommands = MutableStateFlow<List<Command>>(emptyList())
    val availableCommands: StateFlow<List<Command>> = _availableCommands.asStateFlow()

    private val _programCommands = MutableStateFlow<List<Command>>(emptyList())
    val programCommands: StateFlow<List<Command>> = _programCommands.asStateFlow()

    private val _gameFunctions = MutableStateFlow<Map<String, GameFunction>>(emptyMap())
    val gameFunctions: StateFlow<Map<String, GameFunction>> = _gameFunctions.asStateFlow()

    private var initialPlayerState: PlayerState? = null
    private var currentLevel: Level? = null

    fun initLevel(level: Level) {
        currentLevel = level
        _boardState.value = level.board
        initialPlayerState = level.startPlayerState
        _playerState.value = initialPlayerState
        _availableCommands.value = listOf(
            Command.MoveForward,
            Command.TurnLeft,
            Command.TurnRight,
            Command.FunctionCall("F1"), // Пример
            Command.FunctionCall("F2")  // Пример
        )
        // Инициализация функций на основе level.availableFunctions
        val initialFunctions = level.availableFunctions.associateWith { id ->
            GameFunction(id = id, commands = emptyList())
        }
        _gameFunctions.value = initialFunctions
        _programCommands.value = emptyList()
    }

    fun addCommandToProgram(command: Command) {
        _programCommands.value = _programCommands.value + command
    }

    fun addCommandToFunction(functionId: String, command: Command) {
        _gameFunctions.value = _gameFunctions.value.toMutableMap().apply {
            this[functionId] = this[functionId]?.copy(commands = this[functionId]!!.commands + command) ?: GameFunction(functionId, listOf(command))
        }
    }

    fun runProgram() {
        val board = _boardState.value ?: return
        val playerState = _playerState.value ?: return
        val program = _programCommands.value
        val functions = _gameFunctions.value

        val finalPlayerState = CommandProcessor.processCommands(
            board = board,
            initialPlayerState = playerState,
            commands = program,
            availableFunctions = functions
        )
        _playerState.value = finalPlayerState
    }

    fun resetPlayerState() {
        _playerState.value = initialPlayerState
    }
}
