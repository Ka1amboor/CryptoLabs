package org.example.impl;

import org.example.interfaces.ICipherConversion;

import javax.crypto.Cipher;

import static org.example.config.des.DESCipherConversionConf.P_BLOCK_EXPAND;
import static org.example.config.des.DESCipherConversionConf.P_BLOCK_PLAIN;
import static org.example.essentials.BitEssentials.*;

public class DESCipherConversion implements ICipherConversion {

    @Override
    public byte[] encode(byte[] block, byte[] rKey) {
        byte[] result;
        result = permutation(block, P_BLOCK_EXPAND);
        result = xorBits(result, rKey);
        result = substitution(result);
        result = permutation(result, P_BLOCK_PLAIN);

        return result;
    }
}
