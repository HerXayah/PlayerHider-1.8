package meow.emily.patootie;

import com.google.gson.JsonObject;
import meow.emily.patootie.events.PlayerEventHandler;
import net.labymod.addons.voicechat.VoiceChat;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.*;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;

import java.util.List;
import java.util.logging.Logger;

public class Emily extends LabyModAddon {

    private static final Logger LOGGER = Logger.getLogger("PlayerHider");
    private static final String PREFIX = "[PH] ";

    private static Emily instance;
    private VoiceChat voiceChat;


    private boolean renderPlayers = true;

    private boolean ConfigMessage = true;
    private String playersToRender = "";
    private String blacklistedPlayers = "";
    private int key;

    public static Emily getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        api.registerForgeListener(new PlayerEventHandler());

    }

    @Override
    public void loadConfig() {
        JsonObject config = getConfig();
        this.renderPlayers = config.has("renderPlayers") && config.get("renderPlayers").getAsBoolean();
        this.playersToRender = config.has("playersToRender") ? config.get("playersToRender").getAsString() : "";
        this.key = config.has("key") ? config.get("key").getAsInt() : -1;
        this.ConfigMessage = config.has("ConfigMessage") && config.get("ConfigMessage").getAsBoolean();
    }

    @Override
    protected void fillSettings(List<SettingsElement> subSettings) {
        subSettings.add(new HeaderElement(ModColor.cl('a') + "PlayerHider Settings"));
        subSettings.add(new BooleanElement("Enable PlayerHider", this, new ControlElement.IconData(Material.REDSTONE), "renderPlayers", this.renderPlayers));
        final KeyElement keyElement = new KeyElement("Key", new ControlElement.IconData(Material.REDSTONE_TORCH_ON), this.key, integer -> {
            Emily.this.key = integer;
            Emily.this.getConfig().addProperty("key", integer);
            saveConfig();
        });
        subSettings.add(new BooleanElement("Enable Messages", this, new ControlElement.IconData(Material.WOOL), "ConfigMessage", this.ConfigMessage));
        final StringElement playersToRender = new StringElement("Blacklist", new ControlElement.IconData(Material.COAL_BLOCK), this.playersToRender, s -> {
            Emily.this.playersToRender = s;
            Emily.this.getConfig().addProperty("playersToRender", s);
            saveConfig();
        });
        subSettings.add(new HeaderElement(ModColor.cl('a') + "Seperate them by Comma"));
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

    public boolean isConfigMessage() {
        return this.ConfigMessage;
    }

    public void setConfigMessage(boolean ConfigMessage) {
        this.ConfigMessage = ConfigMessage;
    }

    public VoiceChat getVoiceChat() {
        return this.voiceChat;
    }

    public void setVoiceChat(VoiceChat voiceChat) {
        this.voiceChat = voiceChat;
    }

}