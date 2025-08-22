package com.nevalyashka.codegame.domain.model

data class Level(
    val id: Int,
    val board: Board,
    val startPlayerState: PlayerState,
    val availableFunctions: List<String>
)