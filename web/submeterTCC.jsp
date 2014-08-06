<%-- 
    Document   : envioTCC
    Created on : 21/07/2014, 15:18:37
    Author     : Kezia
--%>

<%@page import="java.io.RandomAccessFile"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gerenciamento TCC</title>

    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <a href="menuPrincipalAluno.jsp" class="navbar-brand"> Menu Principal </a>
                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">

                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/verificarTemaAluno.html"> Verificar Situação de Tema </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/CadastroTemaServlet"> Cadastrar Tema </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/telaLogin.jsp"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br><br>

        <h1>Envio do TCC</h1> 
        <form action="upload" method="post" name="form" enctype="multipart/form-data">
            Envie um Arquivo: <input type="file" name="arquivo" /><br />
            <input name="submitbutton" value="Enviar" type="submit" id="id_submitbutton" /> 
            <input name="cancel" value="Cancelar" type="submit" onclick="skipClientValidation = true;
                       return true;" class=" btn-cancel" id="id_cancel" />
        </form>  
             <% 	    
	 String savePath = "Arquivos Salvos";   // o diretório dos arquivos salvos    
	 String filename = "";        
	 ServletInputStream in = request.getInputStream(); //uma referencia do objeto da solicitaçao http onde tem o conteudo do arquivo carregado     
	 //o inicio do arquivo carregado  e separado pelo limite e uma sequencia de caracteres 
	 //de carros de retorno-alimentador de linha ledo o HttpServeltRequest linha a linha 
	 byte[] line = new byte[128];  // define um array de byte chamado line 
	 int i = in.readLine(line, 0, 128);  //usar o metodo readLine de ServeltInputStream para ler a 1a linha do conteudo do objeto http 
	 int boundaryLength = i - 2; //o comprimeto atual da linha -2 do que o num de bytes retornado do metodo readLine 
	 String boundary = new String(line, 0, boundaryLength);   //Descarta os 2 ultimos caracteres da linha  
	 //tendo recuperado o limite, entao pode iniciar a extraçao do elemento de valor de formulario, lendo o conteudo do objeto 
	 // http linha por linha, usando a loop while, ate q ela atinja o final, qdo o medoto readLine retorna -1 
	  while (i != -1) {   
	    String newLine = new String(line, 0, i);    
	    if (newLine.startsWith("Content-Disposition: form-data; name=\"")) {   
	      String s = new String(line, 0, i-2);//agora pode conseguir o nome de arquivo a partir da string de leitura 
	      int pos = s.indexOf("filename=\"");    
	      if (pos != -1) {   
	        String filepath = s.substring(pos+10, s.length()-1);    
	        // navegadores do Windows incluem o caminho completo do cliente, 
	        // mas Unix / Linux e navegadores de Mac só enviar o arquivo de teste 
	        // se este é de um navegador para Windows 
	        pos = filepath.lastIndexOf("\\");    
	        if (pos != -1)    
	          filename = filepath.substring(pos + 1);    
	        else    
	          filename = filepath;    
	      }    
	     
	      // conteúdo do arquivo 
	      i = in.readLine(line, 0, 128);  //depois de conseguir o nome de arquivo, notara os dois pares de caracteres 
	      i = in.readLine(line, 0, 128); //carro de retorno-alimentador de linha antes do inico do conteudo do arquivo carregado 
	      // blank line                  //pois isto chama o metodo readLine duas vezes 
	      i = in.readLine(line, 0, 128);    
	     
	      ByteArrayOutputStream buffer = new  //depois inicia o conteudo atual do arquivo, que e armazenado em um ByteArrayOutputStream 
	      ByteArrayOutputStream(); 
	      newLine = new String(line, 0, i); // que continua lendo a linha ate encontrar um outro limite 
	     
	      while (i != -1 && !newLine.startsWith(boundary)) {   
	       // o problema é a última linha do conteúdo do arquivo contém o caractere nova linha. 
	       // Então, nós precisamos verificar se a linha atual é a última linha 
	        buffer.write(line, 0, i);    
	        i = in.readLine(line, 0, 128);    
	        newLine = new String(line, 0, i);    
	      }    
	      try {   
	        // salvar o arquivo carregado               //o limite sinaliza o final do arquivo carregado 
	        RandomAccessFile f = new RandomAccessFile(  //sendo sua etapa e salvar o buffer em um arquivo 
	          savePath + filename, "rw");    
	        byte[] bytes = buffer.toByteArray();    
	        f.write(bytes, 0, bytes.length - 2);    
	        f.close();    
	      }    
	      catch (Exception e) {}    
	    }    
	    i = in.readLine(line, 0, 128);    
	     
	  } // fim do while
	%>

    </body>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
