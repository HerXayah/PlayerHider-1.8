package meow.emily.patootie;

//import meow.emily.patootie.events.PlayerEventHandler;

import com.google.gson.JsonObject;
import meow.emily.patootie.events.PlayerEventHandler;
import meow.emily.patootie.util.TickScheduler;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;

import java.util.List;
import java.util.logging.Logger;

public class Emily extends LabyModAddon {

    private static final Logger LOGGER = Logger.getLogger("PlayerHider");
    private static final String PREFIX = "[PH] ";

    private static Emily instance;

    private boolean renderPlayers = true;
    private String playersToRender = "";
    private String blacklistedPlayers = "";
    private int key;

    public static Emily getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        LOGGER.log(java.util.logging.Level.INFO, PREFIX + "Enabled");

        api.registerForgeListener(new PlayerEventHandler());
        api.registerForgeListener(new TickScheduler());

    }

    @Override
    public void loadConfig() {
        JsonObject config = getConfig();
        this.renderPlayers = config.has("renderPlayers") && config.get("renderPlayers").getAsBoolean();
        this.playersToRender = config.has("playersToRender") ? config.get("playersToRender").getAsString() : "";
        this.key = config.has("key") ? config.get("key").getAsInt() : -1;
    }

    @Override
    protected void fillSettings(List<SettingsElement> subSettings) {
        subSettings.add(new HeaderElement(ModColor.cl('a') + "PlayerHider Settings"));
        final KeyElement keyElement = new KeyElement("Key", new ControlElement.IconData(Material.REDSTONE_TORCH_ON), this.key, new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                Emily.this.key = integer;
                Emily.this.getConfig().addProperty("key", integer);
                Emily.this.saveConfig();
            }
        });
        final StringElement playersToRender = new StringElement("Players to render", new ControlElement.IconData(Material.LEVER), this.playersToRender, new Consumer<String>() {
            @Override
            public void accept(String s) {
                Emily.this.playersToRender = s;
                System.out.println("Players to render: " + s);
                Emily.this.getConfig().addProperty("playersToRender", s);
                Emily.this.saveConfig();
            }
        });
        System.out.println("FillSettings: " + this.blacklistedPlayers);
        subSettings.add(playersToRender);
        subSettings.add(keyElement);
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getBlacklistedPlayers() {
        return this.blacklistedPlayers;
    }

    public void setBlacklistedPlayers(String blacklistedPlayers) {
        this.blacklistedPlayers = blacklistedPlayers;
    }

    public String getPlayersToRender() {
        return this.playersToRender;
    }

    public void setPlayersToRender(String playersToRender) {
        this.playersToRender = playersToRender;
    }

    public boolean isRenderPlayers() {
        return this.renderPlayers;
    }

    public void setRenderPlayers(boolean renderPlayers) {
        this.renderPlayers = renderPlayers;
    }

}