package com.mazhang.xierwedding.Utils;

import static org.apache.commons.codec.digest.DigestUtils.md5;

public class DigestUtils {
    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final byte[] data) {
        return Hex.encodeHexString(md5(data));
    }
}
