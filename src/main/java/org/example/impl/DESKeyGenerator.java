package org.example.impl;

import org.example.interfaces.IKeyGenerator;

import javax.crypto.KeyGenerator;
import java.nio.ByteBuffer;

import static org.example.config.des.DESExpandKeyConf.*;
import static org.example.essentials.BitEssentials.leftCycleShift;
import static org.example.essentials.BitEssentials.permutation;

public class DESKeyGenerator implements IKeyGenerator {

    private final int rounds = 16;
    private final int halfKeySize = 28;

    private int makeC(byte[] pKey) {
        var byteBuffer = ByteBuffer.wrap(pKey);

        return ((byteBuffer.get() & 0xFF) << 20)
                | ((byteBuffer.get() & 0xFF) << 12)
                | ((byteBuffer.get() & 0xFF) << 4)
                | ((byteBuffer.get() & 0xFF) >>> 4);
    }

    private int makeD(byte[] pKey) {
        var byteBuffer = ByteBuffer.wrap(pKey);
        byteBuffer.position(3);

        return ((byteBuffer.get() & 0x0F) << 24)
                | ((byteBuffer.get() & 0xFF) << 16)
                | ((byteBuffer.get() & 0xFF) << 8)
                | ((byteBuffer.get() & 0xFF));
    }

    private byte[] makeCD(int C, int D) {
        long CD = D | ((long) C) << halfKeySize;

        byte[] bytes = new byte[7];
        for (int j = 0; j < 7; ++j) {
            bytes[j] = (byte) ((CD >>> ((6 - j) * 8)) & 0xFF);
        }
        return bytes;
    }

    @Override
    public byte[][] generateRoundKeys(byte[] key) {
        byte[][] keys = new byte[rounds][];
        byte[] permutedKey = permutation(key, PC_1);
        int C = makeC(permutedKey);
        int D = makeD(permutedKey);

        for (int i = 0; i < rounds; i++) {
            C = leftCycleShift(CYCLE_SHIFTS[i], C, halfKeySize);
            D = leftCycleShift(CYCLE_SHIFTS[i], D, halfKeySize);
            var CD = makeCD(C, D);
            keys[i] = permutation(CD, PC_2);
        }

        return keys;
    }



}
