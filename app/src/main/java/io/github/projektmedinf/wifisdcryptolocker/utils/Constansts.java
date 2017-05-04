package io.github.projektmedinf.wifisdcryptolocker.utils;

/**
 * Created by stiefel40k on 14.04.17.
 */
public class Constansts {
    public static final String CURRENT_USER_KEY = "current_user";
    public static final String USER_NAME ="wifiCryptoSDLockerUser";
    public static final int KEY_LENGTH = 128;
    public static final String AES_CTR_NO_PADDING = "AES/CTR/NoPadding";
    public static final String AES = "AES";

    // TODO OpenJDK can no SHA512 so it is SHA1
    public static final String PBKDF_2_WITH_HMAC_SHA_1 = "PBKDF2WithHmacSHA1";

    public static final String BOUNCY_CASTLE = "BC";

}
