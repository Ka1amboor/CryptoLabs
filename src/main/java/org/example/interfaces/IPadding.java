package org.example.interfaces;

public interface IPadding {

    byte[] addPadding(byte[] block, int size);

    byte[] removePadding(byte[] block);
}
