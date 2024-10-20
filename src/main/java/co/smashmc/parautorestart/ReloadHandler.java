package co.smashmc.parautorestart;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.OutputStream;

import static co.smashmc.parautorestart.ParAutoRestart.async;
import static co.smashmc.parautorestart.ParAutoRestart.sync;

public class ReloadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) {
        async(() -> {
            String res = "reloaded";
            try {
                t.sendResponseHeaders(200, res.length());
                OutputStream os = t.getResponseBody();
                os.write(res.getBytes());
                os.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        sync(() -> Bukkit.getServer().reload());
    }
}
