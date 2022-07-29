package escalar.oobj.avaliacao.mensageria;

import escalar.oobj.avaliacao.io.CriaMensagem;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.io.IOException;

public class Enfileirador{

    public String enfileirador(String conteudo) throws NamingException, JMSException, IOException {

            int index=0;
            int fim =0;
            String processado = null;

            CriaMensagem criaMensagem = new CriaMensagem();

            for(int i=0; i < conteudo.length(); i++){

                int newfim = conteudo.indexOf("25000;STAPLE_TOP_LEFT", fim);

                if(index == 0){
                    newfim = newfim +21;
                    processado = (conteudo.substring(index,newfim));
                    index = newfim;
                    i = newfim;
                    fim = newfim;
                    criaMensagem.criaMensagem(processado);
                }
                else {
                    newfim = newfim +21;
                    i = newfim;
                    index = fim +2;
                    fim = newfim;
                    processado = (conteudo.substring(index,newfim));
                    criaMensagem.criaMensagem(processado);
                }
            }
            return conteudo;

        }
    }




