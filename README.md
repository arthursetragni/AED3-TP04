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


# Classe `SubstituicaoVigenere`

A classe `SubstituicaoVigenere` implementa a cifra de Vigenère para operações de cifragem e decifragem de texto.

## Atributos

- `private static byte[] Keyword`: Chave utilizada na cifra de Vigenère. O valor padrão é `"ARTHUR"`.

## Construtores

### `SubstituicaoVigenere()`
Construtor padrão que inicializa a chave padrão.

### `SubstituicaoVigenere(String chave)`
Construtor que inicializa a chave com o valor fornecido.

## Métodos

### `byte[] generateKey(byte[] str, byte[] key)`
Gera uma chave repetida para igualar o tamanho do texto a ser cifrado.

### `byte[] cipherText(byte[] str, byte[] key)`
Cifra o texto utilizando a chave fornecida.

### `byte[] originalText(byte[] cipher_text, byte[] key)`
Decifra o texto cifrado utilizando a chave fornecida.

### `byte[] cifra(byte[] Str)`
Cifra o texto utilizando a cifra de Vigenère com a chave definida.

### `byte[] decifra(byte[] Str)`
Decifra o texto utilizando a cifra de Vigenère com a chave definida.

---

# Classe `TransposicaoRailFence`

A classe `TransposicaoRailFence` implementa a cifra Rail Fence para operações de cifragem e decifragem de texto.

## Atributos

- `private static byte[] Keyword`: Chave utilizada na cifra Rail Fence. O valor padrão é `"CHAVE"`.
- `private int key`: Número de "trilhos" usados na cifra Rail Fence.

## Construtores

### `TransposicaoRailFence()`
Construtor padrão que inicializa a chave padrão.

### `TransposicaoRailFence(String chave)`
Construtor que inicializa a chave com o valor fornecido.

## Métodos

### `private List<Integer> jaId(int tam)`
Gera a sequência de índices para a cifra Rail Fence.

### `byte[] cifra(byte[] texto)`
Cifra o texto utilizando a cifra Rail Fence.

### `byte[] decifra(byte[] texto)`
Decifra o texto cifrado utilizando a cifra Rail Fence.

**Nossa experiência fazendo**: Conseguimos fazer o trabalho implementando tudo que foi pedido, não foi uma atividade complexa de se entender o problema e nem tão difícil de se implementar. Levou algum tempo mas deu certo.

**Questionário**
*Todas as classes são cifradas por dois metodos que usam a mesma chave, que pode ser mudada na classe CifragemDecifragem.

*Uma das operações de cifragem é baseada na substituição e a outra na transposição.

*O trabalho está funcionando corretamente.

*O trabalho está completo.

*O trabalho é original e não a cópia de um trabalho de um colega.
