package de.allround.item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.papermc.paper.inventory.ItemRarity;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

public class ItemSerializer { //TODO: googlen nach nen spigot itemstack to json serializer
    private final static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static String toJson(ItemStack itemStack) {
        JsonCompatibleItemStack.JsonCompatibleItemStackBuilder builder = JsonCompatibleItemStack.builder();
        builder.material(itemStack.getType());
        builder.amount(itemStack.getAmount());
        return GSON.toJson(builder.build());
    }

    public static ItemStack fromJson(String jsonString) {
        return GSON.fromJson(jsonString, JsonCompatibleItemStack.class).getAsItemStack();
    }

    @Getter
    @Builder
    private static class JsonCompatibleItemStack {
        private String displayName, localizedName;
        private ArrayList<String> lore;
        private ItemRarity rarity;
        private int amount;
        private HashSet<ItemFlag> itemFlags;
        private Material material;


        public ItemStack getAsItemStack() {
            ItemBuilder itemBuilder = new ItemBuilder(material, Math.max(1, amount));
            return itemBuilder.build();
        }
    }
}
