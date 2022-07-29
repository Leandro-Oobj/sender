package escalar.oobj.avaliacao.controller;

import escalar.oobj.avaliacao.io.CriaArquivo;
import escalar.oobj.avaliacao.mensageria.Enfileirador;
import escalar.oobj.avaliacao.mensageria.Receiver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class IntegradorHttpController implements Runnable{
    static String auxConteudo = "";

    @RequestMapping("/api/pre-impressao")
    @PostMapping
    public ResponseEntity<String> entrada(@RequestBody String conteudo) throws NamingException, JMSException, IOException {

        auxConteudo = conteudo;

        if (conteudo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {

            ExecutorService threadPool = Executors.newCachedThreadPool();
            IntegradorHttpController integradorHttpController = new IntegradorHttpController();
            threadPool.execute(integradorHttpController);

            return new ResponseEntity<>("“preImpressaoSolicitada”: true", HttpStatus.OK);
        }
    }

    @Override
    public void run() {

            CriaArquivo criaArquivo = new CriaArquivo();
            try {
                criaArquivo.criaArquivo(auxConteudo);
            } catch (NamingException | JMSException e) {
                throw new RuntimeException(e);
            }

            synchronized (this) {
                try {
                    Enfileirador enfileirador = new Enfileirador();
                    enfileirador.enfileirador(auxConteudo);
                    Receiver receiver = new Receiver();
                    receiver.run();
                } catch (NamingException | JMSException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            criaArquivo.moveArquivo();
    }
}
