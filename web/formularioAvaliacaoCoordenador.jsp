<%-- 
    Document   : FormularioAvaliacaoCoordenador
    Created on : 27/08/2014, 17:36:20
    Author     : Pedro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div id="imagem">
            <center><img src="imagem_logo_unipampa.jpg" ></center>
        </div>

        <h4 style="color: #3c763d"><strong><center>UNIVERSIDADE FEDERAL DO PAMPA 
                    <br>CAMPUS ALEGRETE
                    <br>BACHARELADO EM ENGENHARIA DE SOFTWARE</center>
            </strong>
        </h4>

        <br>
        <h4><center><b>FORMULÁRIO DE AVALIAÇÃO DE TRABALHO DE CONCLUSÃO DE CURSO</b></center></h4>

        <div>
            <br>
            <style>
                .aluno { display:inline; }
                .valorAluno { display:inline; }

                .matricula { display:inline; }
                .valorMatricula { display:inline; }

                .titulo { display:inline; }
                .valorTitulo { display:inline; }

                .avaliador { display:inline; }
                .valorAvaliador { display:inline; }

            </style>
            <div>
                <h5 class="aluno"><left><b> ALUNO: </b></left></h5> <h6 class="valorAluno"><left> <c:out value="${bancaEscolhida.aluno.nome}"/> </left></h6>
            </div>
            <div>
                <h5 class="matricula"><left><b> MATRÍCULA: </b></left></h5> <h6 class="valorMatricula"><left> <c:out value="${bancaEscolhida.aluno.matricula}"/> </left></h6>
            </div>
            <div>
                <h5 class="titulo"><left><b> TÍTULO DO TRABALHO: </b></left></h5> <h6 class="valorTitulo"><left> <c:out value="${tema.descricao}"/> </left></h6>
            </div>
            <div>
                <h5 class="avaliador"><left><b> AVALIADOR: </b></left></h5> <h6 class="valorAvaliador"><left> <c:out value="${avaliador.nome}"/> </left></h6> <br><br>
            </div>
        </div>

        <form method="post" name="formularioDeAvaliacao"
              action="SalvarNotasCoordenadorServlet" method="post"
              onsubmit="return verificarNotaFinal();"
              >
            <div id="formAvaliacao">
                <table class="table table-hover">
                    <tr class="success">
                        <td><label style="font-size: 16px">I – TRABALHO DESENVOLVIDO (4,0)</label></td>
                        <td style="text-align: center"> <label style="font-size: 16px">NOTA</label></td>
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Relevância (1,0)</label><br>
                            <label style="font-size: 14px">O problema é claramente apresentado e devidamente justificado</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaRelevancia" id="notaRelevancia"
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialTrabDesenvolvido(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Estado da Arte (1,0)</label><br>
                            <label style="font-size: 14px">O referêncial teórico está adequado e atualizado</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaEstadoDaArte" id="notaEstadoDaArte"
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialTrabDesenvolvido(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Corretude técnica (1,0)</label><br>
                            <label style="font-size: 14px">O problema foi resolvido utilizando a metodologia adequada</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaCorretudeTecnica" id="notaCorretudeTecnica"
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialTrabDesenvolvido(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Abrangência (1,0)</label><br>
                            <label style="font-size: 14px">O trabalho está completo e os seus objetivos específicos foram atingidos</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaAbrangencia" id="notaAbrangencia"
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialTrabDesenvolvido(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr class="warning">
                        <td style="text-align: left"><label class="label label-warning" style="font-size: 16px">NOTA PARCIAL:</label></td>
                        <td style="text-align: center"><input type="text" name="notaParcialTrabDesenvolvido" id="notaParcialTrabDesenvolvido" ></td>           
                    </tr>
                </table>
                <br>
                <table class="table table-hover">
                    <tr class="success">
                        <td><label style="font-size: 16px">II – APRESENTAÇÃO (3,0)</label></td>
                        <td style="text-align: center"><label style="font-size: 16px">NOTA</label></td>
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Clareza (1,0)</label><br>
                            <label style="font-size: 14px">As ideias foram expostas de maneira crítica e em consonância com o texto</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaClarezaApresentacao" id="notaClarezaApresentacao"
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialApresentacao(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Conhecimento (1,0)</label><br>
                            <label style="font-size: 14px">Na abordagem do tema foi demonstrado segurança e domínio do assunto</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaConhecimento" id="notaConhecimento"
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialApresentacao(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Planejamento (1,0)</label><br>
                            <label style="font-size: 14px">A apresentação teve sequência lógica e divisão equitativa do tempo</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaPlanejamento" id="notaPlanejamento" 
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialApresentacao(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr class="warning">
                        <td style="text-align: left"><label class="label label-warning" style="font-size: 16px">NOTA PARCIAL:</label></td>
                        <td style="text-align: center"><input type="text" name="notaParcialApresentacao" id="notaParcialApresentacao" ></td>           
                    </tr>
                </table>
                <br>
                <table class="table table-hover">
                    <tr class="success">
                        <td><label style="font-size: 16px">III – QUALIDADE DO TEXTO (3,0)</label></td>
                        <td style="text-align: center"><label style="font-size: 16px">NOTA</label></td>
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Clareza (1,0)</label><br>
                            <label style="font-size: 14px"> As ideias foram expostas de maneira clara e o texto é legível</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaClarezaQualidade" id="notaClarezaQualidade" 
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialQualidade(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Gramática e Ortografia (1,0)</label><br>
                            <label style="font-size: 14px">O texto segue as regras gramaticais e ortográficas da língua portuguesa</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaGramaticaEOrtografia" id="notaGramaticaEOrtografia" 
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialQualidade(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr>
                        <td><label style="font-size: 16px" class="text text-muted">Estrutura e Organização (1,0)</label><br>
                            <label style="font-size: 14px">O texto está bem estruturado e organizado</label></td>
                        <td style="text-align: center">
                            <input type="text" name="notaEstruturaEOrganizacao" id="notaEstruturaEOrganizacao" 
                                   onblur="return tamanhoNumero(this.value), incrementarNotaParcialQualidade(this.value)"
                                   onkeypress='return SomenteNumero(event)'
                                   onselect="decrementaNotaParcial1(this.value)">
                        </td>           
                    </tr>
                    <tr class="warning">
                        <td style="text-align: left"><label class="label label-warning" style="font-size: 16px">NOTA PARCIAL:</label></td>
                        <td style="text-align: center"><input type="text" name="notaParcialQualidade" id="notaParcialQualidade" ></td>           
                    </tr>
                    <tr class="info">
                        <td style="text-align: left"><label style="font-size: 16px" class="label label-info">NOTA FINAL:</label></td>
                        <td style="text-align: center"><input type="text" name="notaFinal" id="notaFinal" ></td>                        
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align: center"><button type="button" class="bnt btn-primary" onclick="somarNotas()"> <strong>Somar Notas</strong> </button></td>
                    </tr>
            </div>
        </table>

        <label style="font-size: 16px" class="label label-default">Comentários:</label><br><br>
        <textarea class="form-control" rows="10"></textarea><br>

        <button type="submit" class="btn btn-primary" name="finalizar" id="finalizar" value="<c:out value='${bancaEscolhida.idBanca}'/>">
            <strong>Finalizar</strong>
        </button><br><br>
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
