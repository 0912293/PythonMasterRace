var doc = new jsPDF();
var specialElementHandlers = {
    '#editor': function (element, renderer) {
        return true;
    }
};

$('#cmd').click(function () {
    doc.fromHTML($('#toPdf').html(), 15, 15, {
        'width': 170,
        'elementHandlers': specialElementHandlers
    });
    doc.save(getCurrentUrlParam("uid") + '.pdf');
});

$(function () {
   var uid = getCurrentUrlParam("uid");
   if (checkInvoice()) {
       retrieveJSON("/invoice.json", { "uid" : uid }, fillData)
   } else {
       $('#inner').append("Uw factuur kon niet worden gevonden, " +
           "neem aub contact op met de sitebeheerder.");
   }
});

function checkInvoice() {
    var found = false;
    return found
}

function fillData(data) {

}