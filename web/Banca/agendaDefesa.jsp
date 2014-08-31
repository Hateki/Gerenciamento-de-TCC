<%@page import="br.edu.unipampa.model.Tecnicoadministrativo"%>
<%@page import="br.edu.unipampa.model.Pessoaexterna"%>
<%@page import="br.edu.unipampa.model.Professor"%>
<%@page import="br.edu.unipampa.model.Coordenador"%>
<%@page import="br.edu.unipampa.model.Orientador"%>
<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
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
                <a href="menuPrincipalOutros.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
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

                <% if (coordenador != null) { %>    
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li> <a href="colocar link">Lista de Temas</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/DatasPrazosServlet">Definir Prazos</a> </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarBancaTCCServlet"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário, Local e Data para Bancas</a> </li>
                                <li> <a href="colocar link"> Avaliar Alunos </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> Verificar Bancas</a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/cadastroPessoaExterna.jsp"> Cadastrar Pessoa Externa </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>    
                <% } else { %>   

                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">

                                <% if (orientador != null) { %>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarBancaTCCServlet"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário, Local e Data para Bancas </a> </li>
                                    <% } %>

                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> Verificar Bancas </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>

                        <% if (orientador != null) { %>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/TemasRequisitadosServlet"> Temas Requisitados  </a></li>
                            <% } %>

                        <% if (orientador != null || coordenador != null) { %>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/cadastroPessoaExterna.jsp"> Cadastrar Pessoa Externa </a> </li>
                            <% } %>

                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
                <% } %> 
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
                <% int cont = 0;
                    List<String> titulos = (List<String>) request.getAttribute("titulos"); %>
                <c:forEach var="bancaEncontrada" items="${bancasEncontradas}">
                    <%
                        String titulo = "";
                        if (titulos.size() > 0) {
                            titulo = titulos.get(cont);

                    %>
                    <c:if test="${not empty bancaEncontrada.data}" var="v" scope="request">
                        <tr>
                            <td>1</td>
                            <td><%=titulo%></td>
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
                    <% cont++;%>
                </c:if>
                <% }%>
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
