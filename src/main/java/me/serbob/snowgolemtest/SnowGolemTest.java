package me.serbob.snowgolemtest;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SnowGolemTest extends JavaPlugin implements Listener {
    public static SnowGolemTest instance;
    @Override
    public void onEnable() {
        instance=this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ListenerTEst(), this);
    }

    @Override
    public void onDisable() {
    }
}
