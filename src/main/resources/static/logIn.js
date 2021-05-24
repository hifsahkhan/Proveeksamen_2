function logIn(){
    const melding = {
        brukernavn : $("#brukernavn").val(),
        passord : $("#passord").val()
    };
    $.get("/logIn", melding, function(data){
        if(data){
            $("#feilMelding").html("Vellykket innlogging");
        }
        else{
            $("#feilMelding").html("Feil i brukernavn eller passord");
        }
    })

}