package de.allround.item;

import de.allround.event.EventManager;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAbilityRegister {

    private final Map<String, ItemAbility> itemAbilities;

    public ItemAbilityRegister() {
        this.itemAbilities = new HashMap<>();

        EventManager.listen(
                PlayerInteractEvent.class,
                event -> getItemAbilities(event.getItem()).forEach(itemAbility -> itemAbility.onInteract(event)),
                event -> event.getItem() != null
        );

        EventManager.listen(
                PlayerDropItemEvent.class,
                event -> getItemAbilities(event.getItemDrop().getItemStack()).forEach(itemAbility -> itemAbility.onDrop(event))
        );
    }

    public ItemAbilityRegister registerAbility(String identifier, ItemAbility itemAbility) {
        itemAbilities.put(identifier, itemAbility);
        return this;
    }

    public ItemAbility getItemAbility(String identifier) {
        return itemAbilities.getOrDefault(identifier, null);
    }

    public boolean hasItemAbilities(ItemStack itemStack) {
        return getItemAbilities(itemStack).size() > 0;
    }

    public List<ItemAbility> getItemAbilities(ItemStack itemStack) {
        List<ItemAbility> abilities = new ArrayList<>();
        itemStack.getItemMeta().getPersistentDataContainer().getKeys().forEach(namespacedKey -> {
            if (itemAbilities.containsKey(namespacedKey.getKey())) {
                abilities.add(getItemAbility(namespacedKey.getKey()));
            }
        });
        return abilities;
    }

    public static abstract class ItemAbility {
        public abstract void onInteract(PlayerInteractEvent event);

        public abstract void onDrop(PlayerDropItemEvent event);
    }
}
