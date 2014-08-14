<%-- 
    Document   : exibirSituacao
    Created on : 06/08/2014, 06:39:37
    Author     : pontofrio
--%>

<%@page import="br.edu.unipampa.model.Tcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <title>Situação Tema</title>

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
                <a href="menuPrincipalAluno.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Tema <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CadastroTemaServlet"> Cadastrar Tema </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/SubmeterTCCServlet"> SubmeterTCC </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/ExibirSituacaoServlet"> Exibir Situacao Tema </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        
        <br><br><br><br>
        
        <h1> <center> Situação do tema</center>  </h1>

        <c:if test="${not empty tema}" var="v" scope="request">
            <div class="panel panel-primary">
                <div class="panel-heading"><h5>Tema</h5></div>
                <div class="panel-body">
                    <label> Aluno:</label> <c:out value="${tema.aluno.nome}"/><br>
                    <label> Orientador:</label> <c:out value="${tema.orientador.nome}"/><br>
                    <label> Descrição:</label> <c:out value="${tema.descricao}"/><br>
                    <c:if test="${tema.aprovado == 0}" var="v" scope="request">
                        <label> Situação:</label> Não aprovado<br>
                    </c:if>
                    <c:if test="${tema.aprovado == 1}" var="v" scope="request">
                        <label> Situação:</label> Aprovado pelo Orientador<br>
                    </c:if>
                    <c:if test="${tema.aprovado == 2}" var="v" scope="request">
                        <label> Situação:</label> Aprovado pelo Coordenador<br>
                    </c:if>
                </div>
            </div>
            <c:forEach var="tcc" items="${tccs}">
                <c:if test="${not empty tcc}" var="v" scope="request">
                    <div class="panel panel-info">
                        <div class="panel-heading"><h3>Situação TCC</h3></div>
                        <div class="panel-body">
                            <label>Nome do arquivo:</label> <c:out value="${tcc.titulo}"/><br>
                            <label>Situação</label>
                            <c:if test="${tcc.status == 1}" var="v" scope="request">
                                Aprovado<br><br>
                            </c:if>
                            <c:if test="${tcc.status == 0}" var="v" scope="request">
                                Não Aprovado<br><br>
                            </c:if>
                            <% Tcc tcc = (Tcc) request.getAttribute("tcc1"); %>
                            <% request.getSession().setAttribute("tcc", tcc);%>

                            <form name="download" action="DownloadTCCServlet">
                                <button type="submit" class="bnt btn-success">
                                    Fazer Download Arquivo 
                                </button>
                            </form>

                        </div>
                    </div>  
                </c:if> 
            </c:forEach>               

        </c:if>

        <c:if test="${empty tema}" var="v" scope="request">
            <div class="alert alert-danger" role="alert">Você não têm tema cadastrado</div>
        </c:if>

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>