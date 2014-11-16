function trimString(sInString) {
    sInString = sInString.replace(/^\s+/g, "");
    return sInString.replace(/\s+$/g, "");
}

function trimObject(objeto) {
    var sInString = objeto.value.replace(/^\s+/g, "");
    objeto.value = sInString.replace(/\s+$/g, "");
}

function esIE() {
    if (document.all) {
        return true;
    }
    return false;
}

function esNumero(keychar) {
    var numcheck = /[0123456789]/;
    return numcheck.test(keychar);
}

function esNumeroDecimal(keychar) {
    var numcheck = /[0123456789.-]/;
    return numcheck.test(keychar);
}

function esTeclaNumero(valor) {
    var keychar = String.fromCharCode(valor);
    return esNumero(keychar);
}

function esLetra(valor) {
    if ((valor >= 64 && valor <= 90) || (valor >= 97 && valor <= 122)
        || valor == 209 || valor == 241) {
        return true;
    }
    return false;
}

function esTeclaDeControl(valor) {
    if (valor == 86 || valor < 32) {
        return true;
    }
    return false;
}

function aceptarNumerosLetras(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    return (esTeclaDeControl(key) || esTeclaNumero(key) || esLetra(key));
}

function aceptarNumeros(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    var ctrlPressed = evt.ctrlKey;
    // 45 - 46 .
    // || key == 45 || key == 46 
    return (esTeclaDeControl(key) || esTeclaNumero(key) || (ctrlPressed && (key == 118)) || (ctrlPressed && (key == 99)));
}

function aceptarNumerosNoNegativos(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    var ctrlPressed = evt.ctrlKey;
    return (esTeclaDeControl(key) || esTeclaNumero(key) 
        || (ctrlPressed && (key == 118)) || (ctrlPressed && (key == 99)));
}

function aceptarNumerosDecimales(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    var ctrlPressed = evt.ctrlKey;
    return (esTeclaDeControl(key) || esTeclaNumero(key) || key == 45
        || key == 46 || (ctrlPressed && (key == 118)) || (ctrlPressed && (key == 99)));
}

function aceptarNumerosDecimalesPositivos(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    var ctrlPressed = evt.ctrlKey;
    return (esTeclaDeControl(key) || esTeclaNumero(key) 
        || key == 46 || (ctrlPressed && (key == 118)) || (ctrlPressed && (key == 99)));
}

function aceptarCaracteresTiempo(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    return (esTeclaDeControl(key) || key == 47 || esTeclaNumero(key));
}

function aceptarLetras(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    return (esTeclaDeControl(key) || esLetra(key) || key == 32);
}

function enterPresionado(evt) {
    var key = null;
    if (esIE()) {
        key = evt.keyCode;
    } else {
        key = evt.which;
    }
    return (key == 13);
}

function stripFormat(obj, decimal) {
    if (obj.value) {
			
        var value = trimString(obj.value);
		
        if (value != null) {
            value = value.replace(/\./g, "");
            value = value.replace(/\,/g, "");
            value = value.replace(/\(/g, "");
            value = value.replace(/\)/g, "");
            var numValue = parseInt(value, 10);
            if (decimal) {
                numValue = numValue / 100;
            }
            obj.value = numValue;
        }
    }
}

function formatPasteDocument(obj) {
    var value = trimString(obj.value);
    if (value != null) {
        value = extractFirstCell(value);
        value = replaceAlphabethicalCharsDocument(value);
        value = replaceLeadingDots(value);
        value = replaceLeadingNegative(value);
        value = value.replace(/\./g, "");
        value = value.replace(/\-/g, "");
    }
    obj.value=value;
	
	
}


function stripFormatDocument(obj) {
    if (obj.value) {
			
        var value = trimString(obj.value);
		
        if (value != null) {
            value = value.replace(/\./g, "");
            value = value.replace(/\,/g, "");
            value = value.replace(/\(/g, "");
            value = value.replace(/\)/g, "");
            value = value.replace(/\-/g, "");
        }
        obj.value=value;
    }
	
}


function stripFormat6(obj) {
    if (obj.value) {

        var value = trimString(obj.value);
        if (value != null) {
            value = value.replace(/\./g, "");
            value = value.replace(/\,/g, "");
            value = value.replace(/\(/g, "");
            value = value.replace(/\)/g, "");
            var numValue = parseInt(value, 10);
            numValue = numValue / 1000000;
            obj.value = numValue;
        }
    }
}

function stripFormatPercentage(obj, decimal) {
    if (obj.value) {

        var value = trimString(obj.value);
        if (value != null) {
            value = value.replace(/\./g, "");
            value = value.replace(/\,/g, "");
            value = value.replace(/\%/g, "");
            value = value.replace(/\(/g, "");
            value = value.replace(/\)/g, "");
            var numValue = parseInt(value, 10);
            if (decimal) {
                numValue = numValue / 100;
            }
            obj.value = numValue;
        }
    }
}

function formatPercentage(obj) {
    if (obj.value) {
        formatPaste(obj, true);
        obj.value = obj.value + '%';
    }
}

function formatPastePositive(obj1, keepDecimal1) {
    formatPaste(obj1, keepDecimal1);
    var value = obj1.value;
    if ((value == "") || (value == "0")) {
        value="1";
    }
    obj1.value = value;
}

function formatPaste(obj, keepDecimal) {
    var value = obj.value;
    value = extractFirstCell(value);
    value = replaceAlphabethicalChars(value);
    value = replaceLeadingDots(value);
    value = replaceLeadingNegative(value);
	
    var anynum;
    var sign = "";
    try {
        if ((trimString(value) == "") || (!contieneNumeros(value))) {
            anynum = null;
        } else if (keepDecimal) {
            anynum = parseFloat(value);
        } else {
            anynum = parseInt(value, 10);
			
        }
    } catch (error) {
        anynum = null;
    }
    if (anynum != null) {
        var divider = 1;
        if (keepDecimal) {
            divider = 100;
        }
        var workNum = (Math.round(anynum * divider) / divider);
        var workStr = "" + workNum;

        if (workStr.indexOf(".") == -1) {
            workStr += ".";
        }

        var dStr = workStr.substr(0, workStr.indexOf("."));
        var dNum = dStr - 0;

        var pStr = workStr.substr(workStr.indexOf("."));

        if (keepDecimal) {
            while (pStr.length - 1 < 2) {
                pStr += "0";
            }
        } else {
            pStr = pStr.replace(".", "");
        }
        obj.value = dStr + pStr;
    } else {
        obj.value = "";
    }
}

function formatPaste6(obj) {
    var value = obj.value;
    value = extractFirstCell(value);
    value = replaceAlphabethicalChars(value);
    value = replaceLeadingDots(value);
    value = replaceLeadingNegative(value);
    var anynum;
    var sign = "";
    try {
        if ((trimString(value) == "") || (!contieneNumeros(value))) {
            anynum = null;
        } else {
            anynum = parseFloat(value);
        }
    } catch (error) {
        anynum = null;
    }
    if (anynum != null) {
        var divider = 1000000;
        var workNum = (Math.round(anynum * divider) / divider);
        var workStr = "" + workNum;

        if (workStr.indexOf(".") == -1) {
            workStr += ".";
        }

        var dStr = workStr.substr(0, workStr.indexOf("."));
        var dNum = dStr - 0;

        var pStr = workStr.substr(workStr.indexOf("."));

        while (pStr.length - 1 < 6) {
            pStr += "0";
        }
        obj.value = dStr + pStr;
    } else {
        obj.value = "";
    }
}

function extractFirstCell(valor) {
    result = null;
    if (valor != null) {
        var lineas = null;
        if (valor.indexOf("\n\r") != -1) {
            lineas = valor.split("\n\r");
        } else {
            lineas = valor.split("\n");
        }
        result = lineas[0].split("\t")[0];
    }
    return result;
}

/* decimal - the number of decimals after the digit from 0 to 3 */
/* -- Returns the passed number as a string in the xxx,xxx.xx format. */
function formatNumber(obj, decimal) {

    var value = obj.value;
    value = value.replace(",", "");

    var anynum;
    var sign = "";
    try {
        if (value == "") {
            anynum = null;
        } else {
            anynum = parseFloat(value);
        }
        if (anynum < 0) {
            sign = "-";
        }
    } catch (error) {
        anynum = null;
    }

    if (anynum != null) {

        var divider = 1;

        if (decimal) {
            divider = 100;
        }

        var workNum = Math.abs((Math.round(anynum * divider) / divider));

        var workStr = "" + workNum;

        if (workStr.indexOf(".") == -1) {
            workStr += ".";
        }

        var dStr = workStr.substr(0, workStr.indexOf("."));
        var dNum = dStr - 0;

        var pStr = workStr.substr(workStr.indexOf("."));

        if (decimal) {
            while (pStr.length - 1 < 2) {
                pStr += "0";
            }
        }

        if (pStr == ',')
            pStr = '';

        /* --- Adds a comma in the thousands place. */
        if (dNum >= 1000) {
            var dLen = dStr.length;
            dStr = parseInt("" + (dNum / 1000)) + ","
            + dStr.substring(dLen - 3, dLen);
        }

        /* -- Adds a comma in the millions place. */
        if (dNum >= 1000000) {
            var dLen = dStr.length;
            dStr = parseInt("" + (dNum / 1000000)) + ","
            + dStr.substring(dLen - 7, dLen);
        }

        if (dNum >= 1000000000) {
            var dLen = dStr.length;
            dStr = parseInt("" + (dNum / 1000000000)) + ","
            + dStr.substring(dLen - 11, dLen);
        }

        if (dNum >= 1000000000000) {
            var dLen = dStr.length;
            dStr = parseInt("" + (dNum / 1000000000000)) + ","
            + dStr.substring(dLen - 15, dLen);
        }

        var retval = dStr + pStr;

        /* -- Put numbers in parentheses if negative. */
        /* if (anynum < 0) { */
        /* retval = "(" + retval + ")"; */
        /* } */

        /* You could include a dollar sign in the return value. */
        /* retval = "$"+retval */

        if (!decimal) {
            retval = retval.replace(".", "");
        }

        obj.value = sign + retval;
    }
}

function replaceLeadingDots(value) {
    var result = "";
    if (value != null) {
        var firstDot = value.indexOf(".");
        var resultTmp = value.replace(/\./g, "");
        if (firstDot >= 0) {
            result = resultTmp.substring(0, firstDot) + ".";
            result = result + resultTmp.substring(firstDot);
        } else {
            result = resultTmp;
        }
    }
    return result;
}

function replaceLeadingNegative(value) {
    var result = "";
    if (value != null) {
        var firstNegative = value.indexOf("-");
        var resultTmp = value.replace(/\-/g, "");
        if (firstNegative == 0) {
            result = "-" + resultTmp;
        } else {
            result = resultTmp;
        }
    }
    return result;
}

function replaceAlphabethicalChars(value) {
    var result = "";
    if (value != null) {
        for ( var i = 0; i < value.length; i++) {
            var char = value.charAt(i);
            if (esNumeroDecimal(char)) {
                result = result + char;
            }
        }
    }
    return result;
}

function replaceAlphabethicalCharsDocument(value) {
    var result = "";
    if (value != null) {
        for ( var i = 0; i < value.length; i++) {
            var char = value.charAt(i);
            if (esNumero(char)) {
                result = result + char;
            }
        }
    }
    return result;
}



function contieneNumeros(value) {
    result = false;
    if (value != null) {
        for ( var i = 0; i < value.length; i++) {
            var char = value.charAt(i);
            result = esNumero(char);
            if (result) {
                break;
            }
        }
    }
    return result;
}

function formatTime(obj, mask) {

    var timeString = obj.value;
    var regExp;
    var maskType = mask.split(":").length;
    switch (maskType) {
        case 1:
            regExp = /^\d{1,}$/;
            break;
        case 2:
            regExp = /^\d{1,}\:\d{1,2}$/;
            break;
        case 3:
            regExp = /^\d{1,}\:\d{1,2}\:\d{1,2}$/;
            break;
    }

    if (!regExp.test(timeString)) {
        alert("El formato de ingreso no es correcto");
        obj.value = createDefValue(mask);
    }
    else {
        var timeElements = obj.value.split(":");
        if (!validateTime(timeElements)) {
            alert("El valor ingresado no es correcto");
            obj.value = createDefValue(mask);
        }
    }
}

function createDefValue(mask) {
    var defValue = mask;
    defValue = defValue.replace(/H/g, "0");
    defValue = defValue.replace(/M/g, "0");
    defValue = defValue.replace(/S/g, "0");
    return defValue;
}

function validateTime(timeElements) {
    if (timeElements.length == 1) {
        var seconds = parseInt(timeElements[0], 10);
        return (seconds >= 0);
    } else if (timeElements.length == 2) {
        var minutes = parseInt(timeElements[0], 10);
        var seconds = parseInt(timeElements[1], 10);
        return (minutes >= 0 && seconds >= 0 && seconds <= 59);
    } else if (timeElements.length == 3) {
        var hours = parseInt(timeElements[0], 10);
        var minutes = parseInt(timeElements[1], 10);
        var seconds = parseInt(timeElements[2], 10);
        return (hours >= 0 && minutes >= 0 && minutes <= 59 && seconds >= 0 && seconds <= 59);
    }
}

/* Inicio de funciones para pegar de excel */

function seteaElementoActivo(elemento) {
    if (document.elementoActivo != null) {
        document.elementoActivo.style.borderColor = document.estiloBordeNormal;
    } else {
        document.estiloBordeNormal = elemento.style.borderColor;
    }
    document.elementoActivo = elemento;
    document.elementoActivo.style.borderColor = "#0000FF";
}

function extraeBodyFuncion(declaracion) {
    var result = null;
    if (declaracion != null) {
        if ((declaracion.indexOf("{") != -1)
            && (declaracion.lastIndexOf("}") != -1)) {
            result = declaracion;
            result = result.replace(/\n\r/gi, "");
            result = result.replace(/\n/gi, "");
            result = result.replace(/\t/gi, "");
            result = result.substring(result.indexOf("{") + 1, result
                .lastIndexOf("}"));
        }
    }
    return result;
}

function inicializaPasteExcel(formulario) {
    document.elementoActivo = null;
    document.tabla = null;
    var seleccionadoElemento = false;
    if (document.forms.length > 0) {
        for ( var i = 0; i < document.forms.length; i++) {
            if (document.forms[i].id == formulario) {
                for ( var j = 0; j < document.forms[i].elements.length; j++) {
                    if (document.forms[i].elements[j].type == "text") {
                        var valorFuncion = document.forms[i].elements[j].onfocus;
                        if ((valorFuncion == null)
                            || (valorFuncion.toString().indexOf(
                                "seteaElementoActivo") == -1)) {
                            if (valorFuncion == null) {
                                valorFuncion = "seteaElementoActivo(this);";
                            } else {
                                valorFuncion = extraeBodyFuncion(valorFuncion
                                    .toString());
                                valorFuncion = "seteaElementoActivo(this);"
                                + valorFuncion;
                            }
                            document.forms[i].elements[j].onfocus = new Function(
                                valorFuncion);
                        }
                        if (!seleccionadoElemento) {
                            seteaElementoActivo(document.forms[i].elements[j]);
                            seleccionadoElemento = true;
                        }
                    }
                }
            }
        }
    }
}

function formateaClipboard(valor) {
    var lineas = null;
    if ((valor != null) && (document.elementoActivo != null)) {
        var ultimoElementoActivo = document.elementoActivo;
        var fila = document.elementoActivo.parentNode.parentNode.rowIndex;
        var columna = document.elementoActivo.parentNode.cellIndex;
        if (valor.indexOf("\n\r") != -1) {
            lineas = valor.split(/\n\r/gi);
        } else {
            lineas = valor.split(/\n/gi);
        }
        for ( var i = 0; i < lineas.length; i++) {
            /* if (lineas[i] != "") { */
            var celdas = lineas[i].split(/\t/gi);
            for ( var j = 0; j < celdas.length; j++) {
                var elementoActual = obtieneElementoTabla(fila + i, columna + j);
                if (elementoActual != null) {
                    elementoActual.focus();
                    elementoActual.value = celdas[j];
                    if(j != celdas.length -1){
                        var cuerpoFuncionOnblur = extraeBodyFuncion(elementoActual.onblur.toString());
                        cuerpoFuncionOnblur = cuerpoFuncionOnblur.replace(/this/gi,"elemento");
                        var funcionOnblur = new Function("elemento",cuerpoFuncionOnblur);
                        funcionOnblur(elementoActual);
                    }
                }
            }
        /* } */
        }
        seteaElementoActivo(ultimoElementoActivo);
    }
}

function obtieneElementoTabla(fila, columna) {
    var result = null;
    if (document.elementoActivo != null) {
        if ((document.tabla.rows.length - 1 >= fila)
            && (document.tabla.rows[fila].cells.length - 1 >= columna)) {
            var inputs = document.tabla.rows[fila].cells[columna]
            .getElementsByTagName("input");
            if (inputs != null) {
                result = inputs[0];
            }
        }
    }
    return result;
}

function pegaExcel(elementoFocoFinal, formulario, mensajeBrowserNoSoportado,
    mensajeBrowserSinPrivilegios, idTabla) {
    document.tabla = null;
    if (document.elementoActivo == null) {
        inicializaPasteExcel(formulario);
    }
    document.tabla = $(idTabla);
    if (document.elementoActivo != null) {
        var buffer = null;
        if (window.clipboardData) {
            /* IExplorer */
            buffer = window.clipboardData.getData("Text");
        } else if (navigator.userAgent.indexOf("Opera") != -1) {
            /* Opera */
            alert("Opera " + mensajeBrowserNoSoportado);
            return;
        } else if (window.netscape) {
            /* Netscape - Firefox */
            try {
                netscape.security.PrivilegeManager
                .enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert(mensajeBrowserSinPrivilegios);
                var a = window.location.pathname.split('/');
                openReportWindow("/"+a[1]+"/documentos/CONFIGURACION_DE_MOZILLA_FIREFOX.pdf");
                return;
            }
            var clip = Components.classes['@mozilla.org/widget/clipboard;1']
            .getService(Components.interfaces.nsIClipboard);
            if (!clip) {
                return;
            }
            var trans = Components.classes['@mozilla.org/widget/transferable;1']
            .createInstance(Components.interfaces.nsITransferable);
            if (!trans) {
                return;
            }
            trans.addDataFlavor('text/unicode');
            clip.getData(trans, clip.kGlobalClipboard);
            var str = new Object();
            var strLength = new Object();
            trans.getTransferData("text/unicode", str, strLength);
            if (str) {
                str = str.value
                .QueryInterface(Components.interfaces.nsISupportsString);
            }
            if (str) {
                buffer = str.data.substring(0, strLength.value / 2);
            }
        } else {
            alert(mensajeBrowserNoSoportado);
            return;
        }
        formateaClipboard(buffer);
        if (elementoFocoFinal != null) {
            elementoFocoFinal.focus();
        }
    }
}

/* Fin de funciones para pegar de excel */

function toggleCheck(control, formName, id) {
    var formObject = document.getElementById(formName);
    var stop = control.id.indexOf('::' + id);
    var newId = control.id.substring(0, stop);
    if (formObject) {
        var elements = formObject.elements;
        for (i = 0; i < elements.length; i++) {
            var element = elements[i];
            if (element.type == 'checkbox') {
                if (element.id != null && element.id.indexOf(newId + ':') != -1) {
                    element.checked = control.checked;
                }
            }
        }
        toggleUncheckParent(newId, id);
        if (control.checked) {
            toggleCheckParent(newId, elements, id);
        }
    }
}

function toggleUncheckParent(newId, id) {
    var strSplit = newId.split(':');
    var parentId = '';
    if (strSplit.length > 3) {
        for (i = 0; i < strSplit.length - 1; i++) {
            parentId += strSplit[i] + ':';
        }
        var checkbox = document.getElementById(parentId + ':' + id);
        if (checkbox != null) {
            checkbox.checked = false;
        }
        toggleUncheckParent(parentId.substring(0, parentId.length - 1), id);
    }
}

function toggleCheckParent(newId, elements, id) {
    var isOk = true;
    var strSplit = newId.split(':');
    var parentId = '';
    if (strSplit.length > 3) {
        for (i = 0; i < strSplit.length - 1; i++) {
            parentId += strSplit[i] + ':';
        }
        for (i = 0; i < elements.length; i++) {
            var element = elements[i];
            if (element.type == 'checkbox') {
                if (element.id != null && element.id.indexOf(parentId) != -1
                    && element.id != parentId + ':' + id) {
                    if (!element.checked) {
                        isOk = false;
                        break;
                    }
                }
            }
        }

        var checkbox = document.getElementById(parentId + ':' + id);
        if (checkbox != null) {
            checkbox.checked = isOk;
        }
        toggleCheckParent(parentId.substring(0, parentId.length - 1), elements,
            id);
    }

}

function toggleAll(control, formName) {
    var formObject = document.getElementById(formName);
    var newId = control.id;
    var strSplit = newId.split(':');
    newId = strSplit[strSplit.length - 1];

    if (formObject) {
        var elements = formObject.elements;
        for (i = 0; i < elements.length; i++) {
            var element = elements[i];
            if (element.type == 'checkbox') {
                if (element.id != null && element.id.indexOf(newId) != -1) {
                    element.checked = control.checked;
                }
            }
        }
    }
}

function postData(objetoWizard) {
    var boton = document.getElementById(objetoWizard);
    boton.onclick();
}
function clickeaLinkHeader(boton, idSource, idLinkHeader) {
    var idImageLink=boton.id.replace(":"+idSource,":"+idLinkHeader);
    postData(idImageLink);
    return false;
}

function toUpperCase(object) {
    try {
        if (object) {
            var value = object.value;
            if (value) {
                object.value = value.toUpperCase();
            }
        }
    } catch (exception) {
    }
}

 function handleNuevoCronogramaRequest(xhr, status, args) {  
        if(args.validationFailed || !args.loggedIn) {  
            jQuery('#dialogoNuevoCronograma').effect("shake", { times:3 }, 100);  
        } else {  
            dlgNuevoCronograma.hide();  
            jQuery('#btnNuevoCronograma').fadeOut();  
        }  
    }  
    
    function handleEditarCronogramaRequest(xhr, status, args) {  
        if(args.validationFailed || !args.loggedIn) {  
            jQuery('#dialogoEditarCronograma').effect("shake", { times:3 }, 100);  
        } else {  
            dlgEditarCronograma.hide();  
            jQuery('#cbEditar').fadeOut();  
        }  
    }