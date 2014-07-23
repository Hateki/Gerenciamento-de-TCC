/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.model.web;

import java.io.File;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author pontofrio
 */
public class EnvioEmails {
    
    
    private final String usuarioEmail = "sistematccunipampa@gmail.com";
    private final String emailRemetente = "sistematccunipampa@gmail.com";
    private final String senhaEmail = "Tcc1234567";
    
    /**
     * Envia um email simples para algum destinatário
     * @param mensagemASerEnviada A mensgem que vai ser enviada para o destinatário
     * @param assuntoDoEmail O assunto do email em questão
     * @param emailDestinatario O email do destinatário
     */
    public void enviaEmailSimples(String mensagemASerEnviada, String assuntoDoEmail, String emailDestinatario){
        SimpleEmail email = new SimpleEmail();  
  
        try {  
        email.setDebug(true);  
        email.setHostName("smtp.gmail.com");                  //servidor smtp do gmail
        email.setAuthentication(usuarioEmail, senhaEmail);    //usuario_email (ex: gean_pereira8@hotmail... o usuário é: gean_pereira8) e senha
        email.setSSLOnConnect(true);                             
        email.addTo(emailDestinatario); //email destinatário (pode ser qualquer email)  
        email.setFrom(emailRemetente); //email remetente (necessita ser o email que voce fara a autenticacao)  
        email.setSubject(assuntoDoEmail);                //assunto do email
        email.setMsg(mensagemASerEnviada);        //mensagem a ser enviada
        email.send();                             //envia o email
  
        } catch (EmailException e) {  
  
        System.out.println(e.getMessage());  
  
        } 
    }
    
    public void enviaEmailComAnexo(String mensagemASerEnviada, String caminhoDoArquivo, String assuntoDoEmail, String usuarioEmail, String senhaEmail, String emailRemetente, String emailDestinatario){
        File f = new File(caminhoDoArquivo);   
                    
          EmailAttachment attachment = new EmailAttachment();
          attachment.setPath(f.getPath());                         // Obtem o caminho do arquivo  
          attachment.setDisposition(EmailAttachment.ATTACHMENT);  
          attachment.setDescription("Submissão de TCC");  
          attachment.setName(f.getName());                         // Obtem o nome do arquivo  
  
          try {  
            // Praticamente a mesma lógica do email simples
            MultiPartEmail email = new MultiPartEmail();  
            email.setDebug(true);  
            email.setHostName("smtp.gmail.com");  
            email.setAuthentication(usuarioEmail, senhaEmail);  
            email.setSSLOnConnect(true);
            email.addTo(emailDestinatario); 
            email.setFrom(emailRemetente);  
            email.setSubject(assuntoDoEmail);  
            email.setMsg(mensagemASerEnviada);  
  
            // add o anexo
            email.attach(attachment);  
  
            email.send();  
        } catch (EmailException e) {  
            e.printStackTrace();  
        }  
    }
}
