package org.example;


import lombok.extern.slf4j.Slf4j;
import org.example.manager.CipherManager;
import org.example.mode.CipherModes;
import org.example.padding.Paddings;

import java.io.IOException;
import java.nio.file.Paths;

import static org.example.essentials.BitEssentials.generateIV;
import static org.example.manager.CipherManager.EncryptionAlgorithm.DEAL_256;
import static org.example.manager.CipherManager.EncryptionAlgorithm.DES;
import static org.example.mode.CipherModes.Mode.*;
import static org.example.padding.Paddings.PaddingType.*;


public class Main {
    public static void main(String[] args) {


        // key = 133457788BBCDFF1
        var keyForDES = new byte[]{
                (byte) 0x13, (byte) 0x34, (byte) 0x57, (byte) 0x79,
                (byte) 0x9B, (byte) 0xBC, (byte) 0xDF, (byte) 0xF1
        };
        // key = 133457788BBCDFF11255F9E7911355BD
        var keyForDEAL128 = new byte[]{
                (byte) 0x13, (byte) 0x34, (byte) 0x57, (byte) 0x79,
                (byte) 0x9B, (byte) 0xBC, (byte) 0xDF, (byte) 0xF1,
                (byte) 0x12, (byte) 0x55, (byte) 0xF9, (byte) 0xE7,
                (byte) 0x91, (byte) 0x13, (byte) 0x55, (byte) 0xBD
        };
        // key = 133457788BBCDFF11255F9E7911355BD802F17DAAAC805CC
        var keyForDEAL192 = new byte[]{
                (byte) 0x13, (byte) 0x34, (byte) 0x57, (byte) 0x79,
                (byte) 0x9B, (byte) 0xBC, (byte) 0xDF, (byte) 0xF1,
                (byte) 0x12, (byte) 0x55, (byte) 0xF9, (byte) 0xE7,
                (byte) 0x91, (byte) 0x13, (byte) 0x55, (byte) 0xBD,
                (byte) 0x80, (byte) 0x2F, (byte) 0x17, (byte) 0xDA,
                (byte) 0xAA, (byte) 0xC8, (byte) 0x05, (byte) 0xCC
        };
        // key = 133457788BBCDFF11255F9E7911355BD802F17DAAAC805CCFFEDCBA72233DDC0
        var keyForDEAL256 = new byte[]{
                (byte) 0x13, (byte) 0x34, (byte) 0x57, (byte) 0x79,
                (byte) 0x9B, (byte) 0xBC, (byte) 0xDF, (byte) 0xF1,
                (byte) 0x12, (byte) 0x55, (byte) 0xF9, (byte) 0xE7,
                (byte) 0x91, (byte) 0x13, (byte) 0x55, (byte) 0xBD,
                (byte) 0x80, (byte) 0x2F, (byte) 0x17, (byte) 0xDA,
                (byte) 0xAA, (byte) 0xC8, (byte) 0x05, (byte) 0xCC,
                (byte) 0xFF, (byte) 0xED, (byte) 0xCB, (byte) 0xA7,
                (byte) 0x22, (byte) 0x33, (byte) 0xDD, (byte) 0xC0
        };

        CipherManager.EncryptionAlgorithm algorithm = DES;
        CipherModes.Mode mode = ECB;
        Paddings.PaddingType type = ANSIX923;
        int threads = 7;

        var inputFile = "/Users/a.lisnyak/Downloads/CryptographyLabs/src/main/resources/text/textTest.txt";
        

        var encryptedFile = "/Users/a.lisnyak/Downloads/CryptographyLabs/src/main/resources/text/testTest_encrepted.txt";
        var decryptedFile = "/Users/a.lisnyak/Downloads/CryptographyLabs/src/main/resources/text/textTest_decrepted.txt";

        var manager = new CipherManager(
                switch (algorithm) {
                    case DES -> keyForDES;
                    case DEAL_128 -> keyForDEAL128;
                    case DEAL_192 -> keyForDEAL192;
                    case DEAL_256 -> keyForDEAL256;
                },
                algorithm, mode, type, generateIV((algorithm.equals(DES) ? 64 : 128) / 8)
        );
        try {
            System.out.print("Algorithm: {}" + algorithm);
            System.out.print("Mode: {}" +  mode);
            System.out.print("Type: {}" + type);
            System.out.print("Threads: {}" + threads);
            System.out.print("initial file: {}" + inputFile);
            System.out.print("encrypting file...");
            manager.encryptFileAsync(inputFile
                    ,encryptedFile,threads);
            System.out.print("encrypted: {}" + encryptedFile);
        } catch (IOException e) {
            System.out.print("EncryptFileAsync Error! Message: {}" + e.getMessage());
        }

        try {
            System.out.print("decrypting file...");
            manager.decryptFileAsync(
                    encryptedFile, decryptedFile, threads);
            System.out.print("decrypted: {}" + decryptedFile);
        } catch (IOException e) {
            System.out.print("DecryptFileAsync Error! Message : {}" + e.getMessage());

        }


    }
}