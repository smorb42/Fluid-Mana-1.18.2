package com.smorb42.FluidMana.block;

import com.smorb42.FluidMana.FluidMana;
import com.smorb42.FluidMana.block.custom.ConverterBlock;
import com.smorb42.FluidMana.block.custom.ConverterPoolBlock;
import com.smorb42.FluidMana.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FluidMana.MOD_ID);

    public static final RegistryObject<Block> CONVERTER_POOL_BLOCK = registerBlock("converter_pool_block",
            () -> new ConverterPoolBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MISC);
    public static final RegistryObject<Block> CONVERTER_BLOCK = registerBlock("converter_block",
            () -> new ConverterBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> MANA_FLUID_BLOCK = registerBlock("mana_fluid_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MISC);



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
