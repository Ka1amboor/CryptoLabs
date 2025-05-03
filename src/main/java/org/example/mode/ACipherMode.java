package org.example.mode;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.interfaces.ICipher;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ACipherMode {

    protected final ICipher encryptor;
    protected final int lengthBlock;
    protected final byte[] IV;

    public abstract byte[] encrypt(byte[] data);
    public abstract byte[] decrypt(byte[] data) ;
}
