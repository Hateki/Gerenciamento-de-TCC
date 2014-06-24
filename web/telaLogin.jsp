<%-- 
    Document   : telaLogin
    Created on : 03/06/2014, 06:14:33
    Author     : pontofrio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <style> 
            #area {margin-left:550px; margin-right:550px}; 

        </style>
        
        <style> 
            #imagem {margin-left:500px; margin-right:550px}; 

        </style>
        
         <style> 
            #botao {margin-left:70px; margin-right:550px}; 

        </style>
        
        <link href="bootstrap.min.css" rel="stylesheet" media="screen">
        <script type="text/javascript">
            function validaCampoUsuario()
            {
                if (document.telaLogin.nomeUsuario == "") {
                    alert("O Campo obrigatório Usuário não foi preenchido");
                    return false
                }
                else
                    return true;
            }

            function validaCampoSenha()
            {
                if (document.telaLogin.senhaUsuario == "") {
                    alert("O Campo obrigatório Usuário não foi preenchido");
                    return false
                }
                else
                    return true;
            }


            //trim completo
            function trim(str) {
                return str.replace(/^\s+|\s+$/g, "");
            }

            function validaEspaco(input) {
                texto = input.value;
                textoNovo = trim(texto);
                if (textoNovo === "") {
                    input.value = textoNovo;
                    alert("Campo " + input.name + " invalido");
                }
            }


        </script>

        <title>UNIPAMPA</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div id="imagem">
        <img src="imagem_unipampa.jpg" >
        </div>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="bootstrap.min.js"></script>

        <div id="titulo" ><br></br><br></br></div>

        <form action="LoginServlet" method="post" name="form">
            <div id="area">
                <form id="telaLogin" name="telaLogin" method="post" action="LoginServlet" onsubmit="
                return validaCampoUsuario(), validaCampoSenha();">
                    <fieldset>
                        <br></br>

                        <legend><strong>Gerenciador de TCC</strong></legend>
                        <label> Usuário: </label><input name="nomeUsuario" type="text" id="nomeUsuario" maxlength="200" onblur="validaEspaco(this)" placeholder="Digite E-mail" required ><br>                   


                        <label> Senha: </label><input name="Senha"  type="password" id="senhaUsuario" maxlength="200" onblur="validaEspaco(this)" placeholder="Digite sua senha" required ><br>
                        <br></br>
                        <div id="botao">
                        <input type="submit" class="btn btn-primary" name="enviar" id="enviar" value="Entrar"   />
                        </div>

                    </fieldset>
                    <%
                        String fracasso = (String) request.getAttribute("fracasso");
                        if(fracasso != null)
                        {
                    %>
                        <script> alert("Usuário ou senha incorretos");</script>
                    <%
                        }
                    %>
                </form>
            </div>

        </form>
    </body>
</html>
