<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : datasPrazos
    Created on : 24/07/2014, 10:05:30
    Author     : Kezia
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style> 
            #form1 {margin-left:550px; margin-right:550px}; 
            H1 { text-align: right}
        </style>
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
        <title>Gerenciamento TCC</title>
        <script type="text/javascript">

            function testarData() {

                if ((isNaN(tccForm.date.value)) || (tccForm.date.value === "")) {

                    alert("Digite apenas números!");

                }

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

            }
<!-- Fim do JavaScript que validará os campos obrigatórios! -->
        </script>
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
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CriarBancaTCCServlet"> Marcar Banca </a></li>
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
        <br><br><br><br><br>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="bootstrap.min.js"></script>

        <form method="post" action="DatasPrazosServlet">

            <legend> Período de cadastros de Temas </legend>
            Data Inicial: <input name="dataTemaInicio" id="dataTemaInicio" type="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Inicial" maxlength="14" value="<c:out value="${dataTemaInicio}"/>"/><br>
            Data Final: <input name="dataTemaFinal" id="dataTemaFinal" type="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Final" maxlength="14" value="<c:out value="${dataTemaFinal}"/>"/><br>
            <br><br>

            <legend>Períodos para os envios de TCC 1</legend>
            Data Inicial para Envio: <input name="dataTcc1Inicio" id="dataTccInicio" type="date"  onblur="javascript: validardata(this.value);" placeholder="Digite data Inicial" maxlength="14" value="<c:out value="${dataTcc1Inicio}"/>"/><br>
            Data Final para Envio: <input name="dataTcc1Final" id="dataTccInicio" type="date"  onblur="javascript: validardata(this.value);" placeholder="Digite data Inicial" maxlength="14" value="<c:out value="${dataTcc1Final}"/>"/><br>
            <br><br>


            <legend>Períodos para os envios de TCC 2</legend>
            Data de Envio Inicial: <input name="dataTcc2Inicio" id="dataTccInicio" type="date"  onblur="javascript: validardata(this.value);" placeholder="Digite data Inicial" maxlength="14" value="<c:out value="${dataTcc2Inicio}"/>"/><br>
            Data de Envio Final: <input name="dataTcc2Final" id="dataTccFinal" type="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Final" maxlength="14" value="<c:out value="${dataTcc2Final}"/>"/><br>
            <br><br>



            <legend>Períodos para os envios das Submissões Corrigidas do TCC 2</legend>
            Data de Envio Inicial: <input name="dataTccSubmissaoCorrigidaInicio" id="dataTccInicio" type="date"  onblur="javascript: validardata(this.value);" placeholder="Digite data Inicial" maxlength="14" value="<c:out value="${dataTccSubmissaoCorrigidaInicio}"/>"/><br>
            Data de Envio Final: <input name="dataTccSubmissaoCorrigidaFinal" id="dataTccFinal" type="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Final" maxlength="14" value="<c:out value="${dataTccSubmissaoCorrigidaFinal}"/>"/><br>
            <br><br>

            <legend>Período para marcar Banca</legend>
            Data Inicial: <input name="dataBancaInicio" id="dataBancaInicio" type="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Inicial" maxlength="14" value="<c:out value="${dataBancaInicial}"/>"/><br>
            Data Final:   <input name="dataBancaFinal" id="dataBancaFinal" type="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Final" maxlength="14" value="<c:out value="${dataBancaFinal}"/>"/><br><br>
            <br><br>

            <input type="submit" class="btn btn-primary" name="enviar" id="enviar" value="Enviar"   />
            <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="retornaPaginaPrincipal()">
        </form>

        <script>
            function retornaPaginaPrincipal() {
                location.href = "menuPrincipalProfessor.html";
            }
        </script>

    </div>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
</body>
</html>
</html>