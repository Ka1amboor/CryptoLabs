package org.example.impl;

import org.example.interfaces.IKeyGenerator;
import org.example.mode.ACipherMode;
import org.example.mode.impl.CBCMode;

import javax.crypto.KeyGenerator;

import static java.lang.System.arraycopy;
import static org.example.config.deal.DEALExpandKeyConf.FIXED_DES_KEY;
import static org.example.essentials.BitEssentials.xorBits;
import static org.example.impl.DEALCipher.KeyType.KEY_SIZE_128;
import static org.example.impl.DEALCipher.KeyType.KEY_SIZE_192;

public class DEALKeyGenerator implements IKeyGenerator {

    private final ACipherMode DESModeCBC;
    private final int rounds;
    private final int s;

    public DEALKeyGenerator(DEALCipher.KeyType type) {
        var DES = new DESCipher();
        DES.setKey(FIXED_DES_KEY);
        this.DESModeCBC = new CBCMode(DES, DES.getBlockLength(), new byte[64 / 8]);
        switch (type) {
            case KEY_SIZE_128 -> {
                this.rounds = 6;
                this.s = 2;
            }
            case KEY_SIZE_192 -> {
                this.rounds = 6;
                this.s = 3;
            }
            default -> {
                this.rounds = 8;
                this.s = 4;
            }
        }
    }

    @Override
    public byte[][] generateRoundKeys(byte[] key) {
        byte[][] keys = new byte[rounds][];
        byte[][] keysDES = new byte[s][];

        for (int i = 0; i < s; i++) {
            byte[] initKey = new byte[8];
            arraycopy(key, 8 * i, initKey, 0, initKey.length);
            keysDES[i] = initKey;
        }

        keys[0] = DESModeCBC.encrypt(keysDES[0]);

        for (int i = 1; i < s; i++) {

            keys[i] = DESModeCBC.encrypt(xorBits(keysDES[i], keys[i - 1]));
        }

        var offset = 0;
        for (int i = s; i < rounds; i++) {
            long constant = 1L << (64 - (1 << offset));
            byte[] constantBytes = new byte[8];
            for (int j = 0; j < 8; j++) {
                constantBytes[j] = (byte) ((constant >>> ((7 - j) * 8)) & 0xFF);
            }
            keys[i] = DESModeCBC.encrypt(xorBits(xorBits(keysDES[i % s], constantBytes), keys[i - 1]));
        }

        return keys;
    }
}
