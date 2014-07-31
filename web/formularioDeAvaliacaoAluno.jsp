<%-- 
    Document   : formularioDeAvaliacaoAluno
    Created on : 30/07/2014, 10:58:12
    Author     : Gean
--%>

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

        <title>Formulário de Avaliação de Aluno de TCC</title>

        <!-- Bootstrap core CSS -->
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">

    </head>
    <body>
        <h1><center>Universidade Federal do Pampa 
                <br>Campus Alegrete
                <br>Curso de Engenharia de Software</center>
        </h1>
        <h2><center><br>FORMULÁRIO DE AVALIAÇÃO DE TRABALHO DE CONCLUSÃO DE CURSO</center></h2>

        <br><br>
        <label>Aluno:</label> <input type="text" name="nomeAluno" id="nomeAluno" >
        <label>Matrícula:</label> <input type="text" name="matriculaAluno" id="matriculaAluno" ><br>
        <label>Título do Trabalho:</label> <input type="text" name="tituloTrab" id="tituloTrab" ><br>
        <label>Avaliador:</label> <input type="text" name="nomeAvaliador" id="nomeAvaliador" ><br><br><br>
        <form action="" method="post" name="formularioDeAvaliacao">
            <div id="formAvaliacao">
                <table class="table table-hover">
                    <tr>
                        <td><label>I – TRABALHO DESENVOLVIDO (4,0)</label></td>
                        <td><label>Nota</label></td>
                    </tr>
                    <tr>
                        <td><label>Relevância (1,0)<br>
                                O problema é claramente apresentado e devidamente justificado</label></td>
                        <td><input type="text" name="notaRelevancia" id="notaRelevancia" ></td>           
                    </tr>
                    <tr>
                        <td><label>Estado da Arte (1,0)<br>
                                O referêncial teórico está adequado e atualizado</label></td>
                        <td><input type="text" name="notaEstadoDaArte" id="notaEstadoDaArte" ></td>           
                    </tr>
                    <tr>
                        <td><label>Corretude técnica (1,0)<br>
                                O problema foi resolvido utilizando a metodologia adequada</label></td>
                        <td><input type="text" name="notaCorretudeTecnica" id="notaCorretudeTecnica" ></td>           
                    </tr>
                    <tr>
                        <td><label>Abrangência (1,0)<br>
                                O trabalho está completo e os seus objetivos específicos foram atingidos</label></td>
                        <td><input type="text" name="notaAbrangencia" id="notaAbrangencia" ></td>           
                    </tr>
                    <tr>
                        <td style="text-align: right"><label>Nota Parcial</label></td>
                        <td><input type="text" name="notaParcialTrabDesenvolvido" id="notaParcialTrabDesenvolvido" ></td>           
                    </tr>
                </table>
                <br><br>
                <table class="table table-hover">
                    <tr>
                        <td><label>II – APRESENTAÇÃO (3,0)</label></td>
                        <td><label>Nota</label></td>
                    </tr>
                    <tr>
                        <td><label>Clareza (1,0)<br>
                                As ideias foram expostas de maneira crítica e em consonância com o texto</label></td>
                        <td><input type="text" name="notaClarezaApresentacao" id="notaClarezaApresentacao" ></td>           
                    </tr>
                    <tr>
                        <td><label>Conhecimento (1,0)<br>
                                Na abordagem do tema foi demonstrado segurança e domínio do assunto</label></td>
                        <td><input type="text" name="notaConhecimento" id="notaConhecimento" ></td>           
                    </tr>
                    <tr>
                        <td><label>Planejamento (1,0)<br>
                                A apresentação teve sequência lógica e divisão equitativa do tempo</label></td>
                        <td><input type="text" name="notaPlanejamento" id="notaPlanejamento" ></td>           
                    </tr>
                    <tr>
                        <td style="text-align: right"><label>Nota Parcial</label></td>
                        <td><input type="text" name="notaParcialApresentacao" id="notaParcialApresentacao" ></td>           
                    </tr>
                </table>
                <br><br>
                <table class="table table-hover">
                    <tr>
                        <td><label>III – QUALIDADE DO TEXTO (3,0)</label></td>
                        <td><label>Nota</label></td>
                    </tr>
                    <tr>
                        <td><label>Clareza (1,0)<br>
                                As ideias foram expostas de maneira clara e o texto é legível</label></td>
                        <td><input type="text" name="notaClarezaQualidade" id="notaClarezaQualidade" ></td>           
                    </tr>
                    <tr>
                        <td><label>Gramática e Ortografia (1,0)<br>
                                O texto segue as regras gramaticais e ortográficas da língua portuguesa</label></td>
                        <td><input type="text" name="notaGramaticaEOrtografia" id="notaGramaticaEOrtografia" ></td>           
                    </tr>
                    <tr>
                        <td><label>Estrutura e Organização (1,0)<br>
                                O texto está bem estruturado e organizado</label></td>
                        <td><input type="text" name="notaEstruturaEOrganizacao" id="notaEstruturaEOrganizacao" ></td>           
                    </tr>
                    <tr>
                        <td style="text-align: right"><label>Nota Parcial</label></td>
                        <td><input type="text" name="notaParcialQualidade" id="notaParcialQualidade" ></td>           
                    </tr>
                </table>
                <table class="table table-hover">
                    <tr>
                        <td><label>NOTA FINAL</label></td>
                        <td><input type="text" name="notaFinal" id="notaFinal" ></td>
                    </tr>
                </table>
                <label>Alegrete, blablbla(data)</label>
            </div>
        </form>    
        <label>Comentários:</label><br>
        <textarea></textarea><br>
        <input type="submit" class="btn btn-primary" name="finalizar" id="finalizar" value="Finalizar"   />
    </body>
</html>
