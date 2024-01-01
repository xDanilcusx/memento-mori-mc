package danilcus.mementomori.mixin;

import danilcus.mementomori.item.SickleItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/enchantment/EnchantmentTarget$11")
public class EnchantmentTargetWeaponMixin {

    @Inject(method = "isAcceptableItem(Lnet/minecraft/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    public void ExcludeSwordEnchantmentsOnScythe(Item item, CallbackInfoReturnable<Boolean> info) {
        if(item instanceof SickleItem) {
            info.setReturnValue(false);
        }
    }
}
