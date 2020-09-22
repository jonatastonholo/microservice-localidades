package com.microservices.localidades.util;

import java.text.Normalizer;

/**
 * @author Jônatas Tonholo
 *
 * A class with String util functions
 */
public abstract class StringUtils {

    /**
     * Normalize the string replacing accented characters
     * @param str
     * @return
     */
    public static String normalizeString(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }
}
