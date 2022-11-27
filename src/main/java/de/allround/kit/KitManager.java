package de.allround.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.*;
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
                new ItemStack[]{},
                new ItemStack[]{},
                new PotionEffect[]{new PotionEffect(PotionEffectType.ABSORPTION, 1, 1, false, true, false)},
                5
        );
        registerKits(templateKit);
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

    public Kit loadKit(Path path) {
        if (!path.toFile().exists()) return null;
        try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            return Kit.deserialize(new String(bufferedInputStream.readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveTemplateKit(Path path) {
        String jsonString = Kit.serialize(getKit("template").get());
        try {
            if (path.toFile().exists()) path.toFile().delete();
            path.toFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (final BufferedOutputStream bufferedInputStream = new BufferedOutputStream(new FileOutputStream(path.toFile()))) {
            bufferedInputStream.write(jsonString.getBytes());
            bufferedInputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Kit> getKits() {
        return kits;
    }
}
