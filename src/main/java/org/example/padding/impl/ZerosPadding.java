package org.example.padding.impl;

import org.example.interfaces.IPadding;

import java.util.Arrays;

public class ZerosPadding implements IPadding {

    @Override
    public byte[] addPadding(byte[] block, int size) {
        int n = block.length;
        int lengthPadding = size - (n % size);
        byte[] result = new byte[n + lengthPadding];
        System.arraycopy(block, 0, result, 0, n);
        return result;
    }

    @Override
    public byte[] removePadding(byte[] block) {
        int i = block.length - 1;
        while (block[i] == 0) {
            i--;
        }
        return Arrays.copyOf(block, i + 1);
    }
}
