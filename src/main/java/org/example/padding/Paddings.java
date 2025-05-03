package org.example.padding;

import org.example.interfaces.IPadding;
import org.example.padding.impl.ANSIX923Padding;
import org.example.padding.impl.ISO10126Padding;
import org.example.padding.impl.PKCS7Padding;
import org.example.padding.impl.ZerosPadding;

public class Paddings {

    public enum PaddingType {
        ZEROS, ANSIX923, ISO10126, PKCS7
    }

    public static IPadding getPadding(PaddingType type) {
        return switch (type) {
            case ZEROS -> new ZerosPadding();
            case ANSIX923 -> new ANSIX923Padding();
            case ISO10126 -> new ISO10126Padding();
            case PKCS7 -> new PKCS7Padding();
        };
    }
}
