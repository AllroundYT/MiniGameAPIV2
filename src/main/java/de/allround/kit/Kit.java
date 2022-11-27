package de.allround.kit;

import de.allround.item.ItemSerializer;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

@RequiredArgsConstructor
public class Kit {
    private final String name;
    private final ItemStack[] showcaseItem;
    private final ItemStack[] armorItems;
    private final ItemStack[] hotbarItems;
    private final ItemStack[] inventoryItems;
    private final PotionEffect[] permanentEffects;
    private final int startXp;
}
