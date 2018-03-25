package wit.cryptoexec.backend.api.bittrex.utils;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/*
    Code by platelminto in GitHub
    Code from https://github.com/platelminto/java-bittrex/blob/master/src/EncryptionUtility.java
    Modified by Harrison
 */

public class EncryptionUtility {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String calculateHash(String secret, String url) {
        String encryption = "HmacSHA512";
        Mac shaHmac = null;

        try {

            shaHmac = Mac.getInstance(encryption);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), encryption);

        try {

            shaHmac.init(secretKey);

        } catch (InvalidKeyException e) {

            e.printStackTrace();
        }

        byte[] hash = shaHmac.doFinal(url.getBytes());
        String check = bytesToHex(hash);

        return check;
    }

    public static String generateNonce() {
        Date date = new Date();
        return Long.toString(date.getTime());
    }

    private static String bytesToHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];

        for(int j = 0; j < bytes.length; j++) {

            int v = bytes[j] & 0xFF;

            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}