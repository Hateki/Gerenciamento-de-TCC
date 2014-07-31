<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : agendaDefesa
    Created on : 30/07/2014, 17:54:08
    Author     : pontofrio
--%>

<%@page import="br.edu.unipampa.model.Tcc"%>
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

        <title>Agenda de Defesas</title>

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
                <a href="menuPrincipalProfessor.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarTemaTCCServlet"> Marcar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/definirHLD.jsp"> Definir Horário Local e Data </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/TemasRequisitadosServlet"> Temas Requisitados  </a></li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/ConfirmarTemaServlet"> Confirmar Tema  </a></li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/cadastroPessoaExterna.jsp"> Cadastrar Pessoa Externa </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/telaLogin.jsp"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br>

        <h3> Defesas Marcadas </h3>

        <table border="1" class="table table-hover">
            <thead>
                <tr>
                    <th>TCC</th>
                    <th>Titulo do trabalho</th>
                    <th>Aluno</th>
                    <th>Orientador</th>
                    <th>Coorientador</th>
                    <th>Primeiro Membro</th>
                    <th>Segundo Membro</th>
                    <th>Terceiro Membro</th>
                    <th>Data</th>
                    <th>Horario</th>
                    <th>Local</th>
                </tr>
            </thead>
            <tbody>
                <% int cont = 0;%>
                <% List<String> titulos = (List<String>) request.getAttribute("titulos"); %>
                <c:forEach var="bancaEncontrada" items="${bancasEncontradas}">
                    <% String titulo = titulos.get(cont) ;%>
                    <c:if test="${not empty bancaEncontrada.data}" var="v" scope="request">
                        <tr>
                            <td>1</td>
                            <td><%= titulo %></td>
                            <td><c:out value="${bancaEncontrada.aluno.nome}"/></td>
                            <td><c:out value="${bancaEncontrada.orientadorByOrientadorIdOrientador.nome}"/></td>

                            <c:if test="${not empty bancaEncontrada.orientadorByCoorientadorIdOrientador}" var="v" scope="request">
                                <td><c:out value="${bancaEncontrada.orientadorByCoorientadorIdOrientador.nome}"/></td> 
                            </c:if>

                            <c:if test="${empty bancaEncontrada.orientadorByCoorientadorIdOrientador}" var="v" scope="request">
                                <td>Não existe</td> 
                            </c:if>  

                            <td><c:out value="${bancaEncontrada.pessoaByConvidado1IdPessoa.nome}"/></td>
                            <td><c:out value="${bancaEncontrada.pessoaByConvidado2IdPessoa.nome}"/></td>

                            <c:if test="${not empty bancaEncontrada.pessoaByConvidado3IdPessoa}" var="v" scope="request">
                                <td><c:out value="${bancaEncontrada.pessoaByConvidado2IdPessoa.nome}"/></td> 
                            </c:if>

                            <c:if test="${empty bancaEncontrada.pessoaByConvidado3IdPessoa}" var="v" scope="request">
                                <td>Não existe</td> 
                            </c:if>   

                            <td><c:out value="${bancaEncontrada.data}"/></td>
                            <td><c:out value="${bancaEncontrada.horario}"/></td>
                            <td><c:out value="${bancaEncontrada.local}"/></td>   
                        </tr>
                    <br><br>
                    <% cont++; %>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
