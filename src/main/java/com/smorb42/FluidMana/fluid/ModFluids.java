package com.smorb42.FluidMana.fluid;

import com.smorb42.FluidMana.FluidMana;
import com.smorb42.FluidMana.block.ModBlocks;
import com.smorb42.FluidMana.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, FluidMana.MOD_ID);


    public static final RegistryObject<FlowingFluid> MANA_FLUID
            = FLUIDS.register("mana_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.MANA_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MANA_FLOWING
            = FLUIDS.register("mana_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.MANA_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MANA_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MANA_FLUID.get(), () -> MANA_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(15).luminosity(15).viscosity(5).sound(SoundEvents.AMETHYST_BLOCK_PLACE).overlay(WATER_OVERLAY_RL)
            .color(0xbf5BEDF2)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> ModFluids.MANA_BLOCK.get()).bucket(() -> ModItems.MANA_BUCKET.get());

    public static final RegistryObject<LiquidBlock> MANA_BLOCK = ModBlocks.BLOCKS.register("mana",
            () -> new LiquidBlock(() -> ModFluids.MANA_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));



    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}