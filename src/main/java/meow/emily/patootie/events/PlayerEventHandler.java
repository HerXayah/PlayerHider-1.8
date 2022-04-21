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
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + " Blacklisted Players are now " + ChatFormatting.BOLD + ChatFormatting.DARK_RED + "off"));
                } else {
                    Emily.getInstance().setRenderPlayers(true);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + ChatFormatting.AQUA + "PH" + ChatFormatting.WHITE + "]" + " Blacklisted Players are now " + ChatFormatting.BOLD + ChatFormatting.GREEN + "on"));
                }
            }
        }
    }
}