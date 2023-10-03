package com.example.desafio.util;

public class UrlUtils {

    public static int getIdFromUrl(String url) {
        StringBuilder str = new StringBuilder();
        int urlLen = url.length();
        while (urlLen != 0 && url.charAt(urlLen - 1) != '/') {
            str.append(url.charAt(urlLen - 1));
            urlLen--;
        }
        return Integer.parseInt(str.reverse().toString());
    }

}
