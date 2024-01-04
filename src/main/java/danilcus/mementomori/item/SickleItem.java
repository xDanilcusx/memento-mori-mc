package danilcus.mementomori.item;

import danilcus.mementomori.MementoMori;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.predicate.entity.LightningBoltPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import javax.accessibility.AccessibleKeyBinding;
import javax.swing.text.JTextComponent;

public class SickleItem extends SwordItem {
    public SickleItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, 4, -2.4f, settings);
    }

    public SickleItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        World world = context.getWorld();
        Hand hand = context.getHand();

        assert user != null;
        var sickle = user.getStackInHand(hand);

        boolean twins = 0 < EnchantmentHelper.getLevel(MementoMori.ENCHANTMENTS.get("twins"), sickle);
        if (twins && user.isSneaking()) {
            var otherHand = (hand == Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND;
            if (0 < EnchantmentHelper.getLevel(MementoMori.ENCHANTMENTS.get("twins"), user.getStackInHand(otherHand))) {
                return ignite(context);
            }
        }
        return ActionResult.FAIL;
    }

    public static ActionResult ignite(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity user = context.getPlayer();
        World world = context.getWorld();

        BlockState blockState = world.getBlockState(blockPos);
        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
            BlockPos blockPos2 = blockPos.offset(context.getSide());
            if (SoulFireBlock.canPlaceAt(world, blockPos2, context.getPlayerFacing())) {
                world.playSound(user, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockState2 = Blocks.SOUL_FIRE.getDefaultState();
                world.setBlockState(blockPos2, blockState2, 11);
                world.emitGameEvent(user, GameEvent.BLOCK_PLACE, blockPos);
                ItemStack itemStack = context.getStack();
                if (user instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) user, blockPos2, itemStack);
                }

                user.swingHand(Hand.OFF_HAND);
                return ActionResult.CONSUME;
            } else {
                return ActionResult.FAIL;
            }
        } else {
            world.playSound(user, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(blockPos, blockState.with(Properties.LIT, true), 11);
            world.emitGameEvent(user, GameEvent.BLOCK_CHANGE, blockPos);

            user.swingHand(Hand.OFF_HAND);
            return ActionResult.CONSUME;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var sickle = user.getStackInHand(hand);

        boolean twins = 0 < EnchantmentHelper.getLevel(MementoMori.ENCHANTMENTS.get("twins"), sickle);
        if (twins) {
            var otherHand = (hand == Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND;
            if (0 < EnchantmentHelper.getLevel(MementoMori.ENCHANTMENTS.get("twins"), user.getStackInHand(otherHand))) {
                return user.isSneaking() ? TypedActionResult.fail(sickle) : parry(sickle, user);
            }
        }
        return TypedActionResult.fail(sickle);
    }

    public static TypedActionResult<ItemStack> parry(ItemStack sickle, PlayerEntity user) {
        user.setCurrentHand(Hand.MAIN_HAND);
        user.setSprinting(false);
        return TypedActionResult.pass(sickle);
    }
}
