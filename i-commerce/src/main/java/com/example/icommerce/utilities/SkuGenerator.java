////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * Copyright © 2021 Unified Social, Inc.
 * 180 Madison Avenue, 23rd Floor, New York, NY 10016, U.S.A.
 * All rights reserved.
 *
 * This software (the "Software") is provided pursuant to the license agreement you entered into with Unified Social,
 * Inc. (the "License Agreement").  The Software is the confidential and proprietary information of Unified Social,
 * Inc., and you shall use it only in accordance with the terms and conditions of the License Agreement.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND "AS AVAILABLE."  UNIFIED SOCIAL, INC. MAKES NO WARRANTIES OF ANY KIND, WHETHER
 * EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 */

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.icommerce.utilities;

public final class SkuGenerator {
    private static String DEFAULT_PREFIX_VALUE = "ICM";
    private static long   value                = 1;

    private SkuGenerator () {
    }

    public static String generate () {
        return generate(null);
    }

    public static String generate (String prefix) {
        if ( value > 100000 ) {
            reset();
        }

        StringBuilder builder = new StringBuilder(DEFAULT_PREFIX_VALUE);

        if ( prefix != null ) {
            if ( prefix.length() > 3 ) {
                builder.append(prefix.substring(0, 3).toUpperCase() + String.format("%06d", value));
            }
            else {
                int leadingZeros = 9 - prefix.length();
                builder.append(prefix.toUpperCase() + String.format("%0" + leadingZeros + "d", value));
            }
        }
        else {
            builder.append(String.format("%09d", value));
        }
        value++;
        return builder.toString();
    }

    public static void reset () {
        value = 1;
    }

}
