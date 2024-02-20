package us.timinc.mc.cobblemon.capturexp.command

import us.timinc.mc.command.Command
import us.timinc.mc.command.CommandBranch

object Commands : Command("capturexp") {
    override fun getBranches(): List<CommandBranch> {
        return listOf(
            ReloadCommand
        )
    }
}