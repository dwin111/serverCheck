package org.betelgem.serverchecker.util;

import jdk.nashorn.internal.runtime.regexp.joni.constants.StringType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigUtil {
    private File file;
    private FileConfiguration config;

    public ConfigUtil(Plugin plugin, String path){
        this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
    }
    public ConfigUtil(String path){
       this.file = new File(path);
       this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save()
    {
        try
        {
            this.config.save(this.file);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public File getFile()
    {
        return file;
    }
    public FileConfiguration getConfig()
    {
        return config;
    }



}
