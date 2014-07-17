<%-- 
    Document   : confirmarTema
    Created on : 30/06/2014, 19:28:59
    Author     : Pedro Henrique França Silva
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
                <a href="menuPrincipalAluno.html" class="navbar-brand"> Menu Principal </a>
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
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/criarBancaTCC.jsp"> Marcar Banca </a></li>
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
        <br><br><br>
        <%
            String valorBotao = null;
            List<Tema> temasPendentes = (List<Tema>) request.getAttribute("retorno");
            String aprovado;
        %>
        <div id="tela">
            <h1>Temas cadastrados</h1>

            <form name="formConfirmar" action="ConfirmarTemaServlet" method="POST">
                <%
                    if(temasPendentes != null)
                    for (int i = 0; i < temasPendentes.size(); i++) {
                        Tema tema = temasPendentes.get(i);
                        valorBotao = "" + (i + 1);
                        if(tema.getAprovado() == Tema.APROVADO_ORIENTADOR){
                            aprovado = "Tema aprovado pelo orientador";
                        }else{
                            aprovado = "Tema aprovado pelo coordenador";
                        }
                %>
                <table border="1" class="table table-hover">
                    <thead>
                        <tr>
                            <th>Nome Aluno</th>
                            <th>Orientador</th>
                            <th>Descrição</th>
                            <th>Carga Horária Aluno</th>
                            <th>Situação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><%=tema.getAluno().getNome()%></td>
                            <td><%=tema.getOrientador().getProfessor().getNome()%></td>
                            <td><%=tema.getDescricao()%></td>
                            <td> <%=tema.getAluno().getCargaHoraria()%> </td>
                            <td> <%=aprovado%> </td>
                        </tr>
                    </tbody>
                </table>

                <br>
                <input type="submit" class="btn btn-info" name="confirmar" value="<%= "Confirmar Tema " + (i + 1)%>" />
                <input type="submit" name="confirmar" class="btn btn-warning" value="<%= "Recusar Tema " + (i + 1)%> " />
                <br><br>
                
                <%
                    }
                %>
                <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="retornaPaginaPrincipal()">
                <br>
                </div>
                <script>
                    function retornaPaginaPrincipal() {
                        location.href = "menuPrincipalProfessor.html"
                    }
                </script>
            </form>
                <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>

