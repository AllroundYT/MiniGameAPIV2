package de.allround.kit;

import de.allround.Minigame;
import de.allround.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KitManager { //todo: TEMPLATE KIT verbessern
    private final List<Kit> kits;


    public KitManager() {
        this.kits = new ArrayList<>();
        Kit templateKit = new Kit(
                "template",
                new ItemStack(Material.ANVIL, 2),
                new ItemStack[]{},
                new ItemStack[]{
                        new ItemBuilder(Material.SHIELD).damageItem(5).build()
                },
                new ItemStack[]{
                        new ItemBuilder(Material.TOTEM_OF_UNDYING).setDisplayName("§4TEST").build()
                },
                new PotionEffect[]{new PotionEffect(PotionEffectType.ABSORPTION, 1, 1, false, true, false)},
                5
        );
        Path.of("plugins", Minigame.getInstance().getPlugin().getName(),"Kits").toFile().mkdirs();
        saveKit(templateKit, Path.of("plugins", Minigame.getInstance().getPlugin().getName(),"Kits","template.yml").toFile());
        registerKits(templateKit);
    }

    public Path getKitDirectory(){
        return Path.of("plugins", Minigame.getInstance().getPlugin().getName(),"Kits");
    }

    @SuppressWarnings("ALL")
    public KitManager registerKits(Kit kit, Kit... kits) {
        this.kits.add(kit);
        this.kits.addAll(List.of(kits));
        return this;
    }

    public Optional<Kit> getKit(String name) {
        return getKits().stream().filter(kit -> kit.getName().equals(name)).findFirst();
    }

    public Kit loadKit(@NotNull File file) {
        if (!file.exists()) return null;
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return new Kit(cfg);
    }

    public void saveKit(Kit kit, @NotNull File file) {
        try {
            if (file.exists()) file.delete();
            file.createNewFile();


            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

            kit.write(cfg);

            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Kit> getKits() {
        return kits;
    }
}
