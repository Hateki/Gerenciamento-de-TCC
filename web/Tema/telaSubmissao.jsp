<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : telaSubmissao
    Created on : 01/09/2014, 16:59:56
    Author     : Pedro
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
    </head>
    <body>
        <c:if test="${not empty retornoPositivo}" var="v" scope="request">
            <div class="alert alert-success" role="alert"><c:out value="${retornoPositivo}"/></div>
        </c:if>
        <c:if test="${not empty retornoNegativo}" var="v" scope="request">
            <div class="alert alert-danger" role="alert"><c:out value="${retornoNegativo}"/></div>
        </c:if>
        <br><br>
        <form action="<c:out value="${caminho}"/>">
            <center>
                <button type="submit" class="bnt btn-warning">
                    Continuar
                </button>
            </center>
        </form> 
            
            <script>
                history.forward();
            </script>    
        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="../../GerenciamentoTCC/bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>

