<%-- 
    Document   : temasRequisitados
    Created on : 15/06/2014, 13:02:33
    Author     : pontofrio
--%>

<%@page import="br.edu.unipampa.model.Professor"%>
<%@page import="br.edu.unipampa.model.Orientador"%>
<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
<%@page import="br.edu.unipampa.model.Tema"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <style> 


        </style>
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Temas requisitados</title>

    </head>
    <body>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="bootstrap.min.js"></script>

        <%
            String valorBotao = null;
            List<Tema> temasPendentes = (List<Tema>) request.getAttribute("retorno");
            String aprovado;
            String classe;//Classe da linha da tabela

            String usuario = (String) request.getSession().getAttribute("usuario");
            AcessoSistema acesso = new AcessoSistema();

            Orientador orientador = acesso.procurarOrientador(usuario);
            Professor professor = acesso.procurarProfessor(usuario);

            pageContext.setAttribute("orientador", orientador);
            pageContext.setAttribute("professor", professor);
        %>

        <!-- Criando o menuPrincipal -->
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">

                <% if (orientador != null) { %>
                <a href="menuPrincipalOrientador.jsp" class="navbar-brand"> Gerenciamento de TCC </a>

                <% } else if (professor != null) { %>
                <a href="menuPrincipalProfessor.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                <% } %>

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
                
                <% } else if (professor != null) { %>    
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaServlet"> Verificar Bancas </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/TemasRequisitadosServlet"> Temas Requisitados  </a></li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
                <% } %>

            </div>
        </div>
        <br><br><br><br>
        <!------------------------------------------------------------------------------------------------------------------------------------>



        <div id="tela">
            <h1>Temas cadastrados</h1>

            <%                for (int i = 0; i < temasPendentes.size(); i++) {
                    Tema tema = temasPendentes.get(i);
                    valorBotao = "" + (i + 1);
            %>
            <form name="formConfirmar" action="DetalheTemaServlet" method="POST">
                <table border="1" class="table table-hover">
                    <thead>
                        <tr>
                            <th>Nome Aluno</th>
                            <th>Descrição</th>
                            <th>Situação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <%
                                if (tema.getAprovado() == Tema.APROVADO_ORIENTADOR) {
                                    aprovado = "Aprovado";
                                } else if (tema.getAprovado() == Tema.APROVADO_COODENADOR) {
                                    aprovado = "Aprovado pelo coordenador";
                                } else {
                                    aprovado = "Não aprovado";
                                }
                            %>
                            <td><%=tema.getAluno().getNome()%></td>
                            <td><%=tema.getDescricao()%></td>
                            <td> <%=aprovado%> </td>

                        </tr>
                    </tbody>
                </table>
                <input type="submit" class="btn btn-info" name="confirmar" value="<%= "Confirmar Tema " + (i + 1)%>" />
                <input type="submit" name="confirmar" class="btn btn-danger" value="<%= "Recusar Tema " + (i + 1)%> " />
                <br>
                </div>
            </form>
            <br>
            <form action="ConfirmarTccServlet" method="POST">
                <button type="submit" class="bnt btn-success"  name="confirmarTcc1" value="<%=i%>">
                    Situação Tcc 1
                </button>
            </form>
            <form action="ConfirmarTccServlet" method="POST">
                <button type="submit" class="bnt btn-success"  name="confirmarTcc2" value="<%=i%>">
                    Situação Tcc 2
                </button>
            </form>        
            <br><br>
            <%
                }
            %>
            <!-- Bootstrap core JavaScript
            ================================================== -->
            <!-- Placed at the end of the document so the pages load faster -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
            <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
