package meow.emily.patootie.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import meow.emily.patootie.Emily;
import meow.emily.patootie.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class PlayerEventHandler {
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e) {
        EntityPlayer enPlayer = e.entityPlayer;
        if (Emily.getInstance().isRenderPlayers() && !enPlayer.equals(Minecraft.getMinecraft().thePlayer)) {
            String[] localPlayersToRender = Emily.getInstance().getPlayersToRender().split(",");
            if (!Utils.isNPC(enPlayer)) {
                e.setCanceled(false);
                for (String s : localPlayersToRender) {
                    if (s.equals(enPlayer.getGameProfile().getName())) {
                        e.setCanceled(true);
                      /*  try {
                                // Just testing smth Emily.getInstance().getVoiceChat().getEnabledSetting().getCurrentValue();
                                Emily.getInstance().getVoiceChat().getPlayerVolumes().put(UUID.fromString(enPlayer.getGameProfile().getName()), 0);
                                LabyMod.getInstance().displayMessageInChat("Hidden");
                        } catch (Exception ex) {
                          //  System.out.println("Error: " + ex);
                        }
                        try {
                            Emily.getInstance().getVoiceChat().getPlayerVolumes().get(UUID.fromString(enPlayer.getGameProfile().getName()));
                        } catch (Exception ex) {
                           // System.out.println("Error2: " + ex);
                        }
                        // Why did no one implement a god damn method to mute Players via the client??????
                        // this shit here doesnt work afaik */
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
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + ChatFormatting.BOLD + ChatFormatting.GREEN + " on"));
                    }
                } else {
                    Emily.getInstance().setRenderPlayers(true);
                    if (Emily.getInstance().isConfigMessage()) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + ChatFormatting.BOLD + ChatFormatting.DARK_RED + " off"));
                    }
                }
            }
        }
    }
}