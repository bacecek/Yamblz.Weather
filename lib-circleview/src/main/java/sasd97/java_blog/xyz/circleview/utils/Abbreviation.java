package sasd97.java_blog.xyz.circleview.utils;

import java.util.Locale;

/**
 * Created by alexander on 30/04/2017.
 */

public final class Abbreviation {

    private static final String SPACE_SIGN = " ";
    private static final String EMPTY = "";

    private Abbreviation() {
    }

    public static String make(String text) {
        if (text == null) return EMPTY;
        String temp = text.trim().toUpperCase(Locale.US);

        if (temp.contains(SPACE_SIGN)) {
            String[] slice = temp.split(SPACE_SIGN);
            StringBuilder builder = new StringBuilder();

            builder
                    .append(slice[0].charAt(0))
                    .append(slice[1].charAt(0));

            return builder.toString();
        }

        return String.valueOf(temp.charAt(0));
    }
}
