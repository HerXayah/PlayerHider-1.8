package meow.emily.patootie.util;

import net.minecraft.client.Minecraft;

public class OnlinePlayers {
    public static String[] getListOfPlayerUsernames() {
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap().stream().map(object -> object.getGameProfile().getName()).toArray(String[]::new);
    }
}
