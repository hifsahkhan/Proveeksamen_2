

function registrer(){
    const kunde = {
        brukernavn : $("#brukernavn").val(),
        passord : $("#passord").val()
    };
    if(validerBrukernavn() && validerPassord()){
    $.post("/registrer", kunde, function () {
        hentAlle()
        })
    }
}

function validerBrukernavn(){
    const brukernavn = $("#brukernavn").val();
    const regex = /^[a-zA-ZæøåÆØÅ. \-]{4,20}$/; //regex for brukernavn
    const okBrukernavn = regex.test(brukernavn);
    if(!okBrukernavn){
        $("#feilBrukernavn").html("Feil i brukernavn - må bestå av 4-20 bokstaver");
        return false;
    }
    else{
        $("#feilBrukernavn").html("");
        return true;
    }
}

function validerPassord(){
    const passord = $("#passord").val();
    const regex = /^(?=.*[A-ZÆØÅa-zøæå])(?=.*\d)[A-ZØÆÅa-zøæå0-9\d]{8,}$/; //regex for brukernavn
    const okPassord = regex.test(passord);
    if(!okPassord){
        $("#feilPassord").html("Feil i passord - oppgi minimun 8 tegn");
        return false;
    }
    else{
        $("#feilPassord").html("");
        return true;
    }
}
function formaterData(data){
    let ut = "<table class='table table-striped table-bordered'>" +
        "<tr><th>Brukernavn</th><th>Passord</th></tr>";

    for (const i of data) {
        ut += "<tr>";
        ut += "<td>" + i.brukernavn + "</td><td>" + i.passord + "</td>";
        ut += "</tr>";
    }
    $("#alleKunder").html(ut)
}
function hentAlle(){
    $.get("/hentAlle", function (data) {
        formaterData(data);
    })
}
