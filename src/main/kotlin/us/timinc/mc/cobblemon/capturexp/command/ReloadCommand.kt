package us.timinc.mc.cobblemon.capturexp.command

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import us.timinc.mc.cobblemon.capturexp.CaptureXP
import us.timinc.mc.command.CommandBranch

object ReloadCommand : CommandBranch() {
    override fun build(): List<ArgumentBuilder<CommandSourceStack, *>> {
        return listOf(
            LiteralArgumentBuilder.literal("reload")
        )
    }

    override fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        CaptureXP.reloadConfig()
        println("Howdy")
        return 1
    }
}