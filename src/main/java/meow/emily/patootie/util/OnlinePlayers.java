package meow.emily.patootie.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.util.function.Function;

public class OnlinePlayers {
    public static String[] getListOfPlayerUsernames() {
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap().stream().map(new Function<NetworkPlayerInfo, String>() {
            @Override
            public String apply(NetworkPlayerInfo object) {
                return object.getGameProfile().getName();
            }
        }).toArray(String[]::new);
    }
}
