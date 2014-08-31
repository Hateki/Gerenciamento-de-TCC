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
    Author     : Pedro
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

                <%
                    String usuario = (String) request.getSession().getAttribute("usuario");
                    AcessoSistema acesso = new AcessoSistema();

                    Orientador orientador = acesso.procurarOrientador(usuario);
                    Coordenador coordenador = acesso.procurarCoordenador(usuario);
                    Professor professor = acesso.procurarProfessor(usuario);
                    Pessoaexterna pe = acesso.procurarPessoaExtera(usuario);
                    Tecnicoadministrativo ta = acesso.procurarTecnicoAdministrativo(usuario);

                    pageContext.setAttribute("orientador", orientador);
                    pageContext.setAttribute("coordenador", coordenador);
                    pageContext.setAttribute("professor", professor);
                    pageContext.setAttribute("pe", pe);
                    pageContext.setAttribute("ta", ta);
                %>

                <% if (orientador != null) { %>
                <a href="menuPrincipalOrientador.jsp" class="navbar-brand"> Gerenciamento de TCC </a>

                <% } else if (professor != null) { %>
                <a href="menuPrincipalProfessor.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                <% } %>

                <c:if test="${not empty coordenador}" var="v" scope="request">
                    <a href="menuPrincipalCoordenador.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                </c:if>

                <c:if test="${not empty pe}" var="v" scope="request">
                    <a href="menuPrincipalOutros.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                </c:if>

                <c:if test="${not empty ta}" var="v" scope="request">
                    <a href="menuPrincipalOutros.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                </c:if>

                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">

                                <% if (orientador != null || coordenador != null) { %>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarBancaTCCServlet"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário, Local e Data para Bancas </a> </li>
                                    <% } %>

                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> Verificar Bancas </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>

                        <% if (orientador != null || professor != null) { %>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/TemasRequisitadosServlet"> Temas Requisitados  </a></li>
                            <% } %>

                        <% if (orientador != null || coordenador != null) { %>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/cadastroPessoaExterna.jsp"> Cadastrar Pessoa Externa </a> </li>
                            <% }%>

                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br>

    <center><h3> Relação de Notas </h3></center>
    <br><br>
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
