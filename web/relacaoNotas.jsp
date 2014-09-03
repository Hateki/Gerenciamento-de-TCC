<%@page import="java.util.List"%>
<%@page import="br.edu.unipampa.model.Tecnicoadministrativo"%>
<%@page import="br.edu.unipampa.model.Pessoaexterna"%>
<%@page import="br.edu.unipampa.model.Professor"%>
<%@page import="br.edu.unipampa.model.Coordenador"%>
<%@page import="br.edu.unipampa.model.Orientador"%>
<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : relacaoNotas
    Created on : 30/08/2014, 17:47:28
    Author     : Tiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">

        <title>Relação de notas</title>

        <!-- Bootstrap core CSS -->
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
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
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/RelacaoNotasServlet">Relação de Notas</a> </li>
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

    <center><h3> Relação de Notas </h3></center><hr>
    <br>
    <table border="1" class="table table-hover">
        <thead>
            <tr>
                <th>Nome Aluno</th>
                <th> Orientador </th>
                <th> Descrição Tema </th>
                <th> Nota TCC 1 </th>
                <th> Nota TCC 2 </th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${not empty listaTemas}" var="v" scope="request">
                <c:forEach var="listaTema" items="${listaTemas}">
                    <tr>
                        <td> <c:out value="${listaTema[0].aluno.nome}" /> </td>
                        <td> <c:out value="${listaTema[0].orientador.nome}" /> </td>
                        <td> <c:out value="${listaTema[0].descricao}" /> </td>
                        <td> <c:out value="${listaTema[1]}" /> </td>
                        <c:if test="${not empty listaTema[2]}" var="v" scope="request">
                            <td> <c:out value="${listaTema[2]}" /> </td>
                        </c:if>
                        <c:if test="${empty listaTema[2]}" var="v" scope="request">
                            <td> Não avalidao </td>
                        </c:if>    
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
