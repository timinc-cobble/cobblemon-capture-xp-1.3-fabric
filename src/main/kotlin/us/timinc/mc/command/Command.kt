package us.timinc.mc.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.CommandNode
import net.minecraft.commands.CommandSourceStack

abstract class Command(private val prefix: String) {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val built = build()
        var last: CommandNode<CommandSourceStack> = dispatcher.root
        val main = literal<CommandSourceStack>(prefix)
        if (built.isEmpty()) {
            main.executes { ctx -> execute(ctx) }
        }
        val mb = main.build()
        last.addChild(mb)
        last = mb
        for (i in built.indices) {
            val nb = built.elementAt(i)
            val next = (if (i == built.size - 1) nb.executes { ctx -> execute(ctx) }
                .build() else nb.build()) as CommandNode<CommandSourceStack>
            last.addChild(next)
            last = next
        }

        val branches = getBranches()
        for (i in branches.indices) {
            val branch = branches.elementAt(i)
            branch.register(last)
        }
    }

    open fun build(): List<ArgumentBuilder<CommandSourceStack, *>> {
        return listOf()
    }

    open fun getBranches(): List<CommandBranch> {
        return listOf()
    }

    open fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        return 0
    }
}

abstract class CommandBranch {
    fun register(extLast: CommandNode<CommandSourceStack>) {
        var last = extLast
        val built = build()
        for (i in built.indices) {
            val nb = built.elementAt(i)
            val next = (if (i == built.size - 1) nb.executes { ctx -> execute(ctx) }
                .build() else nb.build()) as CommandNode<CommandSourceStack>
            last.addChild(next)
            last = next
        }

        val branches = getBranches()
        for (i in branches.indices) {
            val branch = branches.elementAt(i)
            branch.register(last)
        }
    }

    open fun build(): List<ArgumentBuilder<CommandSourceStack, *>> {
        return listOf()
    }

    open fun getBranches(): List<CommandBranch> {
        return listOf()
    }

    open fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        return 0
    }
}