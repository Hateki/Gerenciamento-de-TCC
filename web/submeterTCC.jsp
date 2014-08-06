<%-- 
    Document   : envioTCC
    Created on : 21/07/2014, 15:18:37
    Author     : Kezia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap.min.css" rel="stylesheet" media="screen">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

        <h1>Envio do TCC</h1>
    </form>
    <form action="upload" method="post" name="form" enctype="multipart/form-data">
        Envie um Arquivo: <input type="file" name="arquivo" /><br />
        <div style="display: none;"><input name="contextid" type="hidden" value="141359" />
            <input name="userid" type="hidden" value="8139" />
            <input name="action" type="hidden" value="uploadfile" />
            <input name="sesskey" type="hidden" value="QUjkgr84U6" />
        </div>
        <fieldset class="hidden"><div>
                <div id="fitem_id_assignment_file" class="fitem required fitem_ffilepicker ">
                    <div id="filepicker-wrapper-53dfc2a476320" class="mdl-left" style="display:none">
                        <div>
                            <input type="button" class="fp-btn-choose" id="filepicker-button-53dfc2a476320" value="Escolha um arquivo..." name="assignment_filechoose"/>
                            <span> Tamanho máximo para novos arquivos: 20Mb </span>
                        </div>    <div id="file_info_53dfc2a476320" class="mdl-left filepicker-filelist" style="position: relative">
                            <div class="filepicker-filename">
                                <div class="dndupload-progressbars"></div>
                            </div>
                            </fieldset>
                            <fieldset class="hidden"><div>
                                    <div id="fgroup_id_buttonar" class="fitem fitem_actionbuttons fitem_fgroup"><div class="felement fgroup"><input name="submitbutton" value="Salvar mudanças" type="submit" id="id_submitbutton" /> <input name="cancel" value="Cancelar" type="submit" onclick="skipClientValidation = true;
                                            return true;" class=" btn-cancel" id="id_cancel" /></div></div>

                                </div></fieldset>
                            </form></div></div> 
                    </body>
                    <script src="http://code.jquery.com/jquery-latest.js"></script>
                    <script src="bootstrap.min.js"></script>
                    </body>
                    </html>
