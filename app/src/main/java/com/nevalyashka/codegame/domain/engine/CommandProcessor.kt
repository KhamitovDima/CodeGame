package com.nevalyashka.codegame.domain.engine

import com.nevalyashka.codegame.domain.model.Board
import com.nevalyashka.codegame.domain.model.Command
import com.nevalyashka.codegame.domain.model.GameFunction
import com.nevalyashka.codegame.domain.model.PlayerState

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
