package de.allround.event;

import de.allround.Minigame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Stack;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings({"ALL"})
public class EventManager {
    private static final HashMap<UUID, EventListener<?>> eventListeners = new HashMap<>();
    private static final HashMap<UUID,EventListener<?>> registerWaitingList = new HashMap<>();
    private static final Stack<UUID> unregisterWaitingList = new Stack<>();

    public static void registerWaitingListeners(){
        registerWaitingList.forEach((uuid, eventListener) -> {
            Bukkit.getPluginManager().registerEvent(eventListener.getEventClass(), eventListener, eventListener.getPriority(), (listener, event) -> {
                if (!(listener instanceof EventListener)) return;
                if (!(event.getClass().equals(eventListener.getEventClass()))) return;
                ((EventListener) listener).handleEvent(event);
            }, (Plugin) Minigame.getInstance().getPlugin());
            EventManager.eventListeners.put(uuid, eventListener);
        });

        registerWaitingList.clear();
    }

    public static void unregisterWaitingListener(){
        while (unregisterWaitingList.size() > 0){
            UUID uuid = unregisterWaitingList.pop();
            unregisterListener(uuid);
        }
    }

    public static void unregisterListener(UUID uuid) {
        if (!Bukkit.getPluginManager().isPluginEnabled(Minigame.getInstance().getPlugin())) {
            unregisterWaitingList.add(uuid);
            return;
        }
        if (!eventListeners.containsKey(uuid)) return;
        HandlerList.unregisterAll(eventListeners.get(uuid));
        eventListeners.remove(uuid);
    }

    public static void unregisterListeners(Class<? extends Event> clazz) {
        if (!Bukkit.getPluginManager().isPluginEnabled(Minigame.getInstance().getPlugin())) return;
        eventListeners.forEach((uuid, eventListener) -> {
            if (!Bukkit.getPluginManager().isPluginEnabled(Minigame.getInstance().getPlugin())) {
                unregisterWaitingList.add(uuid);
                return;
            }
            if (eventListener.eventClass.equals(clazz)) {
                eventListeners.remove(uuid, eventListener);
                HandlerList.unregisterAll(eventListener);
            }
        });
    }

    public static <T extends Event> UUID listen(Class<T> eventClass, Consumer<T> consumer) {
        UUID uuid = UUID.randomUUID();
        EventListener<T> eventListener = new EventListener<>(eventClass, consumer, null);
        if (!Bukkit.getPluginManager().isPluginEnabled(Minigame.getInstance().getPlugin())) {
            registerWaitingList.put(uuid,eventListener);
            return uuid;
        }
        Bukkit.getPluginManager().registerEvent(eventClass, eventListener, EventPriority.NORMAL, (listener, event) -> {
            if (!(listener instanceof EventListener)) return;
            if (!(event.getClass().equals(eventClass))) return;
            ((EventListener) listener).handleEvent(event);
        }, (Plugin) Minigame.getInstance().getPlugin());
        EventManager.eventListeners.put(uuid, eventListener);
        return uuid;
    }

    public static <T extends Event> UUID listen(Class<T> eventClass, Consumer<T> consumer, Predicate<T> predicate) {
        UUID uuid = UUID.randomUUID();
        EventListener<T> eventListener = new EventListener<>(eventClass, consumer, predicate);
        if (!Bukkit.getPluginManager().isPluginEnabled(Minigame.getInstance().getPlugin())) {
            registerWaitingList.put(uuid,eventListener);
            return uuid;
        }
        Bukkit.getPluginManager().registerEvent(eventClass, eventListener, EventPriority.NORMAL, (listener, event) -> {
            if (!(listener instanceof EventListener)) return;
            if (!(event.getClass().equals(eventClass))) return;
            ((EventListener) listener).handleEvent(event);
        }, (Plugin) Minigame.getInstance().getPlugin());
        EventManager.eventListeners.put(uuid, eventListener);
        return uuid;
    }

    public static <T extends Event> UUID listen(Class<T> eventClass, EventPriority priority, Consumer<T> consumer) {
        UUID uuid = UUID.randomUUID();
        EventListener<T> eventListener = new EventListener<>(eventClass, consumer, null);
        if (!Bukkit.getPluginManager().isPluginEnabled(Minigame.getInstance().getPlugin())) {
            registerWaitingList.put(uuid,eventListener);
            return uuid;
        }
        Bukkit.getPluginManager().registerEvent(eventClass, eventListener, priority, (listener, event) -> {
            if (!(listener instanceof EventListener)) return;
            if (!(event.getClass().equals(eventClass))) return;
            ((EventListener) listener).handleEvent(event);
        }, (Plugin) Minigame.getInstance().getPlugin());
        EventManager.eventListeners.put(uuid, eventListener);
        return uuid;
    }

    public static <T extends Event> UUID listen(Class<T> eventClass, EventPriority priority, Consumer<T> consumer, Predicate<T> predicate) {
        UUID uuid = UUID.randomUUID();
        EventListener<T> eventListener = new EventListener<>(eventClass, consumer, predicate);
        if (!Bukkit.getPluginManager().isPluginEnabled(Minigame.getInstance().getPlugin())) {
            registerWaitingList.put(uuid,eventListener);
            return uuid;
        }
        Bukkit.getPluginManager().registerEvent(eventClass, eventListener, priority, (listener, event) -> {
            if (!(listener instanceof EventListener)) return;
            if (!(event.getClass().equals(eventClass))) return;
            ((EventListener) listener).handleEvent(event);
        }, (Plugin) Minigame.getInstance().getPlugin());
        EventManager.eventListeners.put(uuid, eventListener);
        return uuid;
    }


    @RequiredArgsConstructor
    @AllArgsConstructor
    @Getter
    protected static class EventListener<T extends Event> implements Listener {
        private final Class<T> eventClass;
        private final Consumer<T> eventConsumer;
        private final Predicate<T> predicate;
        private EventPriority priority;

        public void handleEvent(T event) {
            if (predicate != null) {
                if (!predicate.test(event)) return;
            }
            eventConsumer.accept(event);
        }
    }

}
