package fr.envium.enviummod.api.client;

public class DiscordInitialise {

    private static final DiscordInitialise INSTANCE = new DiscordInitialise();
    private static final DiscordIntegration discordIntegration = new DiscordIntegration();

    public void init() {
        discordIntegration.start("Envium", "Chargement de forge...");
    }

    public void shutdown() {

    }

    public DiscordIntegration getDiscordRp() {
        return discordIntegration;
    }

    public static final DiscordInitialise getInstance() {
        return INSTANCE;
    }

}
