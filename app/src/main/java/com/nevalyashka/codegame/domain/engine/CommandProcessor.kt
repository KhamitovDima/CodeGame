package com.nevalyashka.codegame.domain.engine

import com.nevalyashka.codegame.domain.models.Board
import com.nevalyashka.codegame.domain.models.CellType
import com.nevalyashka.codegame.domain.models.Command
import com.nevalyashka.codegame.domain.models.Direction
import com.nevalyashka.codegame.domain.models.GameFunction
import com.nevalyashka.codegame.domain.models.PlayerState

object CommandProcessor {

    fun processCommands(
        board: Board,
        initialPlayerState: PlayerState,
        commands: List<Command>,
        availableFunctions: Map<String, GameFunction>
    ): PlayerState {
        var currentPlayerState = initialPlayerState

        commands.forEach { command ->
            when (command) {
                is Command.MoveForward -> {
                    val nextX = when (currentPlayerState.direction) {
                        Direction.NORTH -> currentPlayerState.x
                        Direction.EAST -> currentPlayerState.x + 1
                        Direction.SOUTH -> currentPlayerState.x
                        Direction.WEST -> currentPlayerState.x - 1
                    }
                    val nextY = when (currentPlayerState.direction) {
                        Direction.NORTH -> currentPlayerState.y - 1
                        Direction.EAST -> currentPlayerState.y
                        Direction.SOUTH -> currentPlayerState.y + 1
                        Direction.WEST -> currentPlayerState.y
                    }

                    // Check bounds and if it's a wall
                    if (nextX >= 0 && nextX < board.grid[0].size &&
                        nextY >= 0 && nextY < board.grid.size &&
                        board.grid[nextY][nextX] != CellType.WALL
                    ) {
                        currentPlayerState = currentPlayerState.copy(x = nextX, y = nextY)
                    }
                    // If it's a wall or out of bounds, player state doesn't change for this move
                }
                is Command.TurnLeft -> {
                    currentPlayerState = currentPlayerState.copy(
                        direction = when (currentPlayerState.direction) {
                            Direction.NORTH -> Direction.WEST
                            Direction.EAST -> Direction.NORTH
                            Direction.SOUTH -> Direction.EAST
                            Direction.WEST -> Direction.SOUTH
                        }
                    )
                }
                is Command.TurnRight -> {
                    currentPlayerState = currentPlayerState.copy(
                        direction = when (currentPlayerState.direction) {
                            Direction.NORTH -> Direction.EAST
                            Direction.EAST -> Direction.SOUTH
                            Direction.SOUTH -> Direction.WEST
                            Direction.WEST -> Direction.NORTH
                        }
                    )
                }
                is Command.FunctionCall -> {
                    val function = availableFunctions[command.functionId]
                    if (function != null) {
                        currentPlayerState = processCommands(board, currentPlayerState, function.commands, availableFunctions)
                    }
                    // If function not found, ignore (or throw an error in a more robust system)
                }
            }
        }
        return currentPlayerState
    }
}
