package arquivos;

import aeds3.Arquivo;
import aeds3.ArvoreBMais;
import aeds3.HashExtensivel;
import aeds3.ParIntInt;
import entidades.Livro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import aed3.ListaInvertida;

public class ArquivoLivros extends Arquivo<Livro> {

  HashExtensivel<ParIsbnId> indiceIndiretoISBN;
  ArvoreBMais<ParIntInt> relLivrosDaCategoria;
  ListaInvertida listaInvertida;

  public ArquivoLivros() throws Exception {
    super("livros", Livro.class.getConstructor());
    listaInvertida = new ListaInvertida(4, "dados/dicionario.listainv.db", "dados/blocos.listainv.db");
    indiceIndiretoISBN = new HashExtensivel<>(
        ParIsbnId.class.getConstructor(),
        4,
        "dados/livros_isbn.hash_d.db",
        "dados/livros_isbn.hash_c.db");
    relLivrosDaCategoria = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 4, "dados/livros_categorias.btree.db");

  }

  @Override
  public int create(Livro obj) throws Exception {
    int id = super.create(obj);
    obj.setID(id);
    String titulo = obj.getTitulo();
    String[] chavesSW = semAcento(titulo).toLowerCase().split(" ");
    String[] chaves = retirarSW(chavesSW);
    for(String i : chaves){
      listaInvertida.create(i, obj.getID());
    }
    indiceIndiretoISBN.create(new ParIsbnId(obj.getIsbn(), obj.getID()));
    relLivrosDaCategoria.create(new ParIntInt(obj.getIdCategoria(), obj.getID()));
    return id;
  }

  public Livro readISBN(String isbn) throws Exception {
    ParIsbnId pii = indiceIndiretoISBN.read(ParIsbnId.hashIsbn(isbn));
    if (pii == null)
      return null;
    int id = pii.getId();
    return super.read(id);
  }

  public Livro[] readTexto(String[] args) throws Exception{
    args = retirarSW(args);
    int id[], idResultado[];
    idResultado = listaInvertida.read(args[0]);
    //Cria um arrayList para cada vetor de inteiros retornado pela lista invertida
    for(String i : args){
      id = listaInvertida.read(i);
      ArrayList<Integer> lista1 = new ArrayList<>();
      for (int num : id) {
          lista1.add(num);
      }
      
      ArrayList<Integer> lista2 = new ArrayList<>();
      for (int num : idResultado) {
          lista2.add(num);
      }

      lista1.retainAll(lista2);
      idResultado = new int[lista1.size()];
      for (int j = 0; j < lista1.size(); j++) {
        idResultado[j] = lista1.get(j);
      }
      
    }
    if(idResultado.length == 0){
      return null;
    }

    Livro[] resultado = new Livro[20];

    for(int i = 0; i < idResultado.length; i++){
      resultado[i] = super.read(idResultado[i]);
    }
    return resultado;
  }

  @Override
  public boolean delete(int id) throws Exception {
    Livro obj = super.read(id);
    String titulo = obj.getTitulo();
    String[] chaves = retirarSW(semAcento(titulo).toLowerCase().split(" "));

    for(String i : chaves){
      listaInvertida.delete(i, obj.getID());
    }
    if (obj != null)
      if (indiceIndiretoISBN.delete(ParIsbnId.hashIsbn(obj.getIsbn()))
          &&
          relLivrosDaCategoria.delete(new ParIntInt(obj.getIdCategoria(), obj.getID())))
        return super.delete(id);
    return false;
  }

  @Override
  public boolean update(Livro novoLivro) throws Exception {
    Livro livroAntigo = super.read(novoLivro.getID());
    if (livroAntigo != null) {

      // Testa alteração do ISBN
      if (livroAntigo.getIsbn().compareTo(novoLivro.getIsbn()) != 0) {
        indiceIndiretoISBN.delete(ParIsbnId.hashIsbn(livroAntigo.getIsbn()));
        indiceIndiretoISBN.create(new ParIsbnId(novoLivro.getIsbn(), novoLivro.getID()));
      }

      // Testa alteração da categoria
      if (livroAntigo.getIdCategoria() != novoLivro.getIdCategoria()) {
        relLivrosDaCategoria.delete(new ParIntInt(livroAntigo.getIdCategoria(), livroAntigo.getID()));
        relLivrosDaCategoria.create(new ParIntInt(novoLivro.getIdCategoria(), novoLivro.getID()));
      }
      //apaga da lista invertida
      String titulo = livroAntigo.getTitulo();
      String novoTitulo = novoLivro.getTitulo();

      if(!(titulo.equals(novoTitulo))){
        String[] chaves = retirarSW(semAcento(titulo).toLowerCase().split(" "));
        for(String i : chaves){
          listaInvertida.delete(i, novoLivro.getID());
        }
        //reescreve na lista invertida
        
        chaves = retirarSW(semAcento(novoTitulo).toLowerCase().split(" "));
        for(String i : chaves){
          listaInvertida.create(i, novoLivro.getID());
        }

        // Atualiza o livro
        return super.update(novoLivro);
      }
    }
    return false;
  }

  public String [] retirarSW(String [] palavrasChaveSW) throws Exception{

    ArrayList<String> stopWords = new ArrayList<>(); //inicio um arraylist para stopwords
    ArrayList<String> palavrasChave = new ArrayList<>(); //inicio um arraylist para as palavras chave (titulo)

    //criando um arraylist das stopWords
    BufferedReader buffRead = new BufferedReader(new FileReader("dados/stopwords.txt")); //abre o arquivo para leitura
    String sw = "";
    while ((sw = buffRead.readLine()) != null) //enquanto não ler null
    {
      stopWords.add(semAcento(sw).toLowerCase().trim()); //adiciona ao arraylist (tudo minusculo para padronizar e tirando espaços)
    }
    buffRead.close(); //ao terminar fecha o bufferedreader

    //criando um arraylist com o titulo (ainda com stopwords)
    for(int i = 0; i < palavrasChaveSW.length; i++)
    {
      palavrasChave.add(palavrasChaveSW[i]); //adiciona todas as palavras chave ao arraylist
    }
    
    palavrasChave.removeAll(stopWords); //removo as stopwords das palavras chave
    
    String[] resposta = new String[palavrasChave.size()]; 
    
    for (int j = 0; j < palavrasChave.size(); j++)
    {
      resposta[j] = (String) palavrasChave.get(j); //coloca em resposta os valores salvos no arraylist
    }
    return resposta; //retorna a resposta
  }

  public static String semAcento(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
 }
}
