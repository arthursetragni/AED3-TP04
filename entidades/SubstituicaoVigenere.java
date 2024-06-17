package entidades;

import java.util.*;

public class SubstituicaoVigenere {

    private static byte[] Keyword = "ARTHUR".getBytes();

    public SubstituicaoVigenere() {}

    public SubstituicaoVigenere(String chave) {
        Keyword = chave.getBytes();
    }

    public byte[] generateKey(byte[] str, byte[] key) {
        int x = str.length;
        byte[] keyBytes = new byte[str.length];
        for (int i = 0; i < str.length; i++) {
            keyBytes[i] = key[i % key.length];
        }
        return keyBytes;
    }

    public byte[] cipherText(byte[] str, byte[] key) {
        byte[] cipher_text = new byte[str.length];
        for (int i = 0; i < str.length; i++) {
            cipher_text[i] = (byte) (str[i] + key[i]);
        }
        return cipher_text;
    }

    public byte[] originalText(byte[] cipher_text, byte[] key) {
        byte[] orig_text = new byte[cipher_text.length];
        for (int i = 0; i < cipher_text.length; i++) {
            orig_text[i] = (byte) (cipher_text[i] - key[i]);
        }
        return orig_text;
    }

    public byte[] cifra(byte[] Str) {
        byte[] key = generateKey(Str, Keyword);
        return cipherText(Str, key);
    }

    public byte[] decifra(byte[] Str) {
        byte[] key = generateKey(Str, Keyword);
        return originalText(Str, key);
    }


    // public static void main(String[] args) {
    //     String Str = "GEEKS for geeks";

    //     SubstituicaoVigenere sub = new SubstituicaoVigenere();

    //     String key = sub.generateKey(Str, Keyword);

    //     System.out.println("Key: " + key + "\n");

    //     String cipher_text = sub.cipherText(Str, key);

    //     System.out.println("Ciphertext: " + cipher_text + "\n");

    //     String decrypted_text = sub.originalText(cipher_text, key);

    //     System.out.println("Original/Decrypted Text: " + decrypted_text);
    // }
 }
