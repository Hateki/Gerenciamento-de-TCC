<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : criarTemaTCC
    Created on : 22/06/2014, 19:37:04
    Author     : pontofrio
--%>

<%@page import="br.edu.unipampa.model.Pessoa"%>
<%@page import="java.util.List"%>
<%@page import="br.edu.unipampa.controller.CriarBancaTCCServlet"%>
<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
<%@page import="br.edu.unipampa.model.Professor"%>
<%@page import="br.edu.unipampa.model.Aluno"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style> 
            #area {margin-left:550px;}; 

        </style>
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
        <title>Criar Banca do TCC</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

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

        <form id="tccForm" name="tccForm" method="post" action="CriarTemaTCCServlet"
              onsubmit="return">

            <label for="titulo"></label>
            <div id="area">

                <h1>Criar banca do TCC</h1>
                <br></br>
                <h4>Banca Avaliadora</h4>

                Matrícula Aluno: <input type="text" name="matricula" id="matricula" maxlength="9" onblur="validaEspaco(this), testarMatricula()" required/>
                <a href="#listaAlunos" data-toggle="modal" class="btn btn-primary"> Ver lista de Alunos </a> <br>
                Professor: <input type="text" name="professor1" id="professor1" onblur="validaEspaco(this)" required/>
                <a href="#listaPessoas" data-toggle="modal" class="btn btn-primary"> Ver lista de Pessoas </a> <br>
                Professor: <input type="text" name="professor2" id="professor2" onblur="validaEspaco(this)" required/>
                <a href="#listaPessoas" data-toggle="modal" class="btn btn-primary"> Ver lista de Pessoas </a> <br>


                <div style="display: none" id="professor3">
                    Professor : <input type="text" name="professor3" id="professor3" onblur="validaEspaco(this)"/>
                    <a href="#listaPessoas" data-toggle="modal" class="btn btn-primary"> Ver lista de Pessoas </a>
                </div>        

                <button type="button" class="btn btn-success" onclick="mostraProfessorInv()" name="btAddProfessor" id="btAddProfessor" >Adicionar</button><br></br>
                <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="retornaPaginaPrincipal()">
                <input type="submit" class="btn btn-info" name="enviar" id="enviar" value="Enviar"   />
            </div>
            <script>
                function retornaPaginaPrincipal() {
                    location.href = "menuPrincipalProfessor.html"
                }
            </script>


        </form>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script>
                function mostraProfessorInv() {
                    $('#professor3').slideDown();
                }

                //trim completo
                function trim(str) {
                    return str.replace(/^\s+|\s+$/g, "");
                }

                function validaEspaco(input) {
                    texto = input.value;
                    textoNovo = trim(texto);
                    if (textoNovo === "") {
                        input.value = textoNovo;
                        alert("Campo " + input.name + " invalido");
                    }
                }

                function testarMatricula() {

                    if ((isNaN(tccForm.matricula.value)) || (tccForm.matricula.value == "")) {

                        alert("Digite apenas números!");

                    }

                }
        </script>

        <c:if test="${not empty retorno}" var="variavel" scope="request">
            <c:if test="${retorno == 0 }" var="variavel" scope="request"><!Professores Existem>
            <div class="alert alert-success" role="alert">Cadastro realizado com sucesso</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 1 }" var="variavel" scope="request"><!Os usuarios dos três professores digitados não existem>
            <div class="alert alert-danger" role="alert">Os usuarios dos três professores digitados não existem</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 2 }" var="variavel" scope="request"><!Os usuários do primeiro e do segundo professor não existem>
            <div class="alert alert-danger" role="alert">Os usuários do primeiro e do segundo professor não existem</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 3 }" var="variavel" scope="request"><!Os usuários do primeiro e do terceiro professor não existem>
            <div class="alert alert-danger" role="alert">Os usuários do primeiro e do terceiro professor não existem</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 4 }" var="variavel" scope="request"><!Os usuários do segundo e do terceiro professor não existem>
            <div class="alert alert-danger" role="alert">Os usuários do segundo e do terceiro professor não existem</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 5 }" var="variavel" scope="request"><!O primeiro professor digitado não existe>
            <div class="alert alert-danger" role="alert">O primeiro professor digitado não existe</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 6 }" var="variavel" scope="request"><!O segundo professor digitado não existe>
            <div class="alert alert-danger" role="alert">O segundo professor digitado não existe</div>
        </c:if>
    </c:if>   

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 7 }" var="variavel" scope="request"><!O terceiro professor digitado não existe>
            <div class="alert alert-danger" role="alert">O terceiro professor digitado não existe</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 8 }" var="variavel" scope="request"><!O Professor digitado é igual ao orientador digitado>
            <div class="alert alert-danger" role="alert">O Professor digitado é igual ao orientador digitado</div>
        </c:if>
    </c:if>

    <c:if test="${not empty retorno}" var="variavel" scope="request">
        <c:if test="${retorno == 9 }" var="variavel" scope="request"><!Um usuário de aluno foi encontrado no lugar onde deveria haver um professor>
            <div class="alert alert-danger" role="alert">Um usuário de aluno foi encontrado no lugar onde deveria haver um professor</div>
        </c:if>
    </c:if>

    <div class="modal fade" id="listaPessoas" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4>Lista de Pessoas</h4>
                </div>
                <div class="modal-body">
                    <table border="1" class="table table-hover">
                        <thead>
                            <tr>
                                <th>Usuário Pessoa</th>
                                <th>Nome do Pessoa</th>
                                <th>Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pessoa" items="${pessoas}">
                                <tr>
                                    <td> <c:out value="${pessoa.usuario}"/> </td>
                                    <td> <c:out value="${pessoa.nome}"/> </td>
                                    <td> <c:out value="${pessoa.email}"/> </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="listaAlunos" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4>Lista de Alunos</h4>
                </div>
                <div class="modal-body">
                    <table border="1" class="table table-hover">
                        <thead>
                            <tr>
                                <th>Usuário Aluno</th>
                                <th>Nome do Aluno</th>
                                <th>Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="aluno" items="${alunos}">
                                <tr>
                                    <td> <c:out value="${aluno.usuario}"/> </td>
                                    <td> <c:out value="${aluno.nome}"/> </td>
                                    <td> <c:out value="${aluno.pessoa.email}"/> </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap core JavaScript
                ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

