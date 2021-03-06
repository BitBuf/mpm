package dev.dewy.mpm.utils;

import com.google.gson.JsonObject;
import dev.dewy.mpm.MPM;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class NetUtils {
    public static void download(URL url, File destination) throws IOException {
        if (MPM.basicSettings.isVerbose()) {
            System.out.println("Downloading " + url + " to " + destination);
        }

        ReadableByteChannel channel = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(destination);

        fos.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
    }

    public static boolean exists(URL url) {
        if (MPM.basicSettings.isVerbose()) {
            System.out.println("Validating URL " + url);
        }

        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("HEAD");

            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            if (MPM.basicSettings.isVerbose()) {
                System.out.println("URL " + url + " does not have a thing that exists.");
            }

            return false;
        }
    }

    public static String getUuidFromName(String name) throws IOException {
        if (MPM.basicSettings.isVerbose()) {
            System.out.println("Getting UUID from name " + name);
        }

        HttpURLConnection con = (HttpURLConnection) new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
        StringBuilder sb = new StringBuilder();

        String tmp;
        while ((tmp = br.readLine()) != null) {
            sb.append(tmp);
        }

        return MPM.GSON.fromJson(sb.toString(), JsonObject.class).get("id").getAsString();
    }
}
