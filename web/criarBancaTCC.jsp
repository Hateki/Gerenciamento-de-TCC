<%-- 
    Document   : criarTemaTCC
    Created on : 22/06/2014, 19:37:04
    Author     : pontofrio
--%>

<%@page import="br.edu.unipampa.controller.CriarBancaTCCServlet"%>
<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Criar Banca do TCC</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <body>
        <h1>Criar banca do TCC</h1>
        <form id="tccForm" name="tccForm" method="post" action="CriarTemaTCCServlet"
              onsubmit="return">

            <label for="titulo"></label>
            <h2>Banca Avaliadora</h2>

            Data: <input name="date" type="date" id="date" required/><br></br>
            Horario: <input type="time" name="time" id="time" required/><br></br>
            Local: <input type="text" name="local" id="local" onblur="validaEspaco(this)" required/><br></br>
            Matrícula Aluno: <input type="text" name="matricula" id="matricula" maxlength="9" onblur="validaEspaco(this), testarMatricula()" required/><br></br>
            Professor: <input type="text" name="professor1" id="professor1" onblur="validaEspaco(this)" required/><br></br>
            Professor: <input type="text" name="professor2" id="professor2" onblur="validaEspaco(this)" required/><br></br>


            <div style="display: none" id="professor3">Professor : <input type="text" name="professor3" id="professor3" onblur="validaEspaco(this)"/></div>        

            <button type="button" onclick="mostraProfessorInv()" name="btAddProfessor" id="btAddProfessor" >Adicionar</button><br></br>
            <input type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="history.go(-1)">

            <input type="submit" name="enviar" id="enviar" value="Enviar"   />
        </form>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script>
                function mostraProfessorInv() {
                    $('#professor3').slideDown();
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

                function testarMatricula() {

                    if ((isNaN(tccForm.matricula.value)) || (tccForm.matricula.value == "")) {

                        alert("Digite apenas números!");

                    }

                }
        </script>
        
        <%
            Integer retorno = (Integer) request.getAttribute("retorno");
            
            if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_EXISTEM)
            {    
        %>
                <script> alert("O cadastro foi realizado com sucesso"); </script>
        <%
            }
            
            else if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_1)
            {    
        %>
                <script> alert("O professor digitado no campo 1 não existe"); </script>
        <% 
            }    
            
            else if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_2)
            {    
        %>
                <script> alert("O professor digitado no campo 2 não existe"); </script>
        <% 
            } 
            else if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_3)
            {    
        %>
                <script> alert("O professor digitado no campo 3 não existe"); </script>
        <% 
            } 
            else if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_1_2)
            {    
        %>
                <script> alert("O professor digitado no campo 1 e 2 não existem"); </script>
        <% 
            } 
            else if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_1_3)
            {    
        %>
                <script> alert("O professor digitado no campo 1 e 3 não existem"); </script>
        <% 
            } 
            else if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_2_3)
            {    
        %>
                <script> alert("O professor digitado no campo 2 e 3 não existem"); </script>
        <% 
            } 
            else if(retorno != null && retorno == CriarBancaTCCServlet.PROFESSORES_1_2_3)
            {    
        %>
                <script> alert("O professor digitado no campo 1 e 2 e 3 não existem"); </script>
        <% 
            } 
        %>
    </body>
</html>

