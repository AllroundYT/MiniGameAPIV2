package de.allround.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

@RequiredArgsConstructor
@Getter
public class Kit {
    private final String name;
    private final ItemStack showcaseItem;
    private final ItemStack[] armorItems;
    private final ItemStack[] hotbarItems;
    private final ItemStack[] inventoryItems;
    private final PotionEffect[] permanentEffects;
    private final int startXp;


    public static String serialize(Kit kit){
        return "";
    }

    public static Kit deserialize(String jsonString){
        return null;
    }
}
