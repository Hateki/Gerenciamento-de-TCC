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
            #area {margin-left:550px;}; 

        </style>
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
    </head>
    <script>
function validaData(campo){  
        if (campo.value!=""){  
            erro=0;  
            hoje = new Date();  
            anoAtual = hoje.getFullYear();  
            barras = campo.value.split("/");  
  
            if (barras.length == 3){  
                dia = barras[0];  
                mes = barras[1];  
                ano = barras[2];  
                resultado = (!isNaN(dia) && (dia > 0) && (dia < 32)) && (!isNaN(mes) && (mes > 0) && (mes < 13)) && (!isNaN(ano) && (ano.length == 4) && (ano <= anoAtual && ano >= 1900));  
  
                if (!resultado){  
                    alert("Data inválida.");  
                    campo.focus();  
                    return false;  
                    }  
                }else{  
                    alert("Data inválida.");  
                    campo.focus();  
                    return false;  
                    }  
            return true;  
            }  
        } 
    </script>
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
        <br><br><br><br>
        <script src="http://code.jquery.com/jquery-latest.js"></script>

        <script src="bootstrap.min.js"></script>
        <script>
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
        </script>
    <legend> Datas de Envio TCC</legend>

    Data para Entrega Inicial: <input name="date" type="date"  id="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Inicial" maxlength="14" required/><br></br>
    Data para Entrega Final:   <input name="date" type="date" id="date" onblur="javascript: validardata(this.value);" placeholder="Digite data Final" maxlength="14" required/><br></br>

    <input type="submit" class="btn btn-primary" name="enviar" id="enviar" value="Enviar"   />
    <input type="reset" class="btn btn-warning" name="limpar" id="limpar" value="Limpar" />
    <input  type="button" class="btn btn-danger  " name="voltar" id="voltar" value="Voltar" onClick="retornaPaginaPrincipal()">
    <script>
                function retornaPaginaPrincipal() {
                location.href = "menuPrincipalProfessor.html";
                }
    </script>

    <!-- Bootstrap core JavaScript
                ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

</script>
</body>
</html>

