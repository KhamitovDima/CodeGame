package com.nevalyashka.codegame.domain.engine

import com.nevalyashka.codegame.domain.models.Board
import com.nevalyashka.codegame.domain.models.Command
import com.nevalyashka.codegame.domain.models.GameFunction
import com.nevalyashka.codegame.domain.models.PlayerState

class CommandProcessor {

    fun processCommands(
        board: Board,
        initialPlayerState: PlayerState,
        commands: List<Command>,
        availableFunctions: Map<String, GameFunction>
    ): PlayerState {
        return initialPlayerState
    }
}
