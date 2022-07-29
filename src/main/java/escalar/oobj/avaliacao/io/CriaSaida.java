package escalar.oobj.avaliacao.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CriaSaida {

    public String criaSaida(String message) throws IOException {

        String nome = CriaArquivo.nomeDiretorio;

        File arquivo = new File("src/main/resources/Saida/"+nome);
        arquivo.createNewFile();

        int index = message.indexOf("SUB-ITINERÁRIO : ") + 17;
        int index2= index + 7;

        String conteudo = message.substring(index,index2);

        int index3 = message.indexOf(";SEQ :") + 6;
        int index4 = index3 +3;

        String conteudo2 = message.substring(index3,index4);
        System.out.println(conteudo2);


        String dadoParaEscrever = conteudo +"|"+conteudo2+" ";


        FileWriter escreverArquivo = new FileWriter(arquivo, true);
        BufferedWriter memoriaEscrita = new BufferedWriter(escreverArquivo);

        memoriaEscrita.write(dadoParaEscrever);
        memoriaEscrita.newLine();
        memoriaEscrita.close();

        return message;
    }
}
