package org.example.interfaces;

public interface ICipher {

    byte[] encrypt(byte[] block);

    byte[] decrypt(byte[] block);

    ICipher setKey(byte[] key);

    int getBlockLength();
}
