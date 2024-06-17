package compactador;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.BitSet;
import compactador.LZW;


public class Compactador {
    public static void descompacta(String versao) {
        String backupFilePath = "./backups/backup_" + versao + ".db"; 
        String folderPath = "dados"; 
        RandomAccessFile backupFile;

        try {
            File dadosDir = new File(folderPath);
            if (!dadosDir.exists()) {
                dadosDir.mkdirs();
            }

            backupFile = new RandomAccessFile(backupFilePath, "r");

            while (backupFile.getFilePointer() < backupFile.length()) {
                // Lê o tamanho do nome do arquivo
                int tamNome = backupFile.readInt();

                // Lê o nome do arquivo em bytes
                byte[] nomeArquivo = new byte[tamNome];
                backupFile.read(nomeArquivo);
                String arquivo = new String(nomeArquivo);

                // Lê o tamanho dos dados compactados
                int tam = backupFile.readInt();

                // Lê os dados compactados
                byte[] ba = new byte[tam];
                backupFile.read(ba);

                // Descompacta os dados
                byte[] dadosDecodificados = LZW.decodifica(ba);

                // Escreve os dados descompactados no arquivo na pasta "dados"
                Path filePath = Paths.get(folderPath, arquivo);
                Files.write(filePath, dadosDecodificados);

                System.out.println("Arquivo: " + arquivo + " descompactado e criado em " + folderPath);
            }

            backupFile.close();
            System.out.println("Descompactação concluída com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void compacta() {
        String pastaDados = "dados";  
        String arquivoBackup = "./backups/backup_" + LocalDate.now() +  ".db";  // Caminho para o arquivo de backup
        RandomAccessFile backupFile;
    
        try {
            File backupDir = new File("./backups");
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            File arquivo = new File(arquivoBackup);
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            backupFile = new RandomAccessFile(arquivoBackup, "rw");
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pastaDados));
    
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    String fileName = path.getFileName().toString();
                    byte[] fileNameBytes = fileName.getBytes();
                    byte[] dataBytes = Files.readAllBytes(path);
                    byte[] compressedData = LZW.codifica(dataBytes);

                    backupFile.writeInt(fileNameBytes.length);
                    
                    backupFile.write(fileNameBytes);
                    
                    backupFile.writeInt(compressedData.length);
                    backupFile.write(compressedData);
                }
            }
            backupFile.close();
            System.out.println("Backup criado com sucesso em " + arquivoBackup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // public static void main(String[] args) {
    //     compacta();
    //     descompacta((LocalDate.now()).toString());
    // }
}
