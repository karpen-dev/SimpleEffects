package com.karpen.simpleEffects.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

public class CheckVersion {

    private JavaPlugin plugin;

    public CheckVersion(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public boolean isLatest(){
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.modrinth.com/v2/project/SimpleEffects/version";

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "SimpleEffects/" + plugin.getDescription().getVersion())
                    .build();

            try(Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()){
                    return false;
                }

                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONArray(responseBody).getJSONObject(0);

                return jsonResponse.getString("version_number").equalsIgnoreCase(plugin.getDescription().getVersion());
            }
        } catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }
}
