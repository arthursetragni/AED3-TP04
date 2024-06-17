package entidades;
public class CifrandoDecifrando {

    SubstituicaoVigenere vigenere;
    TransposicaoRailFence railFence;
    String chave = "ARTHURGUSTAVO";

    public CifrandoDecifrando(){
        vigenere = new SubstituicaoVigenere(chave);
        railFence = new TransposicaoRailFence(chave);
    }

    public byte[] cifra(byte[] texto){
        texto = vigenere.cifra(texto);
        texto = railFence.cifra(texto);
        return texto;
    }

    public byte[] decifra(byte[] texto){
        texto = railFence.decifra(texto);
        texto = vigenere.decifra(texto);
        return texto;
    }

    // public static void main(String args[]){

    //     CifrandoDecifrando cd = new CifrandoDecifrando();

    //     String teste = "TESTaNDO MELHOR";
    //     String cifrado = cd.cifra(teste);
    //     String decifrado = cd.decifra(cifrado);
    //     System.out.println(teste +" "+ cifrado + " " + decifrado);
    // }
}
