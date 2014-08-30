<%-- 
    Document   : criarTemaTCC
    Created on : 22/06/2014, 19:37:04
    Author     : Miguel Zinelli
--%>

<%@page import="br.edu.unipampa.controller.CriarBancaTCCServlet"%>
<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style> 
            #area {margin-left:550px; margin-right:600px}; 

        </style>
        <link href="bootstrap.min.css" rel="stylesheet" media="screen">
        <title>Criar Banca do TCC</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <body>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="bootstrap.min.js"></script>

        <form id="tccForm" name="tccForm" method="post" action="CriarBancaTCCServlet"
              onsubmit="return">

            <label for="titulo"></label>
            <div id="area">

                <h1>Informações TCC</h1>
                <br>
                <h4>Definir data, local e horário</h4>

                Data: <input name="date" type="date" id="date" required/><br></br>
                Horario: <input type="time" name="time" id="time" required/><br></br>
                Local: <input type="text" name="local" id="local" onblur="validaEspaco(this)" required/><br></br>

                <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="retornaPaginaPrincipal()">
                <input type="submit" class="btn btn-info" name="enviar" id="enviar" value="Enviar"   />
            </div>
            <script>
                function retornaPaginaPrincipal() {
                    location.href = "menuPrincipalProfessor.html"
                }
            </script>


        </form>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script>
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
                }
        </script>
    </body>
</html>

