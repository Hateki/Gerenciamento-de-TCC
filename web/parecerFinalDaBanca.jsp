<%-- 
    Document   : parecerFinalDaBanca
    Created on : 30/07/2014, 19:59:56
    Author     : Gean
--%>

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

        <title>Parecer Final da Banca</title>

        <!-- Bootstrap core CSS -->
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">

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
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarTemaTCCServlet"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário Local e Data </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> VerificarBanca </a> </li>
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

        <h1 style="color: #3c763d"><strong><center>Universidade Federal do Pampa 
                    <br>Campus Alegrete
                    <br>Curso de Engenharia de Software</center>
            </strong>
        </h1>
        <h2><center><br><b>PARECER FINAL DA BANCA</b></center></h2>

        <div>
            <br><br>
            <label class="text text-uppercase" style="font-size: x-large">Aluno:</label> <label> <c:out value="${bancaEscolhida.aluno.nome}"/>         </label><br>
            <label class="text text-uppercase" style="font-size: x-large" >Matrícula:</label> <c:out value="${bancaEscolhida.aluno.matricula}"/>
            <label class="text text-uppercase" style="font-size: x-large">Título do Trabalho:</label> <c:out value="${tema.descricao}"/><br>
        </div>

        <form method="post">
            Banca Examinadora: <c:out value="${bancaEscolhida.orientadorByOrientadorIdOrientador.nome}"/><br>

            <c:out value="${bancaEscolhida.pessoaByConvidado1IdPessoa.nome}"/><br>
            <c:out value="${bancaEscolhida.pessoaByConvidado2IdPessoa.nome}"/><br>
            <c:out value="${bancaEscolhida.pessoaByConvidado3IdPessoa.nome}"/><br>
            <%
                String situacaoAluno;
                Float mediaFinal = (Float) request.getAttribute("mediaFinal");
                if (mediaFinal != null && mediaFinal > 6) {
                    situacaoAluno = "Aprovado";
                } else {
                    situacaoAluno = "Reprovado";
                }
            %>
           
            <c:if test="${mediaFinal > 0}" var="v" scope="request">
                <c:out value="${mediaFinal}" /><br>
            </c:if>
            <c:if test="${mediaFinal < 0}" var="v" scope="request">
                Nem todos os avaliadores deram a nota<br>
            </c:if>
            <label class="text text-uppercase">Situação Aluno: <%= situacaoAluno%> </label><br><br><br>

            <label>_______________________________________</label><br>
            <label><c:out value="${bancaEscolhida.orientadorByOrientadorIdOrientador.nome}" /></label><br>
            <label>_______________________________________</label><br>
            <label><c:out value="${bancaEscolhida.pessoaByConvidado1IdPessoa.nome}" /></label><br>
            <label>_______________________________________</label><br>
            <label><c:out value="${bancaEscolhida.pessoaByConvidado2IdPessoa.nome}" /></label><br>
        </form>

    </body>
</html>
