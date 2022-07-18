package com.smorb42.FluidMana.network;

import com.smorb42.FluidMana.block.entity.IFluidHandlingBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncFluidStackToClient {
    private final FluidStack fluidStack;
    private final BlockPos pos;

    public PacketSyncFluidStackToClient(FluidStack stack, BlockPos pos) {
        this.fluidStack = stack;
        this.pos = pos;
    }

    public PacketSyncFluidStackToClient(FriendlyByteBuf buf) {
        this.fluidStack = buf.readFluidStack();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluidStack);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT YES
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IFluidHandlingBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);


            }
        });
        return true;
    }
}