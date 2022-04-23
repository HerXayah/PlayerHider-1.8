package meow.emily.patootie;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import meow.emily.patootie.events.PlayerEventHandler;
import net.labymod.addons.voicechat.VoiceChat;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.*;
import net.labymod.user.User;
import net.labymod.user.util.UserActionEntry;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class Emily extends LabyModAddon {

    private static final Logger LOGGER = Logger.getLogger("PlayerHider");
    private static final String PREFIX = "[PH] ";

    private static Emily instance;
    private VoiceChat voiceChat;


    private boolean renderPlayers = true;

    private boolean ConfigMessage = true;

    private String playersToRenderString = "";

    public Map<UUID, Integer> playersToRender = new HashMap<>();

    private int key;

    public static Emily getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        this.api.registerForgeListener(new PlayerEventHandler());

        this.api.getEventManager().register((user, entityPlayer, networkPlayerInfo, list) -> list.add(new UserActionEntry("Blacklist Player", UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {

            @Override
            public void execute(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                Emily.this.getConfig().addProperty("playersToRenderString", networkPlayerInfo.getGameProfile().getId() + ",");
                LabyMod.getInstance().displayMessageInChat("UUID: " + String.valueOf(Emily.getInstance().getConfig().get("playersToRenderString")));
                try {
                    Emily.getInstance().getConfig();
                    Emily.getInstance().playersToRender.put(networkPlayerInfo.getGameProfile().getId(), 0);
                    savePlayersToRender();
                    saveConfig();
                } catch (Exception e) {
                    e.printStackTrace();
                    LabyMod.getInstance().displayMessageInChat("Error: " + e.getMessage());
                }
                Emily.getInstance().loadConfig();
            }

            @Override
            public boolean canAppear(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo) {
                return true;
            }
        })));

    }

    @Override
    public void loadConfig() {
        JsonObject config = getConfig();
        this.renderPlayers = config.has("renderPlayers") && config.get("renderPlayers").getAsBoolean();
        this.playersToRenderString = config.has("playersToRenderString") ? config.get("playersToRenderString").getAsString() : "";
        this.key = config.has("key") ? config.get("key").getAsInt() : -1;
        this.ConfigMessage = config.has("ConfigMessage") && config.get("ConfigMessage").getAsBoolean();
        if (Emily.getInstance().getConfig().has("playersToRender")) {
            JsonObject object = this.getConfig().get("playersToRender").getAsJsonObject();
            Map<UUID, Integer> playersToRender = new HashMap<>();

            for (Map.Entry<String, JsonElement> stringJsonElementEntry : object.entrySet()) {
                playersToRender.put(UUID.fromString((String) ((Map.Entry<?, ?>) stringJsonElementEntry).getKey()), ((JsonElement) ((Map.Entry<?, ?>) stringJsonElementEntry).getValue()).getAsInt());
            }
            this.playersToRender = playersToRender;
        } else {
            this.playersToRender = new HashMap<>();
        }
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
        final StringElement playersToRender = new StringElement("Blacklist", new ControlElement.IconData(Material.COAL_BLOCK), this.playersToRenderString, s -> {
            Emily.this.playersToRenderString = s;
            Emily.this.getConfig().addProperty("playersToRenderString", s);
            saveConfig();
        });
        subSettings.add(new HeaderElement(ModColor.cl('a') + "Seperate them by Comma"));
        subSettings.add(playersToRender);
        subSettings.add(keyElement);
    }

    public void savePlayersToRender() {
        JsonObject object = new JsonObject();

        for (Map.Entry<UUID, Integer> uuidIntegerEntry : this.playersToRender.entrySet()) {
            object.addProperty(((UUID) ((Map.Entry<?, ?>) uuidIntegerEntry).getKey()).toString(), (Number) ((Map.Entry<?, ?>) uuidIntegerEntry).getValue());
        }

        LabyMod.getInstance().displayMessageInChat("Saved players to render");
        LabyMod.getInstance().displayMessageInChat(Emily.getInstance().getPlayersToRender().toString());

        this.getConfig().add("playersToRender", object);
        this.saveConfig();
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Map<UUID, Integer> getPlayersToRender() {
        return this.playersToRender;
    }

    public void setPlayersToRender(Map<UUID, Integer> playersToRender) {
        this.playersToRender = playersToRender;
    }

    public boolean isRenderPlayers() {
        return this.renderPlayers;
    }

    public void setRenderPlayers(boolean renderPlayers) {
        this.renderPlayers = renderPlayers;
    }

    public String getPlayersToRenderString() {
        return this.playersToRenderString;
    }

    public String setPlayersToRenderString(String playersToRenderString) {
        return this.playersToRenderString = playersToRenderString;
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