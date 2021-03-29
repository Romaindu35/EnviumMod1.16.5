package fr.envium.enviummod.core.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.envium.enviummod.core.jobs.JobsEnum;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;
import java.util.Map;

public class JobsCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        /*LiteralCommandNode<CommandSource> cmdJobs = dispatcher.register(
                Commands.literal(References.MODID)
                    .then(JobsCommandsArgument.register(dispatcher))
        );
        dispatcher.register(Commands.literal("jobs")
                .then(JobsCommandsArgument.register(dispatcher))
                .then(Commands.argument("player", EntityArgument.player()))
                .then(Commands.argument("xp", IntegerArgumentType.integer()))
                .executes((x) -> {
                    return addJobs(x.getSource(), EntityArgument.getPlayer(x, "player"), IntegerArgumentType.getInteger(x, "xp"));
                }));*/
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("jobs")
                        .requires(src -> src.hasPermissionLevel(2))
                        .then(Commands.literal("add")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("MINER")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> addJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp"), JobsEnum.MINER))
                                                )
                                        )
                                        .then(Commands.literal("CHASSEUR")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> addJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp"), JobsEnum.CHASSEUR))
                                                )
                                        )
                                        .then(Commands.literal("LUMBERJACK")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> addJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp"), JobsEnum.LUMBERJACK))
                                                )
                                        )
                                        .then(Commands.literal("ALL")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> addAllJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp")))
                                                )
                                        )

                                )
                        )
                        .then(Commands.literal("set")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("MINER")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> setJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp"), JobsEnum.MINER))
                                                )
                                        )
                                        .then(Commands.literal("CHASSEUR")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> setJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp"), JobsEnum.CHASSEUR))
                                                )
                                        )
                                        .then(Commands.literal("LUMBERJACK")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> setJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp"), JobsEnum.LUMBERJACK))
                                                )
                                        )
                                        .then(Commands.literal("ALL")
                                                .then(Commands.argument("xp", IntegerArgumentType.integer(0)).executes(ctx -> setAllJobs(ctx.getSource(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "xp")))
                                                )
                                        )

                                )
                        )

        );
    }

    /*static class JobsCommandsArgument implements Command<CommandSource> {

        private static final JobsCommandsArgument CMD = new JobsCommandsArgument();

        public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
            return Commands.literal("add")
                    .requires(commandSource -> commandSource.hasPermissionLevel(0))
                    .executes(CMD);
        }



        @Override
        public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
            return 0;
        }
    }*/

    private static int addJobs(CommandSource source, ServerPlayerEntity serverPlayerEntity, int xp, JobsEnum jobs) {
        //serverPlayerEntity.sendMessage(new StringTextComponent("Vous gagne : " + xp + " xp au metier de " + jobs.toString()));
        /*try {
            BDD bdd = BDD.getInstance();
            Map<JobsEnum, Integer> jobsXpMap = new HashMap();
            jobsXpMap.put(jobs, xp);
            bdd.addJobs(serverPlayerEntity, jobsXpMap);

            source.asPlayer().sendMessage(new StringTextComponent(
                    TextFormatting.DARK_RED + "[Jobs]" + TextFormatting.RESET
                            + serverPlayerEntity.getName().getString() + " a gagne " + xp + " xp au metier de " + jobs.toString()), source.asPlayer().getUniqueID());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }*/
        return 1;
    }

    private static int setJobs(CommandSource source, ServerPlayerEntity serverPlayerEntity, int xp, JobsEnum jobs) {
        //serverPlayerEntity.sendMessage(new StringTextComponent("Vous avez renitialise votre metier a : " + xp + " xp au metier de " + jobs.toString()));
        /*try {
            BDD bdd = BDD.getInstance();
            Map<JobsEnum, Integer> jobsXpMap = new HashMap();
            jobsXpMap.put(jobs, xp);
            bdd.setJobs(serverPlayerEntity, jobsXpMap);

            source.asPlayer().sendMessage(new StringTextComponent(
                    TextFormatting.DARK_RED + "[Jobs]" + TextFormatting.RESET
                            + "L'xp de " + serverPlayerEntity.getName().getString() + " a ete reniatialise a " + xp + " xp dans les metier de " + jobs.toString()), source.asPlayer().getUniqueID());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }*/
        return 1;
    }

    private static int addAllJobs(CommandSource source, ServerPlayerEntity serverPlayerEntity, int xp) {
        //serverPlayerEntity.sendMessage(new StringTextComponent("Vous gagne : " + xp + " xp au metier de " + jobs.toString()));
        /*try {
            BDD bdd = BDD.getInstance();
            Map<JobsEnum, Integer> jobsXpMap = new HashMap();
            jobsXpMap.put(JobsEnum.MINER, xp);
            jobsXpMap.put(JobsEnum.CHASSEUR, xp);
            jobsXpMap.put(JobsEnum.LUMBERJACK, xp);
            bdd.addJobs(serverPlayerEntity, jobsXpMap);

            source.asPlayer().sendMessage(new StringTextComponent(
                    TextFormatting.DARK_RED + "[Jobs]" + TextFormatting.RESET
                            + serverPlayerEntity.getName().getString() + " a gagne " + xp + " xp dans tous ses metiers"), source.asPlayer().getUniqueID());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }*/
        return 1;
    }

    private static int setAllJobs(CommandSource source, ServerPlayerEntity serverPlayerEntity, int xp) {
        //serverPlayerEntity.sendMessage(new StringTextComponent("Vous avez renitialise votre metier a : " + xp + " xp au metier de " + jobs.toString()));
        /*try {
            BDD bdd = BDD.getInstance();
            Map<JobsEnum, Integer> jobsXpMap = new HashMap();
            jobsXpMap.put(JobsEnum.MINER, xp);
            jobsXpMap.put(JobsEnum.CHASSEUR, xp);
            jobsXpMap.put(JobsEnum.LUMBERJACK, xp);
            bdd.setJobs(serverPlayerEntity, jobsXpMap);

            source.asPlayer().sendMessage(new StringTextComponent(
                    TextFormatting.DARK_RED + "[Jobs]" + TextFormatting.RESET
                            + "L'xp de " + serverPlayerEntity.getName().getString() + " a ete reniatialise a " + xp + " xp dans tous les metiers "), source.asPlayer().getUniqueID());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }*/
        return 1;
    }
}
