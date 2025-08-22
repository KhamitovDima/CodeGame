package com.nevalyashka.codegame.domain.model

sealed interface Command {
    object MoveForward : Command
    object TurnLeft : Command
    object TurnRight : Command
    data class FunctionCall(val functionId: String) : Command
}