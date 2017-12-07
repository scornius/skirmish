package org.nerdizin.skirmish.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Translator {

    private static ResourceBundle BUNDLE;

    public static void init(final Locale locale) {
        BUNDLE = ResourceBundle.getBundle("resources.bundles.messages", locale);
    }

    public static String translate(final String key) {
        return BUNDLE.getString(key);
    }

    public static String translate(final String key, final Object... params) {
        try {
            return MessageFormat.format(BUNDLE.getString(key), params);
        } catch (final MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
