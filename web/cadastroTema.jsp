<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : cadastroTema
    Created on : 22/06/2014, 18:53:32
    Author     : pontofrio
--%>

<%@page import="java.util.List"%>
<%@page import="br.edu.unipampa.model.Professor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <style> 
        #cadTema {margin-left:550px; margin-right:550px}; 

    </style>
    <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Cadastro Tema</title>
    <script type="text/javascript">

        function validaEspacoOrientador() {
            var orientador = cadform.orientador.value;
            var msg = "";
            if (orientador.search(/\s/g) != -1)
            {
                msg += "NNão é permitido iniciar espaços em branco no Campo Orientador\n";
                orientador = orientador.replace(/\s/g, "");
            }
            if (orientador.search(/[^a-z0-9]/i) != -1)
            {
                msg += "Não é permitido caracteres especiais";
                orientador = orientador.replace(/[^a-z0-9]/gi, "");
            }
            if (msg)
            {
                alert(msg);
                cadform.orientador.value = orientador;
                return false;
            }
            return true;
        }

        function validaEspacoTema() {
            var tema = cadform.tema.value;
            var msg = "";
            if (tema.search(/\s/g) != -1)
            {
                msg += "Não é permitido iniciar espaços em branco no Campo Tema\n";
                tema = tema.replace(/\s/g, "");
            }
            if (orientador.search(/[^a-z0-9]/i) != -1)
            {
                msg += "Não é permitido caracteres especiais";
                tema = tema.replace(/[^a-z0-9]/gi, "");
            }
            if (msg)
            {
                alert(msg);
                cadform.orientador.value = tema;
                return false;
            }
            return true;
        }

        //trim completo
        function trim(str) {
            return str.replace(/^\s+|\s+$/g, "");
        }
<!-- Fim do JavaScript que validará os campos obrigatórios! -->
    </script>
    <script>
        function validaEspaco(input) {
            texto = input.value;
            textoNovo = trim(texto);
            if (textoNovo === "") {
                input.value = textoNovo;
                alert("Campo " + input.name + " invalido");
            }
        }

        function retornaPaginaPrincipalAluno() {
            location.href = "menuPrincipalAluno.html"
        }
    </script>
</head>

<body>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a href="menuPrincipalAluno.html" class="navbar-brand"> Gerenciamento de TCC </a>
            <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="collapse navbar-collapse OpcoesMenu">
                <ul class="nav navbar-nav navbar-right">
                    <li> <a href="http://localhost:8080/GerenciamentoTCC/verificarTemaAluno.html"> Verificar Situação de Tema </a> </li>
                    <li> <a href="http://localhost:8080/GerenciamentoTCC/CadastroTemaServlet"> Cadastrar Tema </a> </li>
                    <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                    <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                    <li> <a href="http://localhost:8080/GerenciamentoTCC/telaLogin.jsp"> Sair</a> </li>
                </ul>
            </div>
        </div>

    </div>
    <form id="cadform" name="cadform" method="post" action="CadastroTemaServlet"
          onsubmit="return validaEspacoTema(), validaEspacoOrientador(), validaCampo(), validaCampo2();">
        <div id="cadTema">

            <legend>Cadastro Do Tema</legend>

            <strong>Tema:</strong><br><br/>
            <textarea rows="6" cols="40" name="tema" id="tema" maxlength="200"  onblur="validaEspaco(this)" required></textarea><br><br/>

            Orientador: <input type="text" name="orientador" id="orientador" onblur="validaEspaco(this)" placeholder="Orientador" required />
            <a href="#listaProfessores" data-toggle="modal" class="btn btn-success"> Ver lista </a>
            <br></br>

            <input class="btn btn-primary" type="submit" id="enviar" name="enviar" value="Enviar">
            <input type="reset" class="btn btn-warning" name="limpar" id="limpar" value="Limpar" />
            <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="retornaPaginaPrincipalAluno()"> 
            </form>
        </div>

        <%
            String retorno = (String) request.getAttribute("retorno");
            List<Professor> professores = (List<Professor>) request.getAttribute("professores");

            if (retorno != null && retorno.equalsIgnoreCase("Sucesso")) {
        %>

        <div class="alert alert-success" role="alert">Cadastro realizado com sucesso</div>

        <%  } else if (retorno != null && retorno.equalsIgnoreCase("Professor Nao Existe")) {
        %>
        <div class="alert alert-danger" role="alert">O professor digitado não existe</div>
        <%  } else if (retorno != null && retorno.equalsIgnoreCase("Problema")) {
        %>
        <div class="alert alert-danger" role="alert">Ocorreu um problema no sistema tente o cadastro novamente</div>
        <%
            }
        %>
        <div class="modal fade" id="listaProfessores" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Lista de Professores</h4>
                    </div>
                    <div class="modal-body">
                        <table border="1" class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Nome do Professor</th>
                                    <th>Email</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="professor" items="${professores}">
                                    <tr>
                                        <td> <c:out value="${professor.nome}"/> </td>
                                        <td> <c:out value="${professor.pessoa.email}"/> </td>
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
