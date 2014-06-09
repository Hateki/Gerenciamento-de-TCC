<%-- 
    Document   : telaLogin
    Created on : 03/06/2014, 06:14:33
    Author     : pontofrio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Teste Página Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
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
                    alert("O Campo obrigatório Senha não foi preenchido");
                    return false
                }
                else
                    return true;
            }


<!-- Fim do JavaScript que validará os campos obrigatórios! -->
        </script>   
        
        
        
        
    </head>
    <body>
        <div id="title" ><h1>UNIPAMPA.EDU.BR</h1></div>
       <form action="LoginServlet" method="post" name="form"
            <div id="area">
                  <form id="telaLogin" name="telaLogin" method="post" action="LoginServlet" onsubmit=" return validaCampoUsuario(), validaCampoSenha();">
                    <fieldset>
                        <legend>Gerenciador de TCC</legend>
                        
                        <label> Nome: </label><input name="nomeUsuario" type="text" id="nomeUsuario" maxlength="200"><br>                   

                        <label> Senha: </label><input name="Senha"  type="password" id="senhaUsuario" maxlength="200"><br>
                        
                        <input class="btn_submit" type="submit" value="Enviar">
                        <a href="http://www.google.com">  Esqueceu sua senha?
                    </fieldset>
                </form>
            </div>

        </form>
    </body>
</html>
