package com.smorb42.FluidMana.item;

import com.smorb42.FluidMana.FluidMana;
import com.smorb42.FluidMana.fluid.ModFluids;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FluidMana.MOD_ID);

    public static final RegistryObject<Item> MANA_BUCKET = ITEMS.register("mana_bucket",
            () -> new BucketItem(ModFluids.MANA_FLUID,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
