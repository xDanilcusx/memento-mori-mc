package danilcus.mementomori.enchantments;

import danilcus.mementomori.item.SickleItem;
import danilcus.mementomori.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
public class SickleTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof SickleItem;
    }
}
