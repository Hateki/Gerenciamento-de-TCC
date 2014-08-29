<%@page import="br.edu.unipampa.model.Tcc"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : submeterTCC
    Created on : 17/07/2014, 11:10:14
    Author     : pontofrio
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

        <title>Submeter TCC</title>

        <!-- Bootstrap core CSS -->
        <link href="../../GerenciamentoTCC/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../../GerenciamentoTCC/bootstrap/css/styles.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            #titulo {margin-left: 500px;};
        </style>
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <a href="menuPrincipalAluno.jsp" class="navbar-brand"> Gerenciamento de TCC </a>
                <button class="navbar-toggle" data-toggle = "collapse" data-target = ".OpcoesMenu">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="collapse navbar-collapse OpcoesMenu">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Tema <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/CadastroTemaServlet"> Cadastrar Tema </a> </li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/SubmeterTCCServlet"> SubmeterTCC </a></li>
                                <li> <a href="http://localhost:8080/GerenciamentoTCC/ExibirSituacaoServlet"> Exibir Situacao Tema </a> </li>
                            </ul>
                        </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/contato.html"> Contato </a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/sobre.html"> Sobre</a> </li>
                        <li> <a href="http://localhost:8080/GerenciamentoTCC/SairSistemaServlet"> Sair</a> </li>
                    </ul>
                </div>
            </div>
        </div>
        <br><br><br>

        <div class="page-header" id="titulo">
            <h1> Envio de TCC's </h1>
            <p class="lead"> Aqui pode enviar os TCC 1, 2 e o final </p>
        </div>

        <h2><center><label>Prazo de submissão: <c:out value="${dataInicial}"/> a <c:out value="${dataFinal}"/></label></center></h2>

        <br><br>
        <c:if test="${not empty retorno}" var="v" scope="request">
            <div class="alert alert-success" role="alert"><c:out value="${retorno}"/></div>
        </c:if>
        <br>
        <div class="panel panel-primary">
            <div class="panel-heading"><h4> Situação TCC Inicial </h4></div>
            <div class="panel-body">
                <div>
                    <p>
                        Aqui pode ver a situação de seu
                        <strong> TCC da primeira fase</strong>
                    </p>
                    <br><br>
                    <div class="row row-fluid">
                        <c:if test="${not empty tccInicial}" var="v" scope="request"> 
                            <div class="col-md-3"><c:out value="${tccInicial.titulo}"/></div>
                            <div class="col-md-3">
                                <form name="download" action="DownloadTCCServlet">
                                    <button type="submit" class="bnt btn-success">
                                        Fazer Download Arquivo 
                                    </button>
                                </form>
                            </div>

                            <c:if test="${tccInicial.status == 1}" var="v" scope="request">
                                <div class="col-md-2"> Aprovado </div>
                            </c:if>

                            <c:if test="${tccInicial.status == 0}" var="v" scope="request">
                                <div class="col-md-2"> Não Aprovado </div>
                            </c:if>   

                            <c:if test="${tccInicial.status == 2}" var="v" scope="request">
                                <div class="col-md-2"> Avaliado </div>
                            </c:if>    

                            <div class="col-md-3">    
                                <form name="refazerUpload" action="SubmeterTCC2Servlet">
                                    <button type="submit" class="bnt btn-warning" name="rafazerUpload" value="0">
                                        Refazer a subimissão do Tcc
                                    </button>
                                </form>
                            </div>
                        </c:if>


                        <c:if test="${empty tccInicial}" var="v" scope="request">
                            <div class="col-md-3">.<strong>Selecione um documento para enviar</strong>:</div>
                            <div class="col-md-2">
                                <form method="post" action="SalvarTcc2Servlet" enctype="multipart/form-data">
                                    <input type="file" name="file" size="60"/><br/>
                                    <c:if test="${PrazoTccInicial == false}" var="v" scope="request">
                                        <div class="alert alert-danger" role="alert"> Você não está no prazo de Entrega </div>
                                    </c:if>
                                    <c:if test="${PrazoTccInicial}" var="v" scope="request">
                                        <input type="submit" value="Enviar" class="btn btn-success" />
                                    </c:if> 
                                </form>
                            </div>
                        </c:if>         
                    </div>
                </div>
            </div>
        </div>
        <br>
        <c:if test="${tccInicial.status == 2 && PrazoTccInicial == false}" var="v" scope="request">
            <div class="panel panel-primary">
                <div class="panel-heading"><h4> Situação TCC Final </h4></div>
                <div class="panel-body">
                    <div>
                        <p>
                            Aqui pode ver a situação de seu
                            <strong> TCC da Final</strong>
                        </p>
                        <br><br>
                        <div class="row row-fluid">
                            <c:if test="${not empty tccFinal}" var="v" scope="request"> 
                                <div class="col-md-3"><c:out value="${tccFinal.titulo}"/></div>
                                <div class="col-md-3">
                                    <form name="download" action="DownloadTCCServlet">
                                        <button type="submit" class="bnt btn-success">
                                            Fazer Download Arquivo 
                                        </button>
                                    </form>
                                </div>

                                <c:if test="${tccFinal.status == 1}" var="v" scope="request">
                                    <div class="col-md-2"> Aprovado </div>
                                </c:if>

                                <c:if test="${tccFinal.status == 0}" var="v" scope="request">
                                    <div class="col-md-2"> Não Aprovado </div>
                                </c:if>   

                                <c:if test="${tccFinal.status == 2}" var="v" scope="request">
                                    <div class="col-md-2"> Avaliado </div>
                                </c:if>    

                                <div class="col-md-3">    
                                    <form name="refazerUpload" action="SubmeterTCC2Servlet">
                                        <button type="submit" class="bnt btn-warning" name="rafazerUpload" value="0">
                                            Refazer a subimissão do Tcc
                                        </button>
                                    </form>
                                </div>
                            </c:if>


                            <c:if test="${empty tccFinal}" var="v" scope="request">
                                <div class="col-md-3">.<strong>Selecione um documento para enviar</strong>:</div>
                                <div class="col-md-2">
                                    <form method="post" action="SalvarTcc2Servlet" enctype="multipart/form-data">
                                        <input type="file" name="file" size="60"/><br/>
                                        <c:if test="${PrazoTccFinal == false}" var="v" scope="request">
                                            <div class="alert alert-danger" role="alert"> Você não está no prazo de Entrega </div>
                                        </c:if>
                                        <c:if test="${PrazoTccFinal == true}" var="v" scope="request">
                                            <input type="submit" value="Enviar" class="btn btn-success"/>
                                        </c:if> 
                                    </form>
                                </div>
                            </c:if>         
                        </div>
                    </div>
                </div>
            </div>
        </c:if>



        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
