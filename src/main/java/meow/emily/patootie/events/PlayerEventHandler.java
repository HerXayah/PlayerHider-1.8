package meow.emily.patootie.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import meow.emily.patootie.Emily;
import meow.emily.patootie.util.Utils;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e) {
        Emily instance = Emily.getInstance();
        if (instance.isRenderPlayers()) {
            EntityPlayer enPlayer = e.getEntityPlayer();
            if (instance.isRenderPlayers() && !enPlayer.equals(Minecraft.getMinecraft().player)) {
                List<String> localPlayersToRender = instance.getPlayersToRenderString();
                if (!Utils.isNPC(enPlayer)) {
                    e.setCanceled(false);
                    for (String s : localPlayersToRender) {
                        if (s.equals(enPlayer.getGameProfile().getName())) {
                            e.setCanceled(true);
                            try {
                                int i;

                                for (i = 0; i < instance.getPlayersToRender().size(); i++) {

                                    instance.getVoiceChat().getPlayerVolumes().put(enPlayer.getUniqueID(), 0);
                                    instance.savePlayersToRender();
                                    LabyMod.getInstance().displayMessageInChat("UUID: " + enPlayer.getUniqueID().toString() + " Name: " + enPlayer.getGameProfile().getName());
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        Emily instance = Emily.getInstance();
        if (instance.getKey() > -1) {
            if (Keyboard.isKeyDown(instance.getKey())) {
                LabyMod labymod = LabyMod.getInstance();
                if (instance.isRenderPlayers()) {
                    instance.setRenderPlayers(false);
                    if (instance.isConfigMessage()) {
                        labymod.displayMessageInChat(ChatFormatting.GRAY + ">>" + "[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + ChatFormatting.BOLD + ChatFormatting.GREEN + " on");
                    }
                } else {
                    instance.setRenderPlayers(true);
                    if (instance.isConfigMessage()) {
                        labymod.displayMessageInChat(ChatFormatting.GRAY + ">>" + "[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + ChatFormatting.BOLD + ChatFormatting.DARK_RED + " off");
                    }
                }
            }
        }
    }
}
