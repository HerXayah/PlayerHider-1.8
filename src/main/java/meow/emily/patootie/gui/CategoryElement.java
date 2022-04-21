package meow.emily.patootie.gui;

import net.labymod.settings.SettingsCategory;

public class CategoryElement extends SettingsCategory {

    private final String modTitle;

    public CategoryElement(String title) {
        super(title);
        this.modTitle = title;
    }

    public String getTitle() {
        return this.modTitle;
    }
}