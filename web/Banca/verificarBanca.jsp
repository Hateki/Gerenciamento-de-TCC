<%@page import="br.edu.unipampa.model.Tecnicoadministrativo"%>
<%@page import="br.edu.unipampa.model.Pessoaexterna"%>
<%@page import="br.edu.unipampa.model.Professor"%>
<%@page import="br.edu.unipampa.model.Coordenador"%>
<%@page import="br.edu.unipampa.model.Orientador"%>
<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : verificarBanca
    Created on : 20/07/2014, 14:35:57
    Author     : Pedro Henrique França Silva
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

        <title>Verificar Banca</title>

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
                    Professor professor = acesso.procurarProfessor(usuario);
                    Pessoaexterna pe = acesso.procurarPessoaExtera(usuario);
                    Tecnicoadministrativo ta = acesso.procurarTecnicoAdministrativo(usuario);

                    pageContext.setAttribute("orientador", orientador);
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
                    <a href="menuPrincipalCoordenadorTCCs.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
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

                <% if (orientador != null) { %>

                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/FiltrarTCCsDoAluno"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário, Local e Data para Bancas </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> Verificar Bancas </a> </li>
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

                <% } else if (ta != null || pe != null || professor != null) { %>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> Verificar Bancas </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
                <% } %>
            </div>
        </div>
        <br><br><br><br>

        <h3> Aqui você pode visualizar as Bancas em que participa!</h3>

        <br><br><br>

        <c:if test="${not empty retornoAta}" var="v" scope="request">
            <div class="alert alert-danger" role="alert"><c:out value="${retornoAta}"/></div>
        </c:if>
        <c:if test="${not empty retornoParecer}" var="v" scope="request">
            <div class="alert alert-danger" role="alert"><c:out value="${retornoParecer}"/></div>
        </c:if>    

        <% int cont = 1;%>

        <c:forEach var="bancaEncontrada" items="${Bancas}">
            <div class="panel panel-primary">
                <div class="panel-heading"><h5>Banca <%= cont%></h5></div>
                <div class="panel-body">
                    <div class="row row-fluid">
                        <c:if test="${not empty bancaEncontrada}" var="v" scope="request"> 

                            <div class="col-md-2"> <strong> Orientador: </strong><c:out value="${bancaEncontrada.orientadorByOrientadorIdOrientador.nome}"/></div>
                            <div class="col-md-3"><strong> Aluno: </strong><c:out value="${bancaEncontrada.aluno.nome}"/></div>
                            <div class="col-md-3"><strong> Convidado: </strong><c:out value="${bancaEncontrada.pessoaByConvidado1IdPessoa.nome}"/></div>
                            <div class="col-md-3"><strong> Convidado: </strong><c:out value="${bancaEncontrada.pessoaByConvidado2IdPessoa.nome}"/></div>
                            <br><br>
                            <c:if test="${not empty bancaEncontrada.pessoaByConvidado3IdPessoa}">
                                <div class="col-md-3"><strong> Convidado: </strong><c:out value="${bancaEncontrada.pessoaByConvidado3IdPessoa.nome}"/></div>
                            </c:if>
                            <c:if test="${not empty bancaEncontrada.local}">
                                <div class="col-md-3"><strong> Local: </strong><c:out value="${bancaEncontrada.local}"/></div>
                            </c:if>
                            <c:if test="${not empty bancaEncontrada.horario}">
                                <div class="col-md-3"><strong> Horario: </strong><c:out value="${bancaEncontrada.horario}"/></div>
                            </c:if>
                            <c:if test="${not empty bancaEncontrada.data}">
                                <div class="col-md-3"><strong> Data: </strong><c:out value="${bancaEncontrada.data}"/></div>
                            </c:if>    
                            <br><br>
                            <div class="col-md-2">
                                <form name="download" action="DetalheTCCServlet">
                                    <button type="submit" class="bnt btn-success" name="botao" value="<%= cont%>">
                                        Fazer dowload do TCC 
                                    </button>
                                </form>    
                            </div>    
                            <div class="col-md-2">
                                <form name="formularioAvaliacao" action="FormularioAvaliacaoServlet">
                                    <button type="submit" class="bnt btn-primary" name="botaoAvaliacao" value="<%= cont%>">
                                        Avaliar Aluno
                                    </button>
                                </form>  
                            </div>             
                            <div class="col-md-2">
                                <form name="ataDefesa" action="AtaDefesaServlet">
                                    <button type="submit" class="bnt btn-primary" name="botaoAvaliacao" value="<%= cont%>">
                                        Ata de defesa
                                    </button>
                                </form>         
                            </div>
                            <div class="col-md-2">
                                <form name="parecerFinal" action="ParecerFinalBanca">
                                    <button type="submit" class="bnt btn-primary" name="botaoAvaliacao" value="<%= cont%>">
                                        Parecer Final da Banca
                                    </button>
                                </form>         
                            </div>            
                            <br><br><br><br><br>
                            <c:if test="${not empty retorno || retorno == ''}" var="v" scope="request">
                                <div class="alert alert-danger" role="alert"><c:out value="${retorno}"/></div>
                            </c:if>

                            <!-- 
                            <div class="col-md-3">
                                <form name="download" action="DownloadTCCServlet">
                                    <button type="submit" class="bnt btn-success">
                                        Fazer Download Arquivo 
                                    </button>
                                </form>
                            </div>
                            
                            -->
                        </c:if>
                    </div>
                </div>
            </div>
            <br><br>
            <% cont++;%>
        </c:forEach>

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
