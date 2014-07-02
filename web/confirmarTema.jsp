<%-- 
    Document   : confirmarTema
    Created on : 30/06/2014, 19:28:59
    Author     : Pedro Henrique França Silva
--%>

<%@page import="java.util.List"%>
<%@page import="br.edu.unipampa.model.Tema"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style> 
            #tela {margin-left:550px; margin-right:350px}; 

        </style>
        <link href="bootstrap.min.css" rel="stylesheet" media="screen">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Temas requisitados</title>

    </head>
    <body>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="bootstrap.min.js"></script>

        <%
            String valorBotao = null;
            List<Tema> temasPendentes = (List<Tema>) request.getAttribute("retorno");
            String aprovado;
        %>
        <div id="tela">
            <h1>Temas cadastrados</h1>

            <form name="formConfirmar" action="ConfirmarTemaServlet" method="POST">
                <%
                    for (int i = 0; i < temasPendentes.size(); i++) {
                        Tema tema = temasPendentes.get(i);
                        valorBotao = "" + (i + 1);
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Nome Aluno</th>
                            <th>Descrição</th>
                            <th>Carga Horária</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><%=tema.getAluno().getNome()%></td>
                            <td><%=tema.getDescricao()%></td>
                            <td> <%=tema.getAluno().getCargaHoraria()%> </td>
                        </tr>
                    </tbody>
                </table>

                <br>
                <input type="submit" class="btn btn-info" name="confirmar" value="<%= "Confirmar Tema " + (i + 1)%>" />
                <input type="submit" name="confirmar" class="btn btn-warning" value="<%= "Recusar Tema " + (i + 1)%> " />
                <br><br>
                
                <%
                    }
                %>
                <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="retornaPaginaPrincipal()">
                <br>
                </div>
                <script>
                    function retornaPaginaPrincipal() {
                        location.href = "menuPrincipalProfessor.html"
                    }
                </script>
            </form>
    </body>
</html>

