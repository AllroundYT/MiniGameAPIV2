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
import java.util.Map;

public class ItemSerializer { //TODO
     public Map<String,Object> serialize(ItemStack itemStack){
         return null;
     }

     public ItemStack deserialize(Map<String,Object> configSection){
         return null;
     }
}
