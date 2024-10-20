package co.smashmc.parautorestart;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class ParAutoRestart extends JavaPlugin {

    private static final int port = 10012;
    public static Plugin plugin;

    private static HttpServer s;

    @Override
    public void onEnable() {
        plugin = this;

        async(() -> {
            try {
                s = HttpServer.create(new InetSocketAddress(port), 0);
                s.createContext("/reload", new ReloadHandler());
                s.setExecutor(null);
                s.start();

                this.getLogger().warning("started http server on port " + port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onDisable() {
        if (s != null) s.stop(1);
    }

    public static void sync(Runnable r) {
        Bukkit.getScheduler().runTask(plugin, r);
    }

    public static void async(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, r);
    }
}
