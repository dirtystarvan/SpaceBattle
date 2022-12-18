package ioc

import commands.ICommand

internal class SetupStrategyCommand(private val newStrategy: Strategy): ICommand {
    override fun execute() {
        IoCC.strategy = newStrategy
    }
}
