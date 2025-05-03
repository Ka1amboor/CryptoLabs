package org.example.impl;

import org.example.interfaces.ICipher;
import org.example.interfaces.ICipherConversion;

public class DEALCipherConversion implements ICipherConversion {

    @Override
    public byte[] encode(byte[] block, byte[] rKey) {
        return new DESCipher()
                .setKey(rKey)
                .encrypt(block);
    }


}
