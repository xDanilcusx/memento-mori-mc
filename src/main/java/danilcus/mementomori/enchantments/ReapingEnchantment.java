package danilcus.mementomori.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;

public class ReapingEnchantment extends Enchantment {
    public ReapingEnchantment(EquipmentSlot... slotTypes) {
        super(Rarity.RARE, CustomEnchantmentTargets.SICKLE_TARGET, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 25;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof TwinsEnchantment) && super.canAccept(other);
    }
}
