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
    </head>
    <body>
        <div id="title" ><h1>UNIPAMPA.EDU.BR</h1></div>
        <form action="teste.do" method="post" name="form">
            <div id="area">
                <form id="formulario" autocomplete="off">
                    <fieldset>
                        <legend>Gerenciador de TCC</legend>
                        <label> Nome:</label><input class="campo_nome" type="text"><br>
                        <label>Senha:</label><input class="campo_senha" type="password"><br>
                        <input class="btn_submit" type="submit" value="Enviar">
                        <a href="http://www.google.com">Esqueceu sua senha?
                    </fieldset>
                </form>
            </div>

        </form>
    </body>
</html>
