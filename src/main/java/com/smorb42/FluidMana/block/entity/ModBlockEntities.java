package com.smorb42.FluidMana.block.entity;

import com.smorb42.FluidMana.FluidMana;
import com.smorb42.FluidMana.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FluidMana.MOD_ID);

    public static final RegistryObject<BlockEntityType<ConverterBlockEntity>> CONVERTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("converter_block_entity", () ->
                    BlockEntityType.Builder.of(ConverterBlockEntity::new,
                            ModBlocks.CONVERTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ConverterPoolBlockEntity>> CONVERTER_POOL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("converter_pool_block_entity", () ->
                    BlockEntityType.Builder.of(ConverterPoolBlockEntity::new,
                            ModBlocks.CONVERTER_BLOCK.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
