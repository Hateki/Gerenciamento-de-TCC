<%@page import="br.edu.unipampa.model.web.AcessoSistema"%>
<%@page import="br.edu.unipampa.model.Orientador"%>
<%@page import="br.edu.unipampa.model.Coordenador"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : cadastroPessoaExterna
    Created on : 22/06/2014, 17:44:21
    Author     : pontofrio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style> 
            #form1 {margin-left:550px; margin-right:550px};         
        </style>
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
        <title>Cadastro Pessoa Externa</title>
        <script type="text/javascript">

            function validaCampoNome()
            {
                if (document.cadPform.nomePessoaExterna.value === "") {
                    alert("O Campo obrigatório Nome não foi preenchido!");
                    return false
                }
                else
                    return true;
            }

            function validaCampoSenha1() {

                if (document.cadPform.passwordPessoaExterna.value === "") {
                    alert("O Campo obrigatório Senha não foi preenchido");
                    return false;
                }
                else
                    return true;
            }


            function validaCampoSenha2() {

                if (document.cadPform.passwordPessoaExterna2.value === "") {
                    alert("O Campo obrigatório confirmação de senha não foi preenchido");
                    return false;
                }
                else
                    return true;
            }
            function validaCampoSenhasIguais() {
                senha1 = document.cadPform.passwordPessoaExterna.value;
                senha2 = document.cadPform.passwordPessoaExterna2.value;

                if (senha1 === senha2) {
                    return true;
                }
                else
                    alert("As senhas não coincidem")
                return false;
            }

            function validaInst() {

                if (document.cadPform.nomeInstituicao.value === "") {
                    alert("O Campo obrigatório Nome Instituicao não foi preenchido");
                    return false;
                }
                else
                    return true;
            }


            function validarCPF(cpf) {
                var filtro = /^\d{3}.\d{3}.\d{3}-\d{2}$/i;

                if (!filtro.test(cpf))
                {
                    window.alert("CPF inválido. Tente novamente.");
                    return false;
                }

                cpf = remove(cpf, ".");
                cpf = remove(cpf, "-");

                if (cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111" ||
                        cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" ||
                        cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" ||
                        cpf == "88888888888" || cpf == "99999999999")
                {
                    window.alert("CPF inválido. Tente novamente.");
                    return false;
                }

                soma = 0;
                for (i = 0; i < 9; i++)
                {
                    soma += parseInt(cpf.charAt(i)) * (10 - i);
                }

                resto = 11 - (soma % 11);
                if (resto == 10 || resto == 11)
                {
                    resto = 0;
                }
                if (resto != parseInt(cpf.charAt(9))) {
                    window.alert("CPF inválido. Tente novamente.");
                    return false;
                }

                soma = 0;
                for (i = 0; i < 10; i++)
                {
                    soma += parseInt(cpf.charAt(i)) * (11 - i);
                }
                resto = 11 - (soma % 11);
                if (resto == 10 || resto == 11)
                {
                    resto = 0;
                }

                if (resto != parseInt(cpf.charAt(10))) {
                    window.alert("CPF inválido. Tente novamente.");
                    return false;
                }

                return true;
            }

            function remove(str, sub) {
                i = str.indexOf(sub);
                r = "";
                if (i == -1)
                    return str;
                {
                    r += str.substring(0, i) + remove(str.substring(i + sub.length), sub);
                }

                return r;
            }

            /**
             * MASCARA ( mascara(o,f) e execmascara() ) CRIADAS POR ELCIO LUIZ
             * elcio.com.br
             */
            function mascara(o, f) {
                v_obj = o
                v_fun = f
                setTimeout("execmascara()", 1)
            }

            function execmascara() {
                v_obj.value = v_fun(v_obj.value)
            }

            function cpf_mask(v) {
                v = v.replace(/\D/g, "")                 //Remove tudo o que não é dígito
                v = v.replace(/(\d{3})(\d)/, "$1.$2")    //Coloca ponto entre o terceiro e o quarto dígitos
                v = v.replace(/(\d{3})(\d)/, "$1.$2")    //Coloca ponto entre o setimo e o oitava dígitos
                v = v.replace(/(\d{3})(\d)/, "$1-$2")   //Coloca ponto entre o decimoprimeiro e o decimosegundo dígitos
                return v
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

            function ValidaEmail()
            {
                var obj = document.cadPform.email;
                var txt = obj.value;
                if ((txt.length != 0) && ((txt.indexOf("@") < 1) || (txt.indexOf('.') < 7)))
                {
                    alert('Email incorreto');
                    obj.focus();
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
                <%
                    String usuario = (String) request.getSession().getAttribute("usuario");
                    AcessoSistema acesso = new AcessoSistema();

                    Orientador orientador = acesso.procurarOrientador(usuario);
                    Coordenador coordenador = acesso.procurarCoordenador(usuario);

                    pageContext.setAttribute("orientador", orientador);
                    pageContext.setAttribute("coordenador", coordenador);

                %>

                <% if (orientador != null) { %>
                <a href="menuPrincipalOrientador.jsp" class="navbar-brand"> Gerenciamento de TCC </a>

                <% } else if (coordenador != null) { %>
                <a href="menuPrincipalCoordenadorTCCs.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                <% } %>

                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">

                    <% if (coordenador != null) { %>    
                    <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav">
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/ConfirmarTemaServlet">Lista de Temas</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/RelacaoNotasServlet">Relação de Notas</a> </li>
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
                    <% } else if (orientador != null) { %>
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
                    <% } %>
                </div>
            </div>
        </div>
        <br><br><br><br><br>
       

        <div id="form1">
            <%
                String retorno = (String) request.getAttribute("retorno");
                if (retorno != null && retorno.equalsIgnoreCase("usuario Existe")) {
            %>
            <br>
            <div class="alert alert-danger" role="alert">Usuário já existe</div>
            <%  }
                if (retorno != null && retorno.equalsIgnoreCase("Sucesso")) {
            %>
            <br>
            <div class="alert alert-success" role="alert">Cadastro realizado com sucesso</div>
            <%
                }
            %>
            <form id="cadPform" name="cadPform" method="post" action="CadastraPessoaExternaServelt"
                  onsubmit="return validaCampoNome(), validaInst(), validaCampoSenha1(), validaCampoSenha2(), validaCampoSenhasIguais();">

                <label for="titulo"></label>
                <legend>Cadastro Pessoa Externa</legend>

                Nome Completo: <input name="nomePessoaExterna" type="text" id="nomePessoaExterna" maxlength="200" onblur="validaEspaco(this)" placeholder="Nome Completo" required /><br></br>
                Instituição: <input name="nomeInstituicao" type="text" id="nomeInstituicao" maxlength="200" onblur="validaEspaco(this)" placeholder="Nome da Instituição" required/><br></br>
                Senha: <input name="passwordPessoaExterna" type="password" id="passwordPessoaExterna" maxlength="200" onblur="validaEspaco(this)" placeholder="Digite sua senha" required><br></br>
                Confirmação de Senha: <input name="passwordPessoaExterna2" type="password" id="passwordPessoaExterna2" maxlength="200" onblur="validaEspaco(this)" placeholder="Confirme sua senha" required><br></br>
                Número do CPF:   <input type="text" name="cpf" id="cpf" onblur="javascript: validarCPF(this.value);" onkeypress="javascript: mascara(this, cpf_mask);"  placeholder="Digite seu CPF" maxlength="14" required/><br></br>
                E-mail:   <input type="email" name="email" id="email" onBlur="ValidaEmail()" placeholder="Digite seu e-mail" required><br></br>

                <input type="submit" class="btn btn-primary" name="enviar" id="enviar" value="Confirmar"   />
                <input type="reset" class="btn btn-warning" name="limpar" id="limpar" value="Limpar" />
                <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="history.go(-1)">
                <script>
                    function retornaPaginaPrincipal() {
                        location.href = "menuPrincipalProfessor.html"
                    }
                </script>

            </form>
        </div>
        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
</html>

