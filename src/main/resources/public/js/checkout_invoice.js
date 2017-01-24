var doc = new jsPDF();
var specialElementHandlers = {
    '#editor': function (element, renderer) {
        return true;
    }
};

$(function () {
   var uid = getCurrentUrlParam("uid");
   var body = $('#toPdf');
   if (checkInvoice()) {
       retrieveJSON("/invoice.json", { "uid" : uid }, fillData);
       var pdfButton = $('#pdfEr');
       pdfButton.removeAttr("hidden");
       pdfButton.click(function () {
           doc.fromHTML(body.html(), 15, 15, {
               'width': 170,
               'elementHandlers': specialElementHandlers
           });
           doc.save(uid + '.pdf');
       });
   } else {
       body.empty();
       body.append("Uw factuur kon niet worden gevonden, " +
           "neem aub contact op met de sitebeheerder.");
   }
});

function checkInvoice() {
    var found = false;
    return found
}

function fillData(data) {

}