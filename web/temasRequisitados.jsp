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
        %>
        <h1>Temas cadastrados</h1>
        <form name="formConfirmar" action="DetalheTemaServlet" method="POST">
            <ul type="disc">
                <%
                    for (int i = 0; i < temasPendentes.size(); i++) {
                        Tema tema = temasPendentes.get(i);
                        valorBotao = "" + (i + 1);
                %>

                <li> <%= tema.getAluno().getNome()%> </li>
                <%= tema.getDescricao()%>
                <br>
                <input type="submit" name="confirmar" value="<%= "confirmar tema " + (i + 1)%>" />
                <%
                    }
                %>
                <br>
            </ul>
        </form>
    </body>
</html>
