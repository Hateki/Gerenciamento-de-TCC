<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : aprovarTcc
    Created on : 24/07/2014, 14:11:53
    Author     : pontofrio
--%>

<%@page import="java.util.List"%>
<%@page import="br.edu.unipampa.model.Tema"%>
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
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br><br>

        <% request.getSession().setAttribute("Tema", request.getAttribute("tema")); %>

        <h1><strong><center>Aprovação de Tcc's</center>
            </strong>
        </h1>

        <c:if test="${not empty tccEncontrados}" var="v" scope="request">
            <% int cont = 0;%>
            <c:forEach var="tcc" items="${tccEncontrados}">
                <div class="panel panel-primary">
                    <c:if test="${tcc.versaoTCC == 0}" var="v" scope="request">
                        <div class="panel-heading"><h4>Tcc Denfendido</h4></div>
                    </c:if>
                    <c:if test="${tcc.versaoTCC == 1}" var="v" scope="request">
                        <div class="panel-heading"><h4>Tcc Corrigido</h4></div>
                    </c:if>    
                    <div class="panel-body">
                        <table border="1" class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Nome Aluno</th>
                                    <th>Orientador</th>
                                    <th>Tema</th>
                                    <th>Situação</th>
                                    <th> Fazer Download </th>
                                        <c:if test="${tcc.status != 2}" var="dasda" scope="request">
                                        <th> Aprovação </th>
                                        </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><c:out value="${tema.aluno.nome}"/></td>
                                    <td><c:out value="${tema.orientador.nome}"/></td>
                                    <td><c:out value="${tema.descricao}"/></td>
                                    <c:if test="${tcc.status == 0}" var="v" scope="request">
                                        <td> Não Aceito </td>
                                    </c:if>

                                    <c:if test="${tcc.status == 1}" var="v" scope="request">
                                        <td>Aceito</td>
                                    </c:if>

                                    <c:if test="${tcc.status == 2}" var="v" scope="request">
                                        <td>Aprovado</td>
                                    </c:if>
                                    
                                    <c:if test="${tcc.status == 3}" var="v" scope="request">
                                        <td>Reprovado</td>
                                    </c:if>    
                                    <td>
                                        <form name="download" action="AprovarTccServlet">
                                            <button type="submit" class="bnt btn-primary" name="download" value="<%= cont%>">
                                                <c:out value="${tcc.titulo}"/>
                                            </button>
                                        </form>     
                                    </td>   
                                    <c:if test="${tcc.status != 2}" var="dasda" scope="request">
                                        <td> 
                                            <form name="aprovar" action="AprovarTccServlet">
                                                <button type="submit" class="bnt btn-success" name="botaoAprovar" value="<%= cont%>.1">
                                                    Aceitar Tcc
                                                </button>
                                                <button type="submit" class="bnt btn-danger" name="botaoAprovar" value="<%= cont%>.2">
                                                    Não Aceitar Tcc
                                                </button>
                                            </form> 
                                        </td>
                                    </c:if>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>                      
                <% cont++;%>
            </c:forEach>
        </c:if>

        <c:if test="${empty tccEncontrados}" var="v" scope="request">
            <div class="alert alert-danger" role="alert">Nenhum Tcc Enviado</div>
        </c:if>

        <!-- Bootstrap core JavaScript
================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>