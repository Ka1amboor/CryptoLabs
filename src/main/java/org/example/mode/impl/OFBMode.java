package org.example.mode.impl;

import org.example.interfaces.ICipher;
import org.example.mode.ACipherMode;

import static org.example.essentials.BitEssentials.xorBits;

public class OFBMode extends ACipherMode {

    public OFBMode(ICipher encryptor, int lengthBlock, byte[] IV) {
        super(encryptor, lengthBlock, IV);
    }

    @Override
    public byte[] encrypt(byte[] data) {
        byte[] result = new byte[data.length];
        byte[] prev = IV;
        for (int i = 0; i < data.length / lengthBlock; ++i) {
            int startIndex = i * lengthBlock;
            byte[] block = new byte[lengthBlock];
            System.arraycopy(data, startIndex, block, 0, lengthBlock);
            byte[] encryptedPart = encryptor.encrypt(prev);
            byte[] encryptedBlock = xorBits(block, encryptedPart);
            System.arraycopy(encryptedBlock, 0, result, startIndex, encryptedBlock.length);
            prev = encryptedPart;
        }
        return result;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return encrypt(data);
    }
}
