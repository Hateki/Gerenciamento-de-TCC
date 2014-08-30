<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%-- 
    Document   : telaDownload
    Created on : 30/08/2014, 16:18:18
    Author     : Pedro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tela download</title>
    </head>
    <body>
        <% String caminho = (String) request.getSession().getAttribute("caminho"); %>
        <c:if test="${not empty retornoPositivo}" var="v" scope="request">
            <div class="alert alert-success" role="alert"><c:out value="${retornoPositivo}"/></div>
        </c:if>
        <c:if test="${not empty retornoNegativo}" var="v" scope="request">
            <div class="alert alert-danger" role="alert"><c:out value="${retornoNegativo}"/></div>
        </c:if>
        <br><br>
        <a href="http://localhost:8080/GerenciamentoTCC/<%=caminho%>"> Voltar</a>   
    </body>
</html>
