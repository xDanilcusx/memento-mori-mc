package danilcus.mementomori.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoulFireBlock.class)
public abstract class SoulFireBlockMixin {
    @Shadow protected abstract boolean isFlammable(BlockState state);

    /**
     * @author Danilcus
     * @reason Memento Mori mod requires being able to summon soulfire anywhere anytime
     */
    @Overwrite
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) && !world.getBlockState(blockPos).isAir();
    }
}
