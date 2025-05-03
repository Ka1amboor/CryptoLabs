package org.example.impl;

import javax.crypto.Cipher;

import static org.example.config.des.DESCipherConf.FINAL_PERMUTATION;
import static org.example.config.des.DESCipherConf.INITIAL_PERMUTATION;
import static org.example.essentials.BitEssentials.permutation;

public class DESCipher extends FeistelNetwork{

    public DESCipher() {
        super(new DESCipherConversion(), new DESKeyGenerator(), 16);
        blockLength = 64 / 8;
    }

    @Override
    public byte[] encrypt(byte[] block) {
        var initialPermutedBlock = permutation(block, INITIAL_PERMUTATION);
        var encryptedBlock = super.encrypt(initialPermutedBlock);
        return permutation(encryptedBlock, FINAL_PERMUTATION);
    }

    @Override
    public byte[] decrypt(byte[] block) {
        var initialPermutedBlock = permutation(block, INITIAL_PERMUTATION);
        var encryptedBlock = super.decrypt(initialPermutedBlock);
        return permutation(encryptedBlock, FINAL_PERMUTATION);
    }
}
