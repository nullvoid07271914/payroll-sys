
/*
Bootstable
 @description  Javascript library to make HMTL tables editable, using Bootstrap
 @version 1.1
 @autor Tito Hinostroza
*/
  "use strict";
  
  var params = null;  		
  var colsEdi =null;
  var newColHtml = '<div class="btn-group pull-right">'+
'<button id="bEdit" type="button" class="btn btn-sm btn-default" onclick="rowEdit(this);">' +
'<span class="glyphicon glyphicon-pencil" > </span>'+
'</button>'+
'<button id="bElim" type="button" class="btn btn-sm btn-default" onclick="rowElim(this);">' +
'<span class="glyphicon glyphicon-trash" > </span>'+
'</button>'+
'<button id="bAcep" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowAcep(this);">' + 
'<span class="glyphicon glyphicon-ok" > </span>'+
'</button>'+
'<button id="bCanc" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowCancel(this);">' + 
'<span class="glyphicon glyphicon-remove" > </span>'+
'</button>'+
    '</div>';
  var colEdicHtml = '<td name="buttons">'+newColHtml+'</td>'; 
    
  $.fn.SetEditable = function (options) {
    var defaults = {
        columnsEd: null,         
        $addButton: null,        
        onEdit: function() {},   
		onBeforeDelete: function() {}, 
        onDelete: function() {}, 
        onAdd: function() {}     
    };
    params = $.extend(defaults, options);
    this.find('thead tr').append('<td name="buttons"><span>Action</span></td>');  
    this.find('tbody tr').append(colEdicHtml);
	var $tabedi = this;   
    
    if (params.$addButton != null) {
        
        params.$addButton.click(function() {
            rowAddNew($tabedi.attr("id"));
        });
    }
    
    if (params.columnsEd != null) {
        
        colsEdi = params.columnsEd.split(',');
    }
  };
function IterarCamposEdit($cols, tarea) {

    var n = 0;
    $cols.each(function() {
        n++;
        if ($(this).attr('name')=='buttons') return;  
        if (!EsEditable(n-1)) return;   
        tarea($(this));
    });
    
    function EsEditable(idx) {
    
        if (colsEdi==null) {  
            return true;  
        } else {  

            for (var i = 0; i < colsEdi.length; i++) {
              if (idx == colsEdi[i]) return true;
            }
            return false;  
        }
    }
}
function FijModoNormal(but) {
    $(but).parent().find('#bAcep').hide();
    $(but).parent().find('#bCanc').hide();
    $(but).parent().find('#bEdit').show();
    $(but).parent().find('#bElim').show();
    var $row = $(but).parents('tr');  
    $row.attr('id', '');  
}
function FijModoEdit(but) {
    $(but).parent().find('#bAcep').show();
    $(but).parent().find('#bCanc').show();
    $(but).parent().find('#bEdit').hide();
    $(but).parent().find('#bElim').hide();
    var $row = $(but).parents('tr');  
    $row.attr('id', 'editing');  
}
function ModoEdicion($row) {
    if ($row.attr('id')=='editing') {
        return true;
    } else {
        return false;
    }
}
function rowAcep(but) {

    var $row = $(but).parents('tr');  
    var $cols = $row.find('td');  
    if (!ModoEdicion($row)) return;  
    
    var $count = 1;
    var working_hours = 0;
    var rate_per_day = 0;
    var gross_rate = 0;
    var cash_advanced = 0;
    var overtime = 0;
    var ut_late = 0;
    var pattern = '[0-9.]+';
    IterarCamposEdit($cols, function($td) {  
      var cont = $td.find('input').val();
      
      if ($count == 1 || $count == 2 || $count == 3) {
        if (cont != '')
            cont = toTitleCase(cont);
      }
      if ($count == 4) {
        if (cont != '') {
            rate_per_day = parseFloat(cont.match(pattern).join(''));
            cont = 'Php ' + formatMoney(rate_per_day);
        }
        else cont = 'Php 0.00';
      }
      if ($count == 5 || $count == 6 || $count == 7 || $count == 8 || $count == 9 || $count == 10) {
        if (cont != '')
            working_hours = working_hours + parseFloat(cont);
      }
      if ($count == 11) {
        cont = working_hours;
      }
      if ($count == 12) {
        rate_per_day = new BigNumber(rate_per_day);
        gross_rate = rate_per_day.dividedBy(8).times(working_hours);

        rate_per_day = parseFloat(rate_per_day.toString());
        gross_rate = parseFloat(gross_rate.toString());
        
        if (gross_rate == 0) {
            cont = 'Php 0.00';
        } else {
            cont = 'Php ' + formatMoney(gross_rate);
        }
      }
      if ($count == 13) {
        if (cont != '') {
            cont = cont.replace(',', '');
            cash_advanced = parseFloat(parseFloat(cont.match(pattern).join('')));
            cont = 'Php ' + formatMoney(cash_advanced);
        } else {
            cont = 'Php 0.00';
        }
      }
      if ($count == 14) {
        if (cont != '') {
            var ot_hours = parseFloat(cont);
            rate_per_day = new BigNumber(rate_per_day);
            gross_rate = new BigNumber(gross_rate);

            overtime = rate_per_day.dividedBy(8).times(ot_hours);
            gross_rate = gross_rate.plus(overtime);

            rate_per_day = parseFloat(rate_per_day.toString());
            overtime = parseFloat(overtime.toString());
            gross_rate = parseFloat(gross_rate.toString());
        }
      }
      if ($count == 15) {
        if (cont != '') {
            ut_late = parseFloat(parseFloat(cont.match(pattern).join('')));
            cont = 'Php ' + formatMoney(ut_late);
        } else {
            cont = 'Php 0.00';
        }
      }
      if ($count == 16) {
        cash_advanced = new BigNumber(cash_advanced);
        ut_late = new BigNumber(ut_late);
        gross_rate = new BigNumber(gross_rate);
        var deduction = cash_advanced.plus(ut_late);
        var net_pay = gross_rate.minus(deduction);

        cash_advanced = parseFloat(cash_advanced.toString());
        ut_late = parseFloat(ut_late.toString());
        gross_rate = parseFloat(gross_rate.toString());
        deduction = parseFloat(deduction.toString());
        net_pay = parseFloat(net_pay.toString());

        if (net_pay == 0) {
            cont = 'Php 0.00';
        } else {
            cont = 'Php ' + formatMoney(net_pay);
        }
      }

      $count = $count + 1;
      $td.html(cont);  
    });
    
    FijModoNormal(but);
    params.onEdit($row);
}
function rowCancel(but) {

    var $row = $(but).parents('tr');  
    var $cols = $row.find('td');  
    if (!ModoEdicion($row)) return;  
    
    IterarCamposEdit($cols, function($td) {  
        var cont = $td.find('div').html(); 
        $td.html(cont);  
    });
    FijModoNormal(but);
}
function rowEdit(but) {  
    var $row = $(but).parents('tr');  
    var $cols = $row.find('td');  
    if (ModoEdicion($row)) return;  
    

    var $count = 1;
    IterarCamposEdit($cols, function($td) {  
        var cont = $td.html(); 

        if ($count == 1 || $count == 2 || $count == 3 || $count == 11 || $count == 12 || $count == 16) {
            var div = '<div style="display: none;">' + cont + '</div>';  
            var input = '<input class="form-control input-sm" value="' + cont + '" disabled>';
            $td.html(div + input);  
        }
        else if ($count == 4 || $count == 13 || $count == 15) {
            if (cont != '') {
                var pattern = '[0-9.]+';
                cont = cont.replace(',', '');
                cont = parseFloat(cont.match(pattern).join('')).toFixed(2);
                var div = '<div style="display: none;">' + cont + '</div>';  
                var input = '<input class="form-control input-sm" value="' + cont + '">';
                $td.html(div + input);  
            } else {
                var div = '<div style="display: none;">' + cont + '</div>';  
                var input = '<input class="form-control input-sm" value="' + cont + '">';
                $td.html(div + input);  
            }
        }
        else {
            var div = '<div style="display: none;">' + cont + '</div>';  
            var input = '<input class="form-control input-sm" value="' + cont + '">';
            $td.html(div + input);  
        }

        $count = $count + 1;
    });
    FijModoEdit(but);
}
function rowElim(but) {  
    var $row = $(but).parents('tr');  
    params.onBeforeDelete($row);
    $row.remove();
    params.onDelete();
}
function rowAddNew(tabId) {
    var $tab_en_edic = $("#" + tabId);  
    var $filas = $tab_en_edic.find('tbody tr');
    if ($filas.length==0) {
        
        var $row = $tab_en_edic.find('thead tr');  
        var $cols = $row.find('th');  
        
        var htmlDat = '';
        $cols.each(function() {
            if ($(this).attr('name')=='buttons') {
                
                htmlDat = htmlDat + colEdicHtml;  
            } else {
                htmlDat = htmlDat + '<td></td>';
            }
        });
        $tab_en_edic.find('tbody').append('<tr>'+htmlDat+'</tr>');
    } else {
        
        var $ultFila = $tab_en_edic.find('tr:last');
        $ultFila.clone().appendTo($ultFila.parent());  
        $ultFila = $tab_en_edic.find('tr:last');
        var $cols = $ultFila.find('td');  
        $cols.each(function() {
            if ($(this).attr('name')=='buttons') {
                
            } else {
                $(this).html('');  
            }
        });
    }
	params.onAdd();
}
function TableToCSV(tabId, separator) {  
    var datFil = '';
    var tmp = '';
	var $tab_en_edic = $("#" + tabId);  
    $tab_en_edic.find('tbody tr').each(function() {
        
        if (ModoEdicion($(this))) {
            $(this).find('#bAcep').click();  
        }
        var $cols = $(this).find('td');  
        datFil = '';
        $cols.each(function() {
            if ($(this).attr('name')=='buttons') {
                
            } else {
                datFil = datFil + $(this).html() + separator;
            }
        });
        if (datFil!='') {
            datFil = datFil.substr(0, datFil.length-separator.length); 
        }
        tmp = tmp + datFil + '\n';
    });
    return tmp;
}
function formatMoney(amount, decimalCount = 2, decimal = ".", thousands = ",") {
  try {
    decimalCount = Math.abs(decimalCount);
    decimalCount = isNaN(decimalCount) ? 2 : decimalCount;

    const negativeSign = amount < 0 ? "-" : "";

    let i = parseInt(amount = Math.abs(Number(amount) || 0).toFixed(decimalCount)).toString();
    let j = (i.length > 3) ? i.length % 3 : 0;

    return negativeSign + (j ? i.substr(0, j) + thousands : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousands) + (decimalCount ? decimal + Math.abs(amount - i).toFixed(decimalCount).slice(2) : "");
  } catch (e) {
    console.log(e)
  }
}

function toTitleCase(str) {
    return str.replace(/\w\S*/g, function(txt){
        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
    });
}

$("#table-list").SetEditable({
        $addButton: $('#add')
    });