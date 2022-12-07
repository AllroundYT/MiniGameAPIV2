package de.allround.kit;

import de.allround.item.YamlSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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


    public Kit(@NotNull ConfigurationSection section) {
        this.name = section.getString("name");
        this.startXp = section.getInt("startXp");
        this.showcaseItem = YamlSerializer.readItemStack(section.getConfigurationSection("showcase"));
        ConfigurationSection armorSection = section.getConfigurationSection("armor");
        ConfigurationSection hotbarSection = section.getConfigurationSection("hotbar");
        ConfigurationSection inventorySection = section.getConfigurationSection("inventory");
        ConfigurationSection effectSection = section.getConfigurationSection("effects");

        List<ItemStack> armorItems = new ArrayList<>();
        if (armorSection != null){
            for (String key : armorSection.getKeys(false)) {
                ConfigurationSection itemSection = armorSection.getConfigurationSection(key);
                armorItems.add(YamlSerializer.readItemStack(itemSection));
            }
        }
        this.armorItems = armorItems.toArray(new ItemStack[0]);

        List<ItemStack> hotbarItems = new ArrayList<>();
        if (hotbarSection != null){
            for (String key : hotbarSection.getKeys(false)) {
                ConfigurationSection itemSection = hotbarSection.getConfigurationSection(key);
                hotbarItems.add(YamlSerializer.readItemStack(itemSection));
            }
        }
        this.hotbarItems = hotbarItems.toArray(new ItemStack[0]);

        List<ItemStack> inventoryItems = new ArrayList<>();
        if (inventorySection != null){
            for (String key : inventorySection.getKeys(false)) {
                ConfigurationSection itemSection = inventorySection.getConfigurationSection(key);
                inventoryItems.add(YamlSerializer.readItemStack(itemSection));
            }
        }
        this.inventoryItems = inventoryItems.toArray(new ItemStack[0]);

        List<PotionEffect> permanentEffects = new ArrayList<>();
        if (effectSection != null){
            for (String key : effectSection.getKeys(false)) {
                ConfigurationSection potionEffectSection = effectSection.getConfigurationSection(key);
                assert potionEffectSection != null;
                permanentEffects.add(YamlSerializer.readPotionEffect(potionEffectSection));
            }
        }
        this.permanentEffects = permanentEffects.toArray(new PotionEffect[0]);
    }


    public void write(@NotNull ConfigurationSection section) {
        section.set("name",getName());
        section.set("startXp",getStartXp());

        ConfigurationSection armorSection = section.createSection("armor");
        for (int i = 0; i < armorItems.length;i++) {
            ItemStack current = armorItems[i];
            YamlSerializer.writeItemStack(current,armorSection.createSection(String.valueOf(i)));
        }

        ConfigurationSection hotbarSection = section.createSection("hotbar");
        for (int i = 0; i < hotbarItems.length;i++) {
            ItemStack current = hotbarItems[i];
            YamlSerializer.writeItemStack(current,hotbarSection.createSection(String.valueOf(i)));
        }

        ConfigurationSection inventorySection = section.createSection("inventory");
        for (int i = 0; i < inventoryItems.length;i++) {
            ItemStack current = inventoryItems[i];
            YamlSerializer.writeItemStack(current,inventorySection.createSection(String.valueOf(i)));
        }

        ConfigurationSection effectsSection = section.createSection("effects");
        for (int i = 0; i < permanentEffects.length;i++) {
            PotionEffect current = permanentEffects[i];
            YamlSerializer.writePotionEffect(current,effectsSection.createSection(String.valueOf(i)));
        }

        ConfigurationSection showcaseSection = section.createSection("showcase");
        YamlSerializer.writeItemStack(showcaseItem,showcaseSection);
    }
}
