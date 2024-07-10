package com.rosiepies.sculksickness.recipes;

import com.google.gson.JsonObject;
import com.rosiepies.sculksickness.items.MortarPestleItem;
import com.rosiepies.sculksickness.register.ItemInit;
import com.rosiepies.sculksickness.register.RecipeSerializerInit;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class MortarPestleRecipe extends ShapelessRecipe {

    public MortarPestleRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input) {
        super(id, group, output, input);
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer) {
        return getResultItem().copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.MORTAR_PESTLE_SERIALIZER.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {

        NonNullList<ItemStack> defaultList = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemStack = container.getItem(i);
            Item item = itemStack.getItem();
            if (item instanceof MortarPestleItem item1) {
                ItemStack mortarPestle = new ItemStack(item1, 1);
                mortarPestle.setDamageValue(itemStack.getDamageValue() + 1);
                if (mortarPestle.getDamageValue() >= mortarPestle.getMaxDamage()) continue;
                defaultList.set(i,mortarPestle);
            }
        }
        return defaultList;
    }

    public static class Serializer extends ShapelessRecipe.Serializer {

        @Override
        public MortarPestleRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ShapelessRecipe shapelessRecipe = super.fromJson(resourceLocation,jsonObject);
            String group = GsonHelper.getAsString(jsonObject, "group","");
            return new MortarPestleRecipe(shapelessRecipe.getId(),group,shapelessRecipe.getResultItem(),shapelessRecipe.getIngredients());
        }

        @Override
        public MortarPestleRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            ShapelessRecipe shapelessRecipe = super.fromNetwork(resourceLocation,friendlyByteBuf);
            return new MortarPestleRecipe(shapelessRecipe.getId(),shapelessRecipe.getGroup(),shapelessRecipe.getResultItem(),shapelessRecipe.getIngredients());
        }
    }
}
