package net.nicolas.calcium.mixin.gameplay;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.nicolas.calcium.block.ModNotes;
import net.nicolas.calcium.block.ModStrengths;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Mutable @Shadow @Final private int lightEmission;

    @Shadow public abstract Block getBlock();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void calcium$adjustLightEmission(CallbackInfo ci) {
        Block block = this.getBlock();
        if (block instanceof MagmaBlock) {
            this.lightEmission = 6;
        } else if (block instanceof MushroomBlock) {
            this.lightEmission = 0;
        }
    }

    @ModifyReturnValue(method = "instrument", at = @At("RETURN"))
    private NoteBlockInstrument calcium$matchMetalInstrument(NoteBlockInstrument original) {
        NoteBlockInstrument instrument = ModNotes.of(((BlockState) (Object) this).getBlock());
        return instrument != null ? instrument : original;
    }

    @ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
    private float calcium$matchOreHardness(float original) {
        ModStrengths.Strength strength = ModStrengths.of(((BlockState) (Object) this).getBlock());
        return strength != null ? strength.hardness() : original;
    }

    @ModifyReturnValue(method = "requiresCorrectToolForDrops", at = @At("RETURN"))
    private boolean calcium$requireTool(boolean original) {
        return original || ModStrengths.requiresTool(((BlockState) (Object) this).getBlock());
    }

}
