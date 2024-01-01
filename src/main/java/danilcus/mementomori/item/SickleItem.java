package danilcus.mementomori.item;

import danilcus.mementomori.MementoMori;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SickleItem extends SwordItem {
    public SickleItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, 4, -2.4f, settings);
    }

    public SickleItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
/*
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        World world = context.getWorld();
        Hand hand = context.getHand();

        assert user != null;
        var stack = user.getStackInHand(hand);

        boolean spewing = 0 < EnchantmentHelper.getLevel(MementoMori.ENCHANTMENTS.get("spewing"), stack);
        if (spewing) {
            return hand != Hand.MAIN_HAND || user.getAttackCooldownProgress(0.5f) < 0.9f ? ActionResult.FAIL : spew(world, user, hand).getResult();
        }
        return world.isClient ? ActionResult.FAIL : harvest(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        boolean spewing = 0 < EnchantmentHelper.getLevel(MementoMori.ENCHANTMENTS.get("spewing"), stack);
        if (!spewing || hand != Hand.MAIN_HAND || user.getAttackCooldownProgress(0.5f) < 0.9f) {
            return TypedActionResult.fail(stack);
        }
        return spew(world, user, hand);
    }
*/
}
