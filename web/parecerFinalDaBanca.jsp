<%-- 
    Document   : parecerFinalDaBanca
    Created on : 30/07/2014, 19:59:56
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

        <title>Parecer Final da Banca</title>

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
        <h4><center><b>PARECER FINAL DA BANCA</b></center></h4>

        <div>
            <br>
            <style>
                .aluno { display:inline; }
                .valorAluno { display:inline; }

                .matricula { display:inline; }
                .valorMatricula { display:inline; } 

                .titulo { display:inline; }
                .valorTitulo { display:inline; }
                
                .banca { display:inline; }
                .orientador { display:inline; }
                
                .notafinal { display:inline; }
                .valorNota { display:inline; }
                
                .situacao { display:inline; }
                .valorSituacao { display:inline; }
                
                .data  { display:inline;
                         float: right}
                       
                

            </style>
            <div>
                <h5 class="aluno"><left><b> ALUNO: </b></left></h5> <h6 class="valorAluno"><left> <c:out value="${bancaEscolhida.aluno.nome}"/> </left></h6>
                <h5 class="data"><right><b> DATA: </b></right></h5>
            </div>
            <div>
                <h5 class="matricula"><left><b> MATRÍCULA: </b></left></h5> <h6 class="valorMatricula"><left> <c:out value="${bancaEscolhida.aluno.matricula}"/> </left></h6>
            </div>
            <div>
                <h5 class="titulo"><left><b> TÍTULO DO TRABALHO: </b></left></h5> <h6 class="valorTitulo"><left> <c:out value="${tema.descricao}"/> </left></h6><br>
            </div>
        </div>
            <br><br>
            
        <form method="post">
            <div> 
            <h5 class="banca"><left><b> BANCA AVALIADORA: </b></left></h5>

            <h6 class="orientador"><center><i> <c:out value="${bancaEscolhida.orientadorByOrientadorIdOrientador.nome}"/> </i></center></h6> <center>(Orientador)</center> <br>
            </div>
            
            <h6><center><i> <c:out value="${bancaEscolhida.pessoaByConvidado1IdPessoa.nome}"/> </i></center></h6><br>
            <h6><center><i> <c:out value="${bancaEscolhida.pessoaByConvidado2IdPessoa.nome}"/> </i></center></h6><br>
            <h6><center><i> <c:out value="${bancaEscolhida.pessoaByConvidado3IdPessoa.nome}"/> </i></center></h6>

            <%
                String situacaoAluno;
                Float mediaFinal = (Float) request.getAttribute("mediaFinal");
                if (mediaFinal != null && mediaFinal > 6) {
                    situacaoAluno = "Aprovado";
                } else {
                    situacaoAluno = "Reprovado";
                }
            %>

            <c:if test="${mediaFinal > 0}" var="v" scope="request">
                <h5 class="notafinal"><left><b> NOTA FINAL DO TRABALHO DE CONCLUSÃO DE CURSO: </b></left></h5>
                <h6 class="valorNota"><center> <c:out value="${mediaFinal}" /> </center></h6><br>
                    </c:if>
                    <c:if test="${mediaFinal < 0}" var="v" scope="request">
                <h5><center> Nem todos os avaliadores deram a nota! </center></h5><br>
                    </c:if>

                
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
