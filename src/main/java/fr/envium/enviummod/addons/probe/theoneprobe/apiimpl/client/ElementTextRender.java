package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.client;

import fr.envium.enviummod.addons.probe.theoneprobe.api.TextStyleClass;
import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;
import fr.envium.enviummod.addons.probe.theoneprobe.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.Set;

import static fr.envium.enviummod.addons.probe.theoneprobe.api.IProbeInfo.ENDLOC;
import static fr.envium.enviummod.addons.probe.theoneprobe.api.IProbeInfo.STARTLOC;

public class ElementTextRender {

    public static void render(String text, int x, int y) {
        RenderHelper.renderText(Minecraft.getInstance(), x, y, stylifyString(text));
    }

    public static void render(ITextComponent text, int x, int y) {
        RenderHelper.renderText(Minecraft.getInstance(), x, y, stylifyString(text));
    }

    private static String stylifyString(ITextComponent text) {
        return stylifyString(text.getFormattedText());
    }

    private static String stylifyString(String text) {
        while (text.contains(STARTLOC) && text.contains(ENDLOC)) {
            int start = text.indexOf(STARTLOC);
            int end = text.indexOf(ENDLOC);
            if (start < end) {
                // Translation is needed
                String left = text.substring(0, start);
                String middle = text.substring(start + 2, end);
                middle = I18n.format(middle).trim();
                String right = text.substring(end+2);
                text = left + middle + right;
            } else {
                break;
            }
        }
        if (text.contains("{=")) {
            Set<TextStyleClass> stylesNeedingContext = EnumSet.noneOf(TextStyleClass.class);
            TextStyleClass context = null;
            for (TextStyleClass styleClass : Config.textStyleClasses.keySet()) {
                if (text.contains(styleClass.toString())) {
                    String replacement = Config.getTextStyle(styleClass);
                    if ("context".equals(replacement)) {
                        stylesNeedingContext.add(styleClass);
                    } else if (context == null) {
                        context = styleClass;
                        text = StringUtils.replace(text, styleClass.toString(), replacement);
                    } else {
                        text = StringUtils.replace(text, styleClass.toString(), replacement);
                    }
                }
            }
            if (context != null) {
                for (TextStyleClass styleClass : stylesNeedingContext) {
                    String replacement = Config.getTextStyle(context);
                    text = StringUtils.replace(text, styleClass.toString(), replacement);
                }
            }
        }
        return text;
    }

    public static int getWidth(String text) {
        return Minecraft.getInstance().fontRenderer.getStringWidth(stylifyString(text));
    }

    public static int getWidth(ITextComponent text) {
        return Minecraft.getInstance().fontRenderer.getStringWidth(stylifyString(text));
    }
}
