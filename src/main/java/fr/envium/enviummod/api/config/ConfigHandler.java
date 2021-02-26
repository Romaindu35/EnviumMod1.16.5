package fr.envium.enviummod.api.config;

import com.google.common.collect.ImmutableList;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IOverlayStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IProbeConfig;
import fr.envium.enviummod.addons.probe.theoneprobe.api.NumberFormat;
import fr.envium.enviummod.addons.probe.theoneprobe.api.TextStyleClass;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.ProbeConfig;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.styles.DefaultOverlayStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.items.IEnumConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static fr.envium.enviummod.addons.probe.theoneprobe.api.TextStyleClass.*;

/*public class ConfigHandler {

    public static final ForgeConfigSpec SERVER_SPECS;
    public static final Server SERVER;

    public static final ForgeConfigSpec CLIENT_SPECS;
    public static final Client CLIENT;

    public static final ForgeConfigSpec COMMON_SPECS;
    public static final Common COMMON;


    public static boolean draw = true;
    public static int maxDistance = 24;
    public static boolean renderInF1 = false;
    public static double heightAbove = 0.6;
    public static boolean drawBackground = true;
    public static int backgroundPadding = 2;
    public static int backgroundHeight = 6;
    public static int barHeight = 4;
    public static int plateSize = 25;
    public static int plateSizeBoss = 50;
    public static boolean showAttributes = true;
    public static boolean showArmor = true;
    public static boolean groupArmor = true;
    public static boolean colorByType = false;
    public static int hpTextHeight = 14;
    public static boolean showMaxHP = true;
    public static boolean showCurrentHP = true;
    public static boolean showPercentage = true;
    public static boolean showOnPlayers = true;
    public static boolean showOnBosses = true;
    public static boolean showOnlyFocused = false;
    public static boolean showFullHealth = true;
    public static boolean enableDebugInfo = true;

    public static List<String> blacklist;

    static
    {
        Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPECS = serverPair.getRight();
        SERVER = serverPair.getLeft();

        Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPECS = clientPair.getRight();
        CLIENT = clientPair.getLeft();

        Pair<Common, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPECS = commonPair.getRight();
        COMMON = commonPair.getLeft();
    }

    public static class Server
    {

        public static ForgeConfigSpec.ConfigValue machine;
        public static ForgeConfigSpec.ConfigValue user;
        public static ForgeConfigSpec.ConfigValue mdp;


        public Server(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Configuration du server")
                    .push("server");

            machine = builder
                    .comment("Ip de la machine contenant la BDD")
                    .define("machine", "");

            user = builder
                    .comment("User qui utilise la bdd")
                    .define("user", "");

            mdp = builder
                    .comment("Mot de passe de l'utilisateur")
                    .define("mdp", "");

            builder.pop();
        }

    }

    public static class Client
    {
        private static ForgeConfigSpec.ConfigValue<Integer> v_maxDistance;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_renderInF1;
        private static ForgeConfigSpec.ConfigValue<Double> v_heightAbove;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_drawBackground;
        private static ForgeConfigSpec.ConfigValue<Integer> v_backgroundPadding;
        private static ForgeConfigSpec.ConfigValue<Integer> v_backgroundHeight;
        private static ForgeConfigSpec.ConfigValue<Integer> v_barHeight;
        private static ForgeConfigSpec.ConfigValue<Integer> v_plateSize;
        private static ForgeConfigSpec.ConfigValue<Integer> v_plateSizeBoss;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showAttributes;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showArmor;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_groupArmor;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_colorByType;
        private static ForgeConfigSpec.ConfigValue<Integer> v_hpTextHeight;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showMaxHP;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showCurrentHP;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showPercentage;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showOnPlayers;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showOnBosses;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showOnlyFocused;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_showFullHealth;
        private static ForgeConfigSpec.ConfigValue<Boolean> v_enableDebugInfo;
        private static ForgeConfigSpec.ConfigValue<List<? extends String>> v_blacklist;
        
        public Client(ForgeConfigSpec.Builder builder)
        {
            
            builder.comment("General configuration");

            v_maxDistance = builder.define("Max Distance", maxDistance);
            v_renderInF1 = builder.define("Render with Interface Disabled (F1)", renderInF1);
            v_heightAbove = builder.define("Height Above Mob", heightAbove);
            v_drawBackground = builder.define("Draw Background", drawBackground);
            v_backgroundPadding = builder.define("Background Padding", backgroundPadding);
            v_backgroundHeight = builder.define("Background Height", backgroundHeight);
            v_barHeight = builder.define("Health Bar Height", barHeight);
            v_plateSize = builder.define("Plate Size", plateSize);
            v_plateSizeBoss = builder.define("Plate Size (Boss)", plateSizeBoss);
            v_showAttributes = builder.define("Show Attributes", showAttributes);
            v_showArmor = builder.define("Show Armor", showArmor);
            v_groupArmor = builder.define("Group Armor (condense 5 iron icons into 1 diamond icon)", groupArmor);
            v_colorByType = builder.define("Color Health Bar by Type (instead of health percentage)", colorByType);
            v_hpTextHeight = builder.define("HP Text Height", hpTextHeight);
            v_showMaxHP = builder.define("Show Max HP", showMaxHP);
            v_showCurrentHP = builder.define("Show Current HP", showCurrentHP);
            v_showPercentage = builder.define("Show HP Percentage", showPercentage);
            v_showOnPlayers = builder.define("Display on Players", showOnPlayers);
            v_showOnBosses = builder.define("Display on Bosses", showOnBosses);
            v_showOnlyFocused = builder.define("Only show the health bar for the entity looked at", showOnlyFocused);
            v_showFullHealth = builder.define("Show entities with full health", showFullHealth);
            v_enableDebugInfo = builder.define("Show Debug Info with F3", enableDebugInfo);
            v_blacklist = builder.comment("Blacklist uses entity IDs, not their display names. Use F3 to see them in the Neat bar.")
                    .defineList("Blacklist",
                            ImmutableList.of("minecraft:shulker", "minecraft:armor_stand", "minecraft:cod", "minecraft:salmon", "minecraft:pufferfish", "minecraft:tropical_fish"),
                            a -> true);

            builder.pop();
        }

        public static void load() {
            maxDistance = v_maxDistance.get();
            renderInF1 = v_renderInF1.get();
            heightAbove = v_heightAbove.get();
            drawBackground = v_drawBackground.get();
            backgroundPadding = v_backgroundPadding.get();
            backgroundHeight = v_backgroundHeight.get();
            barHeight = v_barHeight.get();
            plateSize = v_plateSize.get();
            plateSizeBoss = v_plateSizeBoss.get();
            showAttributes = v_showAttributes.get();
            showArmor = v_showArmor.get();
            groupArmor = v_groupArmor.get();
            colorByType = v_colorByType.get();
            hpTextHeight = v_hpTextHeight.get();
            showMaxHP = v_showMaxHP.get();
            showCurrentHP = v_showCurrentHP.get();
            showPercentage = v_showPercentage.get();
            showOnPlayers = v_showOnPlayers.get();
            showOnBosses = v_showOnBosses.get();
            showOnlyFocused = v_showOnlyFocused.get();
            showFullHealth = v_showFullHealth.get();
            enableDebugInfo = v_enableDebugInfo.get();
            blacklist = (List<String>) v_blacklist.get();
        }
        
    }

    public static class Common
    {
        
        public Common(ForgeConfigSpec.Builder builder)
        {            
            builder.comment("Configuration du server")
                    .push("server");

            ConfigHandler.Server.machine = builder
                    .comment("Ip de la machine contenant la BDD")
                    .define("machine", "");

            ConfigHandler.Server.user = builder
                    .comment("User qui utilise la bdd")
                    .define("user", "");

            ConfigHandler.Server.mdp = builder
                    .comment("Mot de passe de l'utilisateur")
                    .define("mdp", "");
            builder.pop();
        }

    }
}*/
public class ConfigHandler {

    public static final ForgeConfigSpec SERVER_SPECS;
    public static final Server SERVER;

    public static final ForgeConfigSpec CLIENT_SPECS;
    public static final Client CLIENT;

    public static final ForgeConfigSpec COMMON_SPECS;
    public static final Common COMMON;

    static
    {
        Pair<Server, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPECS = serverPair.getRight();
        SERVER = serverPair.getLeft();

        Pair<Client, ForgeConfigSpec> clientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPECS = clientPair.getRight();
        CLIENT = clientPair.getLeft();

        Pair<Common, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPECS = clientPair.getRight();
        COMMON = commonPair.getLeft();
    }

    public static class Server
    {

        public final ForgeConfigSpec.ConfigValue machine;
        public final ForgeConfigSpec.ConfigValue user;
        public final ForgeConfigSpec.ConfigValue mdp;


        public Server(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Configuration du server")
                    .push("server");

            machine = builder
                    .comment("Ip de la machine contenant la BDD")
                    .define("machine", "");

            user = builder
                    .comment("User qui utilise la bdd")
                    .define("user", "");

            mdp = builder
                    .comment("Mot de passe de l'utilisateur")
                    .define("mdp", "");

            builder.pop();
        }

    }

    public static class Client
    {

        public Client(ForgeConfigSpec.Builder builder)
        {

        }

    }

    public static class Common
    {

        public Common(ForgeConfigSpec.Builder builder)
        {

        }

    }

}
