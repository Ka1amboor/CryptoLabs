package org.example.mode;

import org.example.interfaces.ICipher;
import org.example.mode.impl.*;

public class CipherModes {

    public enum Mode {
        ECB,
        CBC, PCBC,
        CFB, OFB,
        CTR, RandomDelta
    }

    public static ACipherMode getMode(
            Mode mode,
            ICipher encryptor,
            byte[] InitializationVector) {
        int size = encryptor.getBlockLength();
        return switch (mode) {
            case ECB -> new ECBMode(encryptor, size, InitializationVector);
            case CBC -> new CBCMode(encryptor, size, InitializationVector);
            case PCBC -> new PCBCMode(encryptor, size, InitializationVector);
            case CFB -> new CFBMode(encryptor, size, InitializationVector);
            case OFB -> new OFBMode(encryptor, size, InitializationVector);
            case CTR -> new CTRMode(encryptor, size, InitializationVector);
            case RandomDelta -> new RandomDeltaMode(encryptor, size, InitializationVector);
        };
    }
}
