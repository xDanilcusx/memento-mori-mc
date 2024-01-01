package danilcus.mementomori;

import danilcus.mementomori.item.SickleItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MementoMori implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "reapwys";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final HashMap<String, Enchantment> ENCHANTMENTS = new HashMap<>() {{
		//put("twins", new TwinsEnchantment(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
		//put("reaping", new ReapingEnchantment(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
	}};

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		for (Map.Entry<String, Enchantment> enchantment : ENCHANTMENTS.entrySet()) {
			Registry.register(Registry.ENCHANTMENT, ID(enchantment.getKey()), enchantment.getValue());
		}

		Registry.register(Registry.ITEM, ID("sickle"), new SickleItem(ToolMaterials.NETHERITE, new FabricItemSettings().fireproof().group(ItemGroup.COMBAT)));
	}

	public static Identifier ID(String path) {
		return new Identifier(MOD_ID, path);
	}
}