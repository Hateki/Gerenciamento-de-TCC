<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : AvaliadoresDiponiveis
    Created on : 20/08/2014, 18:53:07
    Author     : Pedro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="br.edu.unipampa.model.Tema"%>
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
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarBancaTCCServlet"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário Local e Data </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> VerificarBanca </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/TemasRequisitadosServlet"> Temas Requisitados  </a></li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/cadastroPessoaExterna.jsp"> Cadastrar Pessoa Externa </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/telaLogin.jsp"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br><br>
    <center><h1> Avaliações dos membros da Banca </h1></center>
    <br><br>
    <form method="post" action="AvaliacaoCoodenadorServlet" >
        <% int cont = 0;%>
        <table border="1" class="table table-hover">
            <thead>
                <tr>
                    <th>Nome Avaliador</th>
                    <th> Nota </th>
                    <th> Avaliar </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="avaliador" items="${avaliadores}">
                    <% pageContext.setAttribute("cont", cont);%>
                    <tr>
                        <td><c:out value="${avaliador.nome}"/></td>

                        <c:if test="${cont == 0}" var="v" scope="request">
                            <c:if test="${tcc.notaOrientador != -1}" var="v" scope="request">
                                <td> <c:out value="${tcc.notaOrientador}" /> </td>
                            </c:if>
                            <c:if test="${tcc.notaOrientador == -1}" var="v" scope="request">
                                <td> Não Avaliado </td>
                            </c:if>    
                        </c:if>

                        <c:if test="${cont == 1}" var="v" scope="request">
                            <c:if test="${tcc.notaConvidado1 != -1}" var="v" scope="request">
                                <td> <c:out value="${tcc.notaConvidado1}" /> </td>
                            </c:if>
                            <c:if test="${tcc.notaConvidado1 == -1}" var="v" scope="request">
                                <td> Não Avaliado </td>
                            </c:if>    
                        </c:if>

                        <c:if test="${cont == 2}" var="v" scope="request">
                            <c:if test="${tcc.notaConvidado2 != -1}" var="v" scope="request">
                                <td> <c:out value="${tcc.notaConvidado2}" /> </td>
                            </c:if>
                            <c:if test="${tcc.notaConvidado2 == -1}" var="v" scope="request">
                                <td> Não Avaliado </td>
                            </c:if>    
                        </c:if>

                        <c:if test="${cont == 3}" var="v" scope="request">
                            <c:if test="${tcc.notaCoorientador != -1}" var="v" scope="request">
                                <td> <c:out value="${tcc.notaCoorientador}" /> </td>
                            </c:if>
                            <c:if test="${tcc.notaCoorientador == -1}" var="v" scope="request">
                                <td> Não Avaliado </td>
                            </c:if>    
                        </c:if>

                        <td> 
                            <button type="submit" class="bnt btn-success"
                                    name="avaliadorEscolhido"
                                    value="<c:out value="${cont}"/>"> 
                                Avaliar Aluno
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
