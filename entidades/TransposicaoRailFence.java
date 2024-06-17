package entidades;

import java.util.ArrayList;
import java.util.List;

public class TransposicaoRailFence {
    private static byte[] Keyword = "CHAVE".getBytes();
    private int key;

    public TransposicaoRailFence() {
        key = Keyword.length;
    }

    public TransposicaoRailFence(String chave) {
        Keyword = chave.getBytes();
        key = Keyword.length;
    }

    private List<Integer> jaId(int tam) {
        List<Integer> idx = new ArrayList<>();
        int j = 0;
        int inc = 0;
        for (int i = 0; i < tam; i++) {
            if (j == key - 1) {
                inc = -1;
            } else if (j == 0) {
                inc = 1;
            }
            idx.add(j);
            j += inc;
        }
        return idx;
    }

    public byte[] cifra(byte[] texto) {
        int tam = texto.length;
        List<Integer> idx = jaId(tam);

        byte[] cifrado = new byte[tam];
        int pos = 0;
        for (int i = 0; i < key; i++) {
            for (int z = 0; z < tam; z++) {
                if (idx.get(z) == i) {
                    cifrado[pos++] = texto[z];
                }
            }
        }
        return cifrado;
    }

    public byte[] decifra(byte[] texto) {
        int tam = texto.length;
        List<Integer> idx = jaId(tam);

        byte[] textoPlano = new byte[tam];
        int pos = 0;
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < tam; j++) {
                if (idx.get(j) == i && pos < texto.length) {
                    textoPlano[j] = texto[pos++];
                }
            }
        }
        return textoPlano;
    }
}

    // public static void main(String[] args) {
    //     TransposicaoRailFence rf = new TransposicaoRailFence();
        
    //     // Exemplo de cifragem e decifragem
    //     String textoOriginal = "Esta é uma mensagem de exemplo.";
        
        
    //     // Cifragem
    //     String textoCifrado = rf.cifra(textoOriginal);
    //     System.out.println("Texto cifrado: " + textoCifrado);
        
    //     // Decifragem
    //     String textoDecifrado = rf.decifra(textoCifrado);
    //     System.out.println("Texto decifrado: " + textoDecifrado);
    // }

