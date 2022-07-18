package com.smorb42.FluidMana.block.custom;

import com.smorb42.FluidMana.block.entity.ConverterPoolBlockEntity;
import com.smorb42.FluidMana.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public class ConverterPoolBlock extends BaseEntityBlock {

    public ConverterPoolBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ConverterPoolBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.CONVERTER_POOL_BLOCK_ENTITY.get(), level.isClientSide ? ConverterPoolBlockEntity::clientTick : ConverterPoolBlockEntity::serverTick);
    }


    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        ConverterPoolBlockEntity pool = (ConverterPoolBlockEntity) world.getBlockEntity(pos);
        return ConverterPoolBlockEntity.calculateComparatorLevel(pool.getCurrentMana(), pool.manaCap);
    }
}
