package fr.envium.enviummod.core.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.envium.enviummod.References;
import fr.envium.enviummod.api.utils.ChunkUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.chunk.storage.ChunkLoaderUtil;
import net.minecraft.world.server.ServerWorld;

public class CommandRegenChunk {

    // private static final String PERMISSION = "pyrion.command.regen";

    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        /*dispatcher.register(Commands.literal("regenChunk").requires((commandSource) ->
        {
            return commandSource.hasPermissionLevel(4);
        }).executes(source ->
        {
            return regenChunk(source.getSource()) ? 1 : 0;
        }));*/
        dispatcher.register(Commands.literal("regenChunk")
                .requires(commandSource -> commandSource.hasPermissionLevel(4))
                .executes(context -> regenChunk(context.getSource()))
        );
    }

    private static int regenChunk(CommandSource sender) throws CommandException
    {
        ServerPlayerEntity player = null;

        try
        {
            player = sender.asPlayer();
        }
        catch (CommandSyntaxException e)
        {
            e.printStackTrace();
            return 0;
        }

        ServerWorld world = player.getServerWorld();
        int x = player.chunkCoordX;
        int z = player.chunkCoordZ;
        System.out.println("Regen Chunk - Commande execute");
        ChunkUtils.regenerateChunk(world, x, z);


        return 1;
    }
}
