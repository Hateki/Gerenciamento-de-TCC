<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : listaOrientadores
    Created on : 02/09/2014, 21:42:03
    Author     : Pedro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Temas requisitados</title>

    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <a href="menuPrincipalCoordenadorTCCs.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/ConfirmarTemaServlet">Lista de Temas</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/relacaoNotas.jsp">Relação de Notas</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/DatasPrazosServlet">Definir Prazos</a> </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/SelecionarOrientadorServlet"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário, Local e Data para Bancas</a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaCoordenadorServlet"> Verificar Bancas</a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/cadastroPessoaExterna.jsp"> Cadastrar Pessoa Externa </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br><br>
    <center><h1> Orientadores Disponíveis </h1></center>
    <br><br>
    <form method="post" action="FiltrarTCCsDoAluno" >
        <% int cont = 0;%>
        <table border="1" class="table table-hover">
            <thead>
                <tr>
                    <th>Nome Orientador</th>
                    <th> Avaliar </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="orientador" items="${listaOrientadores}">
                    <% pageContext.setAttribute("cont", cont);%>
                    <tr>
                        <td><c:out value="${orientador.nome}"/></td>

                        <td> 
                            <button type="submit" class="bnt btn-success"
                                    name="orientador"
                                    value="<c:out value="${orientador.usuario}"/>"> 
                                Escolher Orientador
                            </button>
                        </td>         
                    </tr>
                    <% cont++;%>
                </c:forEach>
            </tbody>
        </table>
    </form>

    <!-- Bootstrap core JavaScript
================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>