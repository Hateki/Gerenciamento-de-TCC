<%-- 
    Document   : listaDePresenca
    Created on : 30/07/2014, 19:59:28
    Author     : Gean
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">

        <!-- Bootstrap core CSS -->
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">
        <title>Lista de Presença de TCC</title>
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
        <h4><center><b>LISTA DE PRESENÇA</b></center></h4>

        <div class="input-group">
            <br><br>
            <h5><left>DISCIPLINA:</left><br><br><br>
                <left>COORDENADOR:</left><br><br><br>
                <left>CURSO:</left><br><br><br>
                <left>ANO/SEMESTRE:</left><br><br><br>
                <left>APRESENTADOR:</left></h5><br>
        </div>

        <script language="javascript">
            var cont = 1;
            // Função responsável por inserir linhas na tabela
            function inserirLinhaTabela() {
                // Captura a referência da tabela com id “minhaTabela”
                var table = document.getElementById("listaDeParticipantes");
                // Captura a quantidade de linhas já existentes na tabela
                var numOfRows = table.rows.length;
                // Captura a quantidade de colunas da última linha da tabela
                var numOfCols = table.rows[numOfRows - 1].cells.length;
                // Insere uma linha no fim da tabela.
                var newRow = table.insertRow(numOfRows);
                // Faz um loop para criar as colunas

                var currentElement = document.createElement("input");
                currentElement.setAttribute("type", "text");
                //currentElement.setAttribute("name", "oInput" + id);  
                //currentElement.setAttribute("id", "oInput" + id);  

                for (var j = 0; j < numOfCols; j++) {

                    // Insere uma coluna na nova linha 
                    newCell = newRow.insertCell(j);
                    if (j === 0) {
                        newCell.innerHTML = cont;
                        cont++;
                    } else if (j === numOfCols - 1) {
                        newCell.innerHTML = ""
                    } else {
                        //Insere um conteúdo na coluna
                        newCell.innerHTML = "";

                    }

                }
            }
        </script>

        <div>
            <br>
            <table id="listaDeParticipantes" class="table table-bordered">
                <tr>
                    <td><label>Nº</label></td>
                    <td><label>Matrícula</label></td>
                    <td><label>Nome</label></td>
                    <td><label>Assinatura</label></td>
                </tr>
            </table>
            <input class="btn btn-success" type="button" name="adicionarItemTabela" id="adicionarItemTabela" value="Adicionar" onclick="inserirLinhaTabela()"> 
            <input class="btn btn-info" type="submit" name="finalizarForm" id="finalizarForm" value="Finalizar" onclick="inserirLinhaTabela()"><br><br>
        </div>
    </body>
</html>
