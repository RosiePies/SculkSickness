package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

public class TabInit {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(SculkSickness.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> SCULK_SICKNESS_TAB = TABS.register(
            "sculk_sickness_tab", // Tab ID
            () -> CreativeTabRegistry.create(
                    Component.translatable("category.sculksickness.sculk_sickness_tab"), // Tab Name
                    () -> new ItemStack(ItemInit.SICKNESS_ICON.get()) // Icon
            )
    );
    public static void init() {
        TABS.register();
    }
}
