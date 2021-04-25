package dev.dewy.mpm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class NetUtils {
    public static void download(URL url, File destination) throws IOException {
        ReadableByteChannel channel = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(destination);

        fos.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
    }

    public static boolean exists(URL url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("HEAD");

            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }
}
