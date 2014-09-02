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

        <title>Ata de Defesa</title>

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
        <h4><center><b>ATA DE DEFESA</b></center></h4>

        <div>
            <style>
                .aluno { display:inline; }
                .valorAluno { display:inline; }

                .matricula { display:inline; }
                .valorMatricula { display:inline; }

                .titulo { display:inline; }
                .valorTitulo { display:inline; }

                .data { display:inline; }
                .valorData { display:inline; }

                .local { display:inline; }
                .valorLocal { display:inline; }

                .horario { display:inline; }
                .valorHorario { display:inline; }

                .situacao { display:inline; }
                .valorSituacao { display:inline; }
            </style>




            <br>
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
                <h5 class="data"><left><b> DATA: </b></left></h5> <h6 class="valorData"><left> <c:out value="${bancaEscolhida.data}"/> </left></h6>
            </div>
            <div>
                <h5 class="local"><left><b> LOCAL: </b></left></h5> <h6 class="valorLocal"><left> <c:out value="${bancaEscolhida.local}"/> </left></h6>
            </div>
            <div>
                <h5 class="horario"><left><b> HORÁRIO: </b></left></h5> <h6 class="valorHorario"><left> <c:out value="${bancaEscolhida.horario}"/> </left></h6> <br>
            </div>
            <br>
        </div>

        <form method="post">
            <table border="1" class="table table-hover">
                <thead>
                    <tr>
                        <th>BANCA AVALIADORA</th>
                        <th>NOTA</th>                 
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td> Orientador: <c:out value="${bancaEscolhida.orientadorByOrientadorIdOrientador.nome}"/> </td>
                        <c:if test="${tcc.notaOrientador < 0}" var="v" scope="request">
                            <td> Não Avaliado </td>
                        </c:if>
                        <c:if test="${tcc.notaOrientador >= 0}" var="v" scope="request">
                            <td> <c:out value="${tcc.notaOrientador}"/> </td>
                        </c:if>
                    </tr>
                    <tr>
                        <td> <c:out value="${bancaEscolhida.pessoaByConvidado1IdPessoa.nome}"/> </td>
                        <c:if test="${tcc.notaConvidado1 < 0}" var="v" scope="request">
                            <td> Não Avaliado </td>
                        </c:if>
                        <c:if test="${tcc.notaConvidado1 >= 0}" var="v" scope="request">
                            <td> <c:out value="${tcc.notaConvidado1}"/> </td>
                        </c:if>
                    </tr>
                    <tr>
                        <td> <c:out value="${bancaEscolhida.pessoaByConvidado2IdPessoa.nome}"/> </td>
                        <c:if test="${tcc.notaConvidado2 < 0}" var="v" scope="request">
                            <td> Não Avaliado </td>
                        </c:if>
                        <c:if test="${tcc.notaConvidado2 >= 0}" var="v" scope="request">
                            <td> <c:out value="${tcc.notaConvidado2}"/> </td>
                        </c:if>
                    </tr>
                    <c:if test="${not empty bancaEscolhida.pessoaByConvidado3IdPessoa}" var="v" scope="request">
                        <tr>
                            <td> <c:out value="${bancaEscolhida.pessoaByConvidado3IdPessoa.nome}"/> </td>
                            <c:if test="${tcc.notaConvidado3 < 0}" var="v" scope="request">
                                <td> Não Avaliado </td>
                            </c:if>
                            <c:if test="${tcc.notaConvidado3 >= 0}" var="v" scope="request">
                                <td> <c:out value="${tcc.notaConvidado3}"/> </td>
                            </c:if>
                        </tr>
                    </c:if>
                    <tr>
                        <td id="mediaFinal"> MÉDIA FINAL </td>
                        <td> <c:out value="${mediaFinal}"/> </td>
                    </tr>    
                </tbody>
            </table>

            <%
                String situacaoAluno;
                Float mediaFinal = (Float) request.getAttribute("mediaFinal");
                if (mediaFinal != null && mediaFinal > 6) {
                    situacaoAluno = "Aprovado";
                } else {
                    situacaoAluno = "Reprovado";
                }
            %>

            <div>
                <h5 class="situacao"><left><b> SITUAÇAO DO ALUNO: </b></left></h5> <h6 class="valorSituacao"><center><b> <%= situacaoAluno%> </b></center></h6>
            </div>

            <br><br>
            <h5><center>_______________________________________</center></h5>
            <h6><center> <c:out value="${bancaEscolhida.orientadorByOrientadorIdOrientador.nome}" /> </center></h6><br><br>

            <h5><center>_______________________________________</center></h5>
            <h6><center> <c:out value="${bancaEscolhida.pessoaByConvidado1IdPessoa.nome}" /> </center></h6><br><br>

            <h5><center>_______________________________________</center></h5>
            <h6><center> <c:out value="${bancaEscolhida.pessoaByConvidado2IdPessoa.nome}" /> </center></h6>

        </form>

    </body>
</html>

