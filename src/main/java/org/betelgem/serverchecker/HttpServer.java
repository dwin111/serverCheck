package org.betelgem.serverchecker;
import static spark.Spark.*;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.betelgem.serverchecker.model.serverInfo;
import org.bukkit.Bukkit;
import spark.Request;

import static spark.Spark.get;

public class HttpServer {

    void server()
    {
        get("/server_info", (request, response) -> {
            long memMax = Runtime.getRuntime().maxMemory();
            long memTotal = Runtime.getRuntime().totalMemory();
            long memFree = Runtime.getRuntime().freeMemory();
            org.bukkit.Server bukkitServer = Bukkit.getServer();

            return  bukkitServer.toString() + " Memory: Max - " + memMax +" Total - " + memTotal +" Free - " + memFree;
        });
        get("/info_online", (request, response) -> {
            int playersOnline = Bukkit.getServer().getOnlinePlayers().size();
            int maxPlayers = Bukkit.getServer().getMaxPlayers();

            return playersOnline + "/" + maxPlayers;
        });
        get("/buy_apple/:Player", (request, response) -> {
            int playersOnline = Bukkit.getServer().getOnlinePlayers().size();
            int maxPlayers = Bukkit.getServer().getMaxPlayers();

            return playersOnline + "/" + maxPlayers;
        });
    }
    void start()
    {
        server();

        get("/", (request, response) -> {
            return true;
        });

    }
    public void serverInfoUpdate(String callbackUrl, serverInfo data){
        Unirest.config().verifySsl(false);
        HttpResponse<JsonNode> jsonResponse
                = Unirest.put(callbackUrl)
                .header("content-type", "application/json")
                .body(data)
                .asJson();
    }


}
