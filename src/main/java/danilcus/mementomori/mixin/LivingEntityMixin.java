package danilcus.mementomori.mixin;

import danilcus.mementomori.item.SickleItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected ItemStack activeItemStack;
    @Shadow public abstract boolean isUsingItem();
    @Shadow public abstract boolean blockedByShield(DamageSource source);

    private DamageSource sickle$cachedSource;
    private boolean sickle$appearBlocking = false;

    @Inject(at = @At(value = "HEAD"), method = "isBlocking", cancellable = true)
    public void sickle$fakeShieldBlocking(CallbackInfoReturnable<Boolean> cir) {
        var item = this.activeItemStack.getItem();
        if(item instanceof SickleItem) {
            cir.setReturnValue(sickle$appearBlocking);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "damage", cancellable = true)
    public void sickle$cacheDamageSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.sickle$cachedSource = source;
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), index = 2, argsOnly = true)
    private float parry$applySwordBlockProtection(float old) {
        var item = this.activeItemStack.getItem();
        sickle$appearBlocking = true;
        if(item instanceof SickleItem && this.isUsingItem() && this.blockedByShield(sickle$cachedSource)) {
            old *= 0.5f;
        }
        sickle$appearBlocking = false;
        return old;
    }

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
}
