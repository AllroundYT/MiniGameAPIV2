package de.allround.util;


import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColorHelper {
    public static ChatColor[] getColorVerlauf(int charAmount, Color c1, Color c2) {
        int r1, r2, g1, g2, b1, b2;
        r1 = c1.getRed();
        r2 = c2.getRed();
        g1 = c1.getGreen();
        g2 = c2.getGreen();
        b1 = c1.getBlue();
        b2 = c2.getBlue();
        ChatColor[] output = new ChatColor[charAmount];
        for (int charIndex = 0; charIndex < charAmount; charIndex++) {
            int r, g, b;
            if (r1 > r2) {
                r = r1 - (charIndex * ((r1 - r2) / charAmount));
            } else if (r2 > r1) {
                r = r1 + (charIndex * ((r2 - r1) / charAmount));
            } else r = r1;

            if (g1 > g2) {
                g = g1 - (charIndex * ((g1 - g2) / charAmount));
            } else if (g2 > g1) {
                g = g1 + (charIndex * ((g2 - g1) / charAmount));
            } else g = g1;


            if (b1 > b2) {
                b = b1 - (charIndex * ((b1 - b2) / charAmount));
            } else if (b2 > b1) {
                b = b1 + (charIndex * ((b2 - b1) / charAmount));
            } else b = b1;

            Color color = new Color(r, g, b, 100);
            ChatColor.of(color);
        }
        return output;
    }

    public static String getTextWithGradiant(char[] chars, Color c1, Color c2) {
        String[] coloredChars = new String[chars.length];

        ChatColor[] colors = getColorVerlauf(chars.length, c1, c2);

        for (int i = 0; i < chars.length; i++) {
            coloredChars[i] = colors[i] + String.valueOf(chars[i]);
        }
        return String.join("", coloredChars);
    }

    public static String getTextWithRainbow(String rawText, Color... colors) {
        int colorAmount = colors.length;

        List<String> stringParts = new ArrayList<>();

        for (int colorIndex = 0; colorIndex < colorAmount; colorIndex++) {
            int minIndex = colorIndex * (rawText.toCharArray().length / colorAmount);
            int maxIndex = Math.max(rawText.toCharArray().length,(colorIndex + 1) * ((rawText.toCharArray().length / colorAmount)));
            stringParts.add(getTextWithGradiant(rawText.substring(minIndex,maxIndex).toCharArray(),colors[colorIndex],colors[Math.max(colorAmount-1,colorIndex+1)]));
        }

        return String.join("",stringParts);
    }
}
