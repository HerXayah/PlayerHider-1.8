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

import java.lang.reflect.InvocationTargetException;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        if (Emily.getInstance().isRenderPlayers()) {
            EntityPlayer enPlayer = e.getEntityPlayer();
            if (Emily.getInstance().isRenderPlayers() && !enPlayer.equals(Minecraft.getMinecraft().player)) {
                String[] localPlayersToRender = Emily.getInstance().getPlayersToRenderString().split(",");
                if (!Utils.isNPC(enPlayer)) {
                    e.setCanceled(false);
                    for (String s : localPlayersToRender) {
                        if (s.equals(enPlayer.getGameProfile().getName())) {
                            e.setCanceled(true);

                         /*    try {
                                for (int i = 0; i < localPlayersToRender.length; i++) {
                                    Emily.getInstance().getVoiceChat().getVolume(enPlayer.getUniqueID());
                                    JsonObject object = new JsonObject();

                                    for (Map.Entry<UUID, Integer> ignored : Emily.getInstance().getVoiceChat().playerVolumes.entrySet()) {
                                        object.addProperty(String.valueOf(Emily.getInstance().getVoiceChat().getVolume(enPlayer.getUniqueID())), 0);
                                    }

                                    Emily.getInstance().getVoiceChat().getConfig().add("playerVolumes", object);
                                    Emily.getInstance().getVoiceChat().saveConfig();
                                }
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }

                       try {
                            Field classVoice = Emily.getInstance().getVoiceChat().getClass().getDeclaredField("playerVolumes");
                            classVoice.setAccessible(true);
                            Method voiceMethod = Emily.getInstance().getVoiceChat().getClass().getDeclaredMethod("getPlayerVolumes");
                            voiceMethod.setAccessible(true);
                            float[] playerVolumes = (float[]) voiceMethod.invoke(Emily.getInstance().getVoiceChat());
                            playerVolumes[enPlayer.getEntityId()] = 0.0F;
                            classVoice.set(Emily.getInstance().getVoiceChat(), playerVolumes);
                            System.out.println(enPlayer.getGameProfile().getName() + " is muted");
                        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                            e1.printStackTrace();
                        }

                        /*

                        // Get Players from list and

                       for(int i = 0; i < Emily.getInstance().getPlayersToRender().size(); i++) {
                            Emily.getInstance().getVoiceChat().getVolume(enPlayer.getUniqueID());
                            if(Emily.getInstance().getVoiceChat().getVolume(enPlayer.getUniqueID()) > 0) {

                            }
                        }
                        try {
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
