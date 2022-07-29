package escalar.oobj.avaliacao.io;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class CriaArquivo{

    File nomeProcessado = new File("");
    public static String nomeDiretorio = "";


    public Boolean criaArquivo(String conteudo) throws NamingException, JMSException {
        try {
            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime dateTime = ZonedDateTime.now(zid);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timeStamp = dateTime.format(formatter);

            String nomeArquivo = "pre-impressao-"+timeStamp+".txt";
            nomeDiretorio = nomeArquivo;

            File arquivo = new File("src/main/resources/entrada/"+nomeArquivo);
            nomeProcessado = arquivo;
            arquivo.createNewFile();

            FileWriter escreverArquivo = new FileWriter(arquivo, true);
            BufferedWriter memoriaEscrita = new BufferedWriter(escreverArquivo);

            memoriaEscrita.write(conteudo);
            memoriaEscrita.close();

            return arquivo.createNewFile();

        } catch (IOException e) {
            System.out.println("deuruim");
            return false;
        }
    }

    public Boolean moveArquivo(){
        File diretorioDestino = new File("src/main/resources/processado/");

        boolean sucesso = nomeProcessado.renameTo(new File(diretorioDestino, nomeProcessado.getName()));
        if (sucesso) {
            System.out.println("Arquivo movido para '" + diretorioDestino.getAbsolutePath() + "'");
        } else {
            System.out.println("Erro ao mover arquivo '" + nomeProcessado.getAbsolutePath() + "' para '"
                    + diretorioDestino.getAbsolutePath() + "'");
        }
        return null;
    }

}
