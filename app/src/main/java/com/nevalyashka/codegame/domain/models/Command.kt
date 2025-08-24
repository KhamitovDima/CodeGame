
package com.nevalyashka.codegame.domain.models

sealed class Command {
    object MoveForward : Command()
    object TurnLeft : Command()
    object TurnRight : Command()
    data class FunctionCall(val functionId: String) : Command()
}
