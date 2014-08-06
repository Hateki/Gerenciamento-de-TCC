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

        <h1 style="color: #3c763d"><strong><center>Universidade Federal do Pampa 
                    <br>Campus Alegrete
                    <br>Curso de Engenharia de Software</center>
            </strong>
        </h1>
        <h2><center><br><b>PARECER FINAL DA BANCA</b></center></h2>

        <div>
            <br><br>
            <label class="text text-uppercase" style="font-size: x-large">Aluno:</label> <label> <c:out value="${bancaEscolhida.aluno.nome}"/></label><br>
            <label class="text text-uppercase" style="font-size: x-large" >Matrícula:</label> <c:out value="${bancaEscolhida.aluno.matricula}"/><br>
            <label class="text text-uppercase" style="font-size: x-large">Título do Trabalho:</label> <c:out value="${tema.descricao}"/><br>
        </div>

        <form method="post">
            <br>
            <label>Banca Examinadora:</label>
            <center> <c:out value="${bancaEscolhida.orientadorByOrientadorIdOrientador.nome}"/>(Orientador)</center><br>

            <center><c:out value="${bancaEscolhida.pessoaByConvidado1IdPessoa.nome}"/></center><br>
            <center><c:out value="${bancaEscolhida.pessoaByConvidado2IdPessoa.nome}"/></center><br>
            <center><c:out value="${bancaEscolhida.pessoaByConvidado3IdPessoa.nome}"/></center><br>
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
                <label>Nota final do Tcc: </label>
                <center><c:out value="${mediaFinal}" /></center><br>
            </c:if>
            <c:if test="${mediaFinal < 0}" var="v" scope="request">
            <center><label>Nem todos os avaliadores deram a nota</label></center><br>
            </c:if>
                <label>Situação Aluno:</label> 
                <center><%= situacaoAluno%></center><br><br><br>

            <center><label>_______________________________________</label></center><br>
            <center><label><c:out value="${bancaEscolhida.orientadorByOrientadorIdOrientador.nome}" /></center></label><br>
            <center><label>_______________________________________</label></center><br>
            <center><label><c:out value="${bancaEscolhida.pessoaByConvidado1IdPessoa.nome}" /></label></center><br>
            <center><label>_______________________________________</label><br></center>
            <center><label><c:out value="${bancaEscolhida.pessoaByConvidado2IdPessoa.nome}" /></label></center><br>
        </form>

    </body>
</html>
