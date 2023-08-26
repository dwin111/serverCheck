package org.betelgem.serverchecker;
import org.betelgem.serverchecker.model.serverInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {

    private Plugin pluign;
    private HttpServer httpServer;

    public Events(Plugin plugin, HttpServer httpServer){
        this.pluign = plugin;
        this.httpServer = httpServer;
        this.pluign.saveDefaultConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Bukkit.broadcastMessage("Welcome to the server!");
//        String callbackUrl =  pluign.getConfig().getString("callback_join_player");
//        httpServer.serverInfoUpdate(callbackUrl, createServerInfo());


    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Bukkit.broadcastMessage("Left");
//        String callbackUrl =  pluign.getConfig().getString("callback_join_player");
//        httpServer.serverInfoUpdate(callbackUrl, createServerInfo());
    }


    private serverInfo createServerInfo(){
        serverInfo sinfo = new serverInfo();
        sinfo.Id = 1;
        sinfo.Name = Bukkit.getServer().getServerName();
        sinfo.CurrentPlayers = Bukkit.getServer().getOnlinePlayers().size();
        sinfo.TotalPlayers = Bukkit.getServer().getMaxPlayers();
        sinfo.IsOnline = true;
        return sinfo;
    }

}