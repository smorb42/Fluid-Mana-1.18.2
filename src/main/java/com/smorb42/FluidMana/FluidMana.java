package com.smorb42.FluidMana;

import com.mojang.logging.LogUtils;
import com.smorb42.FluidMana.block.ModBlocks;
import com.smorb42.FluidMana.block.entity.ModBlockEntities;
import com.smorb42.FluidMana.item.ModItems;
import com.smorb42.FluidMana.fluid.ModFluids;
import com.smorb42.FluidMana.network.ModMessages;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FluidMana.MOD_ID)
public class FluidMana
{
    public static final String MOD_ID = "fluidmana";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public FluidMana()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModFluids.register(eventBus);
        ModBlockEntities.register(eventBus);

        // Register the setup method for modloading
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModFluids.MANA_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.MANA_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.MANA_FLOWING.get(), RenderType.translucent());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModMessages.register();

        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
