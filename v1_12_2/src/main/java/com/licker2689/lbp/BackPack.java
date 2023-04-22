package com.licker2689.lbp;

import com.licker2689.lbp.commands.LBPCommand;
import com.licker2689.lbp.events.LBPEvent;
import com.licker2689.lpc.utils.DataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BackPack extends JavaPlugin {
    private static BackPack plugin;
    public static DataContainer data;
    public static Set<UUID> currentBackPack = new HashSet<>();

    public static BackPack getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        data = new DataContainer(plugin);
        plugin.getServer().getPluginManager().registerEvents(new LBPEvent(), plugin);
        getCommand("lbp").setExecutor(new LBPCommand());
    }

    @Override
    public void onDisable() {
    }
}
