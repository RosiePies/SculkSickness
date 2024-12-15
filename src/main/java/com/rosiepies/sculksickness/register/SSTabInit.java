package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SculkSickness.MOD_ID);

    RegistryObject<CreativeModeTab> SCULK_SICKNESS = CREATIVE_MODE_TAB.register("sculk_sickness", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(SSItemInit.SICKNESS_ICON.get()))
            .title(Component.translatable("creativetab.sculksickness.sculk_sickness_tab"))
            .displayItems((params, output) -> {
                output.accept(SSItemInit.SCULK_BLOSSOM_ITEM.get());
                output.accept(SSItemInit.BLOOM_FRUIT.get());
                output.accept(SSItemInit.AMBROSIA_HERB.get());
                output.accept(SSItemInit.VILE_HERB.get());
                output.accept(SSItemInit.SHRIEKER_BONE.get());
            })
            .build());

    public static void init (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
