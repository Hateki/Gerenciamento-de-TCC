<%-- 
    Document   : ataDeDefesa
    Created on : 30/07/2014, 20:00:15
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

        <title>Formulário de Avaliação de Aluno de TCC</title>

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
        <h2><center><br><b>FORMULÁRIO DE AVALIAÇÃO DE TRABALHO DE CONCLUSÃO DE CURSO</b></center></h2>

        <div>
            <br><br>
            <label class="text text-uppercase" style="font-size: x-large">Aluno:</label> <label> <c:out value="${bancaEscolhida.aluno.nome}"/> </label>
            <label class="text text-uppercase" style="font-size: x-large" >Matrícula:</label> <c:out value="${bancaEscolhida.aluno.matricula}"/><br>
            <label class="text text-uppercase" style="font-size: x-large">Título do Trabalho:</label> <c:out value="${tema.descricao}"/><br>
            <label class="text text-uppercase" style="font-size: x-large">Avaliador:</label> <c:out value="${avaliador.nome}"/><br>
            <label class="text text-uppercase" style="font-size: x-large">Data:</label> <c:out value="${bancaEscolhida.data}"/><br>
            <label class="text text-uppercase" style="font-size: x-large">Local:</label> <c:out value="${bancaEscolhida.local}"/><br>
            <label class="text text-uppercase" style="font-size: x-large">Horario:</label> <c:out value="${bancaEscolhida.horario}"/><br><br><br>
        </div>

        <form method="post">
            <table border="1" class="table table-hover">
                <thead>
                    <tr>
                        <th>Banca Examinadora</th>
                        <th>Nota</th>                 
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td> Orientador: <c:out value="${bancaEncontrada.orientadorByOrientadorIdOrientador.nome}"/> </td>
                        <td> <c:out value="${tcc.notaOrientador}"/> </td>
                    </tr>
                    <tr>
                        <td> <c:out value="${bancaEncontrada.pessoaByConvidado1IdPessoa.nome}"/> </td>
                        <td> <c:out value="${tcc.notaOrientador}"/> </td>
                    </tr>
                    <tr>
                        <td> <c:out value="${bancaEncontrada.pessoaByConvidado2IdPessoa.nome}"/> </td>
                        <td> <c:out value="${tcc.notaOrientador}"/> </td>
                    </tr>
                    <c:if test="${not empty bancaEncontrada.pessoaByConvidado3IdPessoa}" var="v" scope="request">
                        <tr>
                            <td> <c:out value="${bancaEncontrada.pessoaByConvidado3IdPessoa.nome}"/> </td>
                            <td> <c:out value="${tcc.notaOrientador}"/> </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td id="mediaFinal"> Média Final </td>
                        <td> <c:out value="${mediaFinal}"/> </td>
                    </tr>    
                </tbody>
                </table>
                
                <%
                    String situacaoAluno;
                    Float mediaFinal = (Float) request.getAttribute("mediaFinal");
                    if(mediaFinal != null && mediaFinal > 6){
                        situacaoAluno = "Aprovado";
                    }else{
                        situacaoAluno = "Reprovado";
                    }
                %>
                
                <label> Situação Aluno: <%= situacaoAluno %> </label><br>
                
                <label>_______________________________________</label><br>
                <label><c:out value="${bancaEncontrada.orientadorByOrientadorIdOrientador.nome}" /></label>
                <label>_______________________________________</label><br>
                <label><c:out value="${bancaEncontrada.pessoaByConvidado1IdPessoa.nome}" /></label>
                <label>_______________________________________</label><br>
                <label><c:out value="${bancaEncontrada.pessoaByConvidado2IdPessoa.nome}" /></label>
        </form>
        <script>
            var flag = false;
            var valorAnterior = 0.0;
            var contNotaParcial = 0;
            function incrementarNotaParcialTrabDesenvolvido(valor) {
                var valorParcial = document.getElementById("notaParcialTrabDesenvolvido");
                if (!isNaN(valor) && valor !== "" && flag !== true) {
                    var valorAntigo = Number(valorParcial.value);
                    valorParcial.value = parseFloat(valorAntigo, 10) + parseFloat(valor, 10);
                    contNotaParcial++;
                } else {
                    if (valor !== "" && isNaN(valor)) {
                        alert("Digite somente números,\n\
         as notas parciais devem ser feitas utilizando ponto. Exemplo : 8.1");
                    }
                }
                flag = false;
            }

            function incrementarNotaParcialApresentacao(valor) {
                var valorParcial = document.getElementById("notaParcialApresentacao");
                if (!isNaN(valor) && valor !== "" && flag !== true) {
                    var valorAntigo = Number(valorParcial.value);
                    valorParcial.value = parseFloat(valorAntigo, 10) + parseFloat(valor, 10);
                    contNotaParcial++;
                } else {
                    if (valor !== "" && isNaN(valor)) {
                        alert("Digite somente números,\n\
         as notas parciais devem ser feitas utilizando ponto. Exemplo : 8.1");
                    }
                }
                flag = false;
            }

            function incrementarNotaParcialQualidade(valor) {
                var valorParcial = document.getElementById("notaParcialQualidade");
                if (!isNaN(valor) && valor !== "" && flag !== true) {
                    var valorAntigo = Number(valorParcial.value);
                    valorParcial.value = parseFloat(valorAntigo, 10) + parseFloat(valor, 10);
                    contNotaParcial++;
                } else {
                    if (valor !== "" && isNaN(valor)) {
                        alert("Digite somente números,\n\
         as notas parciais devem ser feitas utilizando ponto. Exemplo : 8.1");
                    }
                }
                flag = false;
            }

            function decrementaNotaParcial1(valor) {
                if (!isNaN(valor) && valor !== "") {
                    //valorAnterior = parseFloat(valor,10);
                    contNotaParcial = contNotaParcial - 1;
                }
            }

            function SomenteNumero(e) {
                var tecla = (window.event) ? event.keyCode : e.which;
                if ((tecla > 47 && tecla < 58))
                    return true;
                else {
                    if (tecla == 8 || tecla == 0) {
                        return true;
                    } else if (tecla == 46) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }

            function tamanhoNumero(numero) {
                if (!isNaN(numero) && numero !== "") {
                    var numeroFloat = parseFloat(numero);
                    if (numeroFloat <= 1 && numeroFloat >= 0) {
                        return true;
                    } else {
                        alert("Digite somente um valor de 0 a 1");
                        flag = true;
                        return false;
                    }
                }
                return false;
            }

            function somarNotas() {
                var notaFinal = document.getElementById("notaFinal");
                var valorParcialDesenvolvimento = document.getElementById("notaParcialTrabDesenvolvido");
                var valorParcialApresentação = document.getElementById("notaParcialApresentacao");
                var valorParcialQualidade = document.getElementById("notaParcialQualidade");
                if (contNotaParcial == 10) {
                    notaFinal.value = parseFloat(valorParcialDesenvolvimento.value)
                            + parseFloat(valorParcialApresentação.valueOf().value)
                            + parseFloat(valorParcialQualidade.valueOf().value);
                } else {
                    alert("Preencha todas as notas primeiro");
                }
            }

            function verificarNotaFinal() {
                var notaFinal = document.getElementById("notaFinal");
                if (notaFinal.value == "" || isNaN(notaFinal.value)) {
                    alert("Faça a soma da nota final primeiro");
                    return false;
                } else {
                    return true;
                }
            }
        </script>
    </body>
</html>

