**# AED3-TP04**  
**Backup compactado**

Adição do arquivo **CifrandoDecifrando** na pasta Entidades:
# Classe `CifrandoDecifrando`

A classe `CifrandoDecifrando` realiza operações de cifragem e decifragem utilizando dois métodos de criptografia: Substituição Vigenère e Transposição Rail Fence. A chave utilizada para ambas as operações é `"ARTHURGUSTAVO"`.

## Atributos

- `SubstituicaoVigenere vigenere`: Instância da classe `SubstituicaoVigenere` utilizada para cifragem e decifragem pelo método Vigenère.
- `TransposicaoRailFence railFence`: Instância da classe `TransposicaoRailFence` utilizada para cifragem e decifragem pelo método Rail Fence.
- `String chave`: Chave de criptografia utilizada nos métodos Vigenère e Rail Fence. O valor padrão é `"ARTHURGUSTAVO"`.

## Construtor

### `CifrandoDecifrando()`
Inicializa as instâncias de `SubstituicaoVigenere` e `TransposicaoRailFence` com a chave definida.

```java
public CifrandoDecifrando(){  
    vigenere = new SubstituicaoVigenere(chave);  
    railFence = new TransposicaoRailFence(chave);  
}
```

## Métodos

### `byte[] cifra(byte[] texto)`
Realiza a cifragem do texto utilizando primeiro o método Vigenère e, em seguida, o método Rail Fence.

- **Parâmetros**: 
  - `byte[] texto`: Texto em bytes a ser cifrado.
- **Retorno**: 
  - `byte[]`: Texto cifrado em bytes.

```java
public byte[] cifra(byte[] texto){  
    texto = vigenere.cifra(texto);  
    texto = railFence.cifra(texto);  
    return texto;  
}
```

### `byte[] decifra(byte[] texto)`
Realiza a decifragem do texto utilizando primeiro o método Rail Fence e, em seguida, o método Vigenère.

- **Parâmetros**: 
  - `byte[] texto`: Texto em bytes a ser decifrado.
- **Retorno**: 
  - `byte[]`: Texto decifrado em bytes.

```java
public byte[] decifra(byte[] texto){  
    texto = railFence.decifra(texto);  
    texto = vigenere.decifra(texto);  
    return texto;  
}
```



Adição do arquivo **Compactador**:

1. Criamos a classe Compactador onde fazemos as rotinas de compressão dos dados em arquivos de BackUp e de descompressão destes dados de volta para a pasta dados.
2. Criamos a função `compacta`, que percorre o diretório de dados lendo todos os arquivos e armazenando no arquivo de BackUp o nome do arquivo em bytes, indicador de tamanho dos dados comprimidos e o arquivo comprimido.
3. Dentro da função `compacta` o método `codifica` do LZW é chamado uma vez para arquivo no diretório de dados.
4. Criamos a função `descompacta`, que recebe a versão do BackUp a ser recuperado. A função lê o nome do arquivo, o tamanho dos dados compactados e o prório array de bytes, e cria na pasta de dados o arquivo com o nome recuperado e escreve no mesmo os dados após serem descompactados.
5. Dentro da função `descompacta` o método `decodifica` do LZW é chamado uma vez para arquivo salvo no BackUp.

**Observações**:
- O sistema guarda apenas um BackUp por dia

**Nossa experiência fazendo**: Conseguimos fazer o trabalho implementando tudo que foi pedido, não foi uma atividade complexa de se entender o problema e nem tão difícil de se implementar. Eu (Gustavo Garcia) tive um problema para conseguir fazer o método de descompressão corretamente, mas depois de algum tempo consegui entender a implementação.

**Questionário**
* Foi feita uma rotina de compactação utilizando o algorítimo do LZW para fazer o backup dos arquivos

* Foi feita uma rotina de descompactação utilizando o algorítimo do LZW para recuperar o backup dos arquivos

* O usuário pode escolher qual versão recuperar

* Conseguimos uma taxa de aproximadamente 69% de compressão (0,69801145938658577687900235928547)

* O trabalho funciona corretamente 

* O trabalho está completo 

* O trabalho não é cópia 
