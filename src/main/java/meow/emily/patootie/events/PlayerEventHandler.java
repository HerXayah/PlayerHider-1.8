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

public class PlayerEventHandler {

    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e) {

        if (Emily.getInstance().isRenderPlayers()) {
            EntityPlayer enPlayer = e.getEntityPlayer();
            if (Emily.getInstance().isRenderPlayers() && !enPlayer.equals(Minecraft.getMinecraft().player)) {
                String[] localPlayersToRender = Emily.getInstance().getPlayersToRenderString().split(",");
                if (!Utils.isNPC(enPlayer)) {
                    e.setCanceled(false);
                    for (String s : localPlayersToRender) {
                        if (s.equals(enPlayer.getGameProfile().getName())) {
                            e.setCanceled(true);

                            // Add Player from list to RenderPlayers as UUID
                            try {
                                int i;

                                for (i = 0; i < Emily.getInstance().getPlayersToRender().size(); i++) {

                                    Emily.getInstance().getVoiceChat().getPlayerVolumes().put(enPlayer.getUniqueID(), 0);
                                    Emily.getInstance().savePlayersToRender();
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
        if (Emily.getInstance().getKey() > -1) {
            if (Keyboard.isKeyDown(Emily.getInstance().getKey())) {
                if (Emily.getInstance().isRenderPlayers()) {
                    Emily.getInstance().setRenderPlayers(false);
                    if (Emily.getInstance().isConfigMessage()) {
                        LabyMod.getInstance().displayMessageInChat(ChatFormatting.GRAY + ">>" + "[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + ChatFormatting.BOLD + ChatFormatting.GREEN + " on");
                    }
                } else {
                    Emily.getInstance().setRenderPlayers(true);
                    if (Emily.getInstance().isConfigMessage()) {
                        LabyMod.getInstance().displayMessageInChat(ChatFormatting.GRAY + ">>" + "[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + ChatFormatting.BOLD + ChatFormatting.DARK_RED + " off");
                    }
                }
            }
        }
    }
}
