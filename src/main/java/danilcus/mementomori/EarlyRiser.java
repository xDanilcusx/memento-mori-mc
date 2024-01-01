package danilcus.mementomori;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;

public class EarlyRiser implements Runnable {

    @Override
    public void run() {
        var remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0])
                .addEnumSubclass("SICKLE", "danilcus.mementomori.enchantments.SickleTarget")
                .build();
    }

}