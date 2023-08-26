package org.betelgem.serverchecker;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.betelgem.serverchecker.model.serverInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public final class ServerChecker extends JavaPlugin {

    private HttpServer _httpServer;
    private String _callbackUrl;
    @Override
    public void onEnable() {
        _httpServer = new HttpServer();
        _httpServer.start();

//        _callbackUrl =  getConfig().getString("callback_join_player");
//        _httpServer.serverInfoUpdate(_callbackUrl, createServerInfo(true));

        saveDefaultConfig();


        getServer().getPluginManager().registerEvents(new Events(this, _httpServer), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("getdonat"))
        {
            if(sender instanceof Player) {
                ArrayList<String> sticklore = new ArrayList<>();

                Player player = (Player) sender; // The player who joined
                PlayerInventory inventory = player.getInventory(); // The player's inventory
                ItemStack handItemstack = inventory.getItemInMainHand();
                NBT.modify(handItemstack, nbt ->{
                    Bukkit.broadcastMessage(nbt.getString("DEUpgrades"));
                });
                ItemStack itemstack = new ItemStack(Material.valueOf("DRACONICEVOLUTION_DRACONIC_STAFF_OF_POWER")); // A stack of diamonds
                ItemMeta metaData = itemstack.getItemMeta();
                sticklore.add("Донат машина");
                sticklore.add("{Energy: 48000000}");
                metaData.setDisplayName(ChatColor.DARK_PURPLE + "Донат машина пушка");
                metaData.setLore(sticklore);
                itemstack.setItemMeta(metaData);

                            NBT.modify(itemstack, nbt -> {
                                String str = "{attackDmg: 3, digSpeed: 3, digAOE: 3, rfCap: 3, attackAOE: 3}";
                                byte[] output3 = str.getBytes(StandardCharsets.UTF_8);
                                nbt.setString("DEUpgrades","{attackDmg: 3, digSpeed: 3, digAOE: 3, rfCap: 3, attackAOE: 3}");
                                nbt.setInteger("Energy", 48000000);
                });

                inventory.addItem(itemstack);
                if (inventory.contains(itemstack)) {
                    inventory.addItem(); // Adds a stack of diamonds to the player's inventory
                    player.sendMessage("Welcome! You seem to be reeeally rich, so we gave you some more diamonds!");
                }
            }
        }
        if(command.getName().equalsIgnoreCase("getdonatcommand"))
        {
            if(sender instanceof Player) {
                Player player = (Player) sender; // The player who joined
                //Bukkit.broadcastMessage("minecraft:give "+ player.getName().toString() +" draconicevolution:draconic_staff_of_power 1 0 {DEUpgrades: {attackDmg: 3, digSpeed: 3, digAOE: 3, rfCap: 3, attackAOE: 3}, Energy: 768000000}");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"minecraft:give " + player.getName().toString()
                        + " draconicevolution:draconic_staff_of_power 1 0 {DEUpgrades: {attackDmg: 3, digSpeed: 3, digAOE: 3, rfCap: 3, attackAOE: 3}, Energy: 768000000}");
            }
        }


        return true;
    }

    @Override
    public void onDisable() {
        _httpServer.serverInfoUpdate(_callbackUrl, createServerInfo(false));
    }
    private serverInfo createServerInfo(boolean isOnline){
        serverInfo sinfo = new serverInfo();
        sinfo.Id = 1;
        sinfo.Name = Bukkit.getServer().getServerName();
        sinfo.CurrentPlayers = Bukkit.getServer().getOnlinePlayers().size();
        sinfo.TotalPlayers = Bukkit.getServer().getMaxPlayers();
        sinfo.IsOnline = isOnline;
        return sinfo;
    }
}
