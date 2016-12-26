package br.com.nextel.cleanversion.bill.util;

import java.text.DecimalFormat;

/**
 * Created by renato.soares on 12/22/16.
 */

public class PriceUtils {

    public static String formatValueToText(Object text) {
        return String.format("R$ %.2f", text).replace(".", ",");
    }

}
