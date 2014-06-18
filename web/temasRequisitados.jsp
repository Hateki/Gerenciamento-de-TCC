<%-- 
    Document   : temasRequisitados
    Created on : 15/06/2014, 13:02:33
    Author     : pontofrio
--%>

<%@page import="br.edu.unipampa.model.Tema"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Temas requisitados</title>

    </head>
    <body>
        <%
            String valorBotao = null;
            List<Tema> temasPendentes = (List<Tema>) request.getAttribute("retorno");
            String aprovado;
        %>
        <h1>Temas cadastrados</h1>
        <form name="formConfirmar" action="DetalheTemaServlet" method="POST">
            <ul type="disc">
                <%
                    for (int i = 0; i < temasPendentes.size(); i++) {
                        Tema tema = temasPendentes.get(i);
                        valorBotao = "" + (i + 1);
                %>

                <li> <%= "ALUNO QUE REQUISITOU: " + tema.getAluno().getNome()%> </li>
                    <%= "DESCRIÇÃO DO TEMA: " + tema.getDescricao()%>
                <br>
                

                <%
                    if (tema.getAprovado()) {
                        aprovado = "Aprovado";
                    } else {
                        aprovado = "Não aprovado";
                    }
                %>

                <%= "SITUAÇÃO: " + aprovado%>

                <input type="submit" name="confirmar" value="<%= "Confirmar Tema " + (i + 1)%>" />
                <input type="submit" name="confirmar" value="<%= "Recusar Tema " + (i + 1)%> " />
                <br><br>
                <%
                    }
                %>
                <br>
            </ul>
        </form>
    </body>
</html>
