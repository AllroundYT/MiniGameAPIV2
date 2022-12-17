package de.allround.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class YamlSerializer {
    public static @NotNull ItemStack readItemStack(ConfigurationSection section) {
        Material material;
        String name;
        int amount;
        Map<Enchantment, Integer> enchantmentToLvlMap = new HashMap<>();
        boolean unbreakable;
        int damage;
        try {
            material = Material.valueOf(section.getString("material"));
        } catch (Exception e) {
            material = Material.AIR;
        }

        name = section.getString("name");

        ConfigurationSection enchantmentSection = section.getConfigurationSection("enchantments");
        if (enchantmentSection != null) {
            for (String enchantmentKey : enchantmentSection.getKeys(false)) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentKey.toLowerCase()));

                if (enchantment != null) {
                    int level = enchantmentSection.getInt(enchantmentKey);
                    enchantmentToLvlMap.put(enchantment, level);
                }
            }
        }
        unbreakable = section.getBoolean("unbreakable");
        damage = section.getInt("damage");
        amount = section.getInt("amount");

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }

        if (!enchantmentToLvlMap.isEmpty()) {
            enchantmentToLvlMap.forEach((enchantment, integer) -> {
                itemMeta.addEnchant(enchantment, integer, true);
            });
        }

        if (itemMeta instanceof Damageable damageable) damageable.setDamage(damage);

        itemMeta.setUnbreakable(unbreakable);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void writeItemStack(ItemStack itemStack, ConfigurationSection section) {
        if (itemStack.getItemMeta().hasDisplayName()) section.set("name", itemStack.getItemMeta().getDisplayName().replace("§","&"));
        section.set("material", itemStack.getType().name());
        section.set("amount", itemStack.getAmount());
        section.createSection("enchantments");
        itemStack.getEnchantments().forEach((enchantment, integer) -> section.getConfigurationSection("enchantments").set(enchantment.getKey().getKey(), integer));
        section.set("unbreakable",itemStack.getItemMeta().isUnbreakable());
        if (itemStack.getItemMeta() instanceof Damageable damageable){
            section.set("damage",damageable.getDamage());
        }
    }

    @Contract("_ -> new")
    public static @NotNull PotionEffect readPotionEffect(@NotNull ConfigurationSection section) {

        PotionEffectType effectType = PotionEffectType.getByKey(NamespacedKey.minecraft(section.getString("type") != null ? Objects.requireNonNull(section.getString("type")) : PotionEffectType.ABSORPTION.getKey().getKey()));
        int duration = section.getInt("duration");
        int amplifier = section.getInt("amplifier");
        boolean ambient = section.getBoolean("ambient");
        boolean particles = section.getBoolean("particles");
        boolean icon = section.getBoolean("icon");

        if (effectType == null) {
            return new PotionEffect(PotionEffectType.GLOWING, 0, 0, false, false, false);
        }

        return new PotionEffect(effectType, duration, amplifier, ambient, particles, icon);
    }

    public static void writePotionEffect(@NotNull PotionEffect potionEffect, @NotNull ConfigurationSection section) {
        section.set("type", potionEffect.getType().getKey().getKey());
        section.set("duration", potionEffect.getDuration());
        section.set("amplifier", potionEffect.getAmplifier());
        section.set("ambient", potionEffect.isAmbient());
        section.set("particles", potionEffect.hasParticles());
        section.set("icon", potionEffect.hasIcon());
    }
}
