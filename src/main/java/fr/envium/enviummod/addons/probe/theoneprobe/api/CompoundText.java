package fr.envium.enviummod.addons.probe.theoneprobe.api;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CompoundText {

    private ITextComponent component = null;

    public static CompoundText create() {
        return new CompoundText();
    }

    /**
     * Conveniance for the common case where a label and info is required
     * Equivalent to CompoundText.create().labelInfo(label, value)
     */
    public static CompoundText createLabelInfo(String label, Object value) {
        return CompoundText.create().labelInfo(label, value);
    }

    /// Use this to set a style that the player can configure client-side. This is the recommended way to do text formatting
    public CompoundText style(TextStyleClass style) {
        ITextComponent cmp = new StringTextComponent(style.toString());
        return newComponent(cmp);
    }

    /// The prefered way to do translatable text
    public CompoundText text(ITextComponent cmp) {
        return newComponent(cmp);
    }

    /// Only use this for small strings or numbers for which no translation is useful
    public CompoundText text(String text) {
        return newComponent(new StringTextComponent(text));
    }

    /// A common usage: label + info
    public CompoundText labelInfo(String label, Object value) {
        return style(TextStyleClass.LABEL).text(label).style(TextStyleClass.INFO).text(String.valueOf(value));
    }

    /// Shorthand for style(TextStyleClass.INFO).text(new TranslationTextComponent(translationKey):
    public CompoundText info(String translationKey) {
        return style(TextStyleClass.INFO).text(new TranslationTextComponent(translationKey));
    }

    /// Shorthand for style(TextStyleClass.INFOIMP).text(new TranslationTextComponent(translationKey):
    public CompoundText important(String translationKey) {
        return style(TextStyleClass.INFOIMP).text(new TranslationTextComponent(translationKey));
    }

    /// Shorthand for style(TextStyleClass.WARNING).text(new TranslationTextComponent(translationKey):
    public CompoundText warning(String translationKey) {
        return style(TextStyleClass.WARNING).text(new TranslationTextComponent(translationKey));
    }

    /// Shorthand for style(TextStyleClass.ERROR).text(new TranslationTextComponent(translationKey):
    public CompoundText error(String translationKey) {
        return style(TextStyleClass.ERROR).text(new TranslationTextComponent(translationKey));
    }

    /// Shorthand for style(TextStyleClass.LABEL).text(new TranslationTextComponent(translationKey):
    public CompoundText label(String translationKey) {
        return style(TextStyleClass.LABEL).text(new TranslationTextComponent(translationKey));
    }

    /// Shorthand for style(TextStyleClass.OK).text(new TranslationTextComponent(translationKey):
    public CompoundText ok(String translationKey) {
        return style(TextStyleClass.OK).text(new TranslationTextComponent(translationKey));
    }

    /// Shorthand for style(TextStyleClass.NAME).text(new TranslationTextComponent(translationKey):
    public CompoundText name(String translationKey) {
        return style(TextStyleClass.NAME).text(new TranslationTextComponent(translationKey));
    }

    /// Shorthand for style(TextStyleClass.PROGRESS).text(new TranslationTextComponent(translationKey):
    public CompoundText progress(String translationKey) {
        return style(TextStyleClass.PROGRESS).text(new TranslationTextComponent(translationKey));
    }

    private CompoundText newComponent(ITextComponent cmp) {
        if (component == null) {
            component = cmp;
        } else {
            component.appendSibling(cmp);
        }
        return this;
    }

    public ITextComponent get() {
        return component;
    }
}
