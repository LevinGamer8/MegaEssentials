package de.megaessentialsrecode.utils;

import java.net.URL;

public class UpdateUTILS {

    private static final String GITHUB_API_URL_VIAVERSION = "https://api.github.com/repos/ViaVersion/ViaVersion/releases/latest";

    public static URL VIAVERSION_URL() {
        try {
            return new URL(GITHUB_API_URL_VIAVERSION);
        } catch (Exception e) {

        }
        return null;
    }


}
