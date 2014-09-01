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
        <title>Listar Temas</title>

    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <a href="menuPrincipalCoordenadorTCCs.jsp" class="navbar-brand">Gerenciamento de TCC  </a>
                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/ConfirmarTemaServlet">Lista de Temas</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/DatasPrazosServlet">Definir Prazos</a> </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banca Avaliadora<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarBancaTCCServlet"> Criar Banca </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/MarcarBancaServlet"> Definir Horário, Local e Data para Bancas</a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/VerificarBancaCoordenadorServlet"> Verificar Bancas</a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/AgendaDefesasServlet"> Agenda de Defesas </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/cadastroPessoaExterna.jsp"> Cadastrar Pessoa Externa </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br><br>
        <%
            String valorBotao = null;
            List<List> temasPendentes = (List<List>) request.getAttribute("retorno");
            String aprovado;
        %>  
        <div id="tela">
            <h1>Temas cadastrados</h1>

            <form name="formConfirmar" action="ConfirmarTemaServlet" method="POST">
                <%
                    if (temasPendentes != null)
                        for (int i = 0; i < temasPendentes.size(); i++) {
                            Tema tema = (Tema) temasPendentes.get(i).get(0);
                            valorBotao = "" + (i + 1);
                            if (tema.getAprovado() == Tema.APROVADO_ORIENTADOR) {
                                aprovado = "Tema aprovado pelo orientador";
                            } else {
                                aprovado = "Tema aprovado pelo coordenador";
                            }
                %>
                <table border="1" class="table table-hover">
                    <thead>
                        <tr>
                            <th>Nome Aluno</th>
                            <th> Curso </th>
                            <th>Orientador</th>
                            <th>Descrição</th>
                            <th>Carga Horária Aluno</th>
                            <th>Situação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><%=tema.getAluno().getNome()%></td>
                            <td> <%=temasPendentes.get(i).get(2) %> </td>
                            <td>
                                <%=tema.getOrientador().getProfessor().getNome()%>
                            </td>
                            <td><%=tema.getDescricao()%></td>
                            <td>
                                <input type="text" name="cargaHoraria" id="cargaHoraria"
                                       value="<%=tema.getAluno().getCargaHoraria()%>"
                                       onkeypress='return SomenteNumero(event)'/>
                                <button type="button" class="btn"
                                        id="botaoVerificar"
                                        value="<%=temasPendentes.get(i).get(1)%>"
                                        onclick="verificarCargaHoraria()">
                                    Verificar carga horária.
                                </button>
                            </td>
                            <td> <%=aprovado%> </td>
                        </tr>
                    </tbody>
                </table>

                <br>
                <button type="submit" class="btn btn-success" name="confirmar" value="<%= "Confirmar Tema " + (i + 1)%>">
                    Confirmar Tema <%=(i + 1)%>
                </button>
                <button type="submit" name="confirmar" class="btn btn-danger" value="<%= "Recusar Tema " + (i + 1)%> ">
                    Recusar Tema <%=(i + 1)%>
                </button>
                <br><br>
                <br>
            </form>

            <form action="ConfirmarTccServlet" method="POST">
                <button type="submit" class="bnt btn-info"  name="confirmarTcc1" value="<%=i%>">
                    Situação Tcc 1
                </button>
                <button type="submit" class="bnt btn-info"  name="confirmarTcc2" value="<%=i%>">
                    Situação Tcc 2
                </button>
            </form>        
            <br><br>
            <%
                }
            %>
            <br>
        </div>
        <script>
            
            function verificarCargaHoraria(){
                var totalHoras = document.getElementById("botaoVerificar");
                var campoCarga = document.getElementById("cargaHoraria");
                var cargaHoraria = Number(campoCarga.value);
                var cargaHorariaTotal = Number(totalHoras.value);
                var minimo = 65*cargaHorariaTotal/100;
                if(minimo <= cargaHoraria){
                    alert("Carga Horária válida");
                }else{
                    alert("Carga Horária Inválida");
                }
            }

            function SomenteNumero(e) {
                var tecla = (window.event) ? event.keyCode : e.which;
                if ((tecla > 47 && tecla < 58))
                    return true;
                else {
                    return false;
                }
            }
        </script>
        <!-- Bootstrap core JavaScript
================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>

