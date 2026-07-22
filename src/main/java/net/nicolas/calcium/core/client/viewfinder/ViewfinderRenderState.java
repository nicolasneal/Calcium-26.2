package net.nicolas.calcium.core.client.viewfinder;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class ViewfinderRenderState extends BlockEntityRenderState {

    public Direction facing = Direction.NORTH;
    public float absoluteYaw;
    public float absolutePitch;
    public boolean hiddenFromLocalViewer;

}