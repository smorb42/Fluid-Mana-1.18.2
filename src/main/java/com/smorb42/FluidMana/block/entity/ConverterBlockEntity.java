package com.smorb42.FluidMana.block.entity;

import com.smorb42.FluidMana.network.ModMessages;
import com.smorb42.FluidMana.network.PacketSyncFluidStackToClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConverterBlockEntity extends BlockEntity implements IFluidHandlingBlockEntity{

    private final FluidTank FLUID_TANK = createFluidTank();


    public  ConverterBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.CONVERTER_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    @NotNull
    protected FluidTank createFluidTank() {
        return new FluidTank(16000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (!level.isClientSide()) {
                    ModMessages.sendToClients(new PacketSyncFluidStackToClient(this.fluid, worldPosition));
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid() == Fluids.WATER;
            }
        };
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    @Override
    public void setFluid(FluidStack fluidStack) {
        this.FLUID_TANK.setFluid(fluidStack);
    }

    @Override
    public FluidStack getFluid() {
        return this.FLUID_TANK.getFluid();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
                return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag = FLUID_TANK.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        FLUID_TANK.readFromNBT(nbt);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ConverterBlockEntity pBlockEntity) {
        if(pLevel.isClientSide()) {
            return;
        }

        if(hasSpaceInTank(pBlockEntity,10)) {
            //convert
        }
    }


    private static boolean hasSpaceInTank(ConverterBlockEntity entity, int fillAmount) {
        return entity.FLUID_TANK.getSpace() >= fillAmount;
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);

        return compound;
    }
}
