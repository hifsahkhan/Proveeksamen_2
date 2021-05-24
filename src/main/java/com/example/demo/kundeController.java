package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class kundeController {
    Logger logger = LoggerFactory.getLogger(kundeController.class);

    @Autowired
    private JdbcTemplate db;

    @Autowired
    private HttpSession session;

    @PostMapping("/registrer")
    public void registrer(Bruker bruker){
        String regexBrukernavn = "\"[a-zA-ZæøåÆØÅ. \\\\-]{2,20}\"";
        String regexPassord = "\"[a-zA-ZæøåÆØÅ0-9]{2,20}\"";
        boolean brukernavnOK = bruker.getBrukernavn().matches(regexBrukernavn);
        boolean passordOK = bruker.getPassord().matches(regexPassord);
        if(brukernavnOK && passordOK){
            String sql = "INSERT INTO Kunde (brukernavn, passord) "+
                    "VALUES (?,?)";
            try{
                db.update(sql, bruker.getBrukernavn(), bruker.getPassord());
            }
            catch(Exception e){
                logger.error("Feil i registrering" +e);
            }
        }
    }

    @GetMapping("/hentAlle")
    public List<Bruker> bruker(){
        String sql = "SELECT * FROM Kunde";
        try{
            List<Bruker> list = db.query(sql, new BeanPropertyRowMapper(Bruker.class));
            return list;
        }
        catch(Exception e){
            logger.error("Feil i hentAlle() "+e);
            return null;
        }
    }
    /*Gitt samme HTML-skjema som i vedlegget, men nå i en ny fil. Skjemaet skal nå brukes til
    å sjekke om man kan logge inn. Det vil si at når knappen trykkes skal det kalles en metode
    på server for å sjekke om det innskrevne brukernavn og passord stemmer med de lagrede
    data i Kundetabellen. Programmer denne metoden. Det er ikke nødvendig å hash’e
passordet.*/
    @GetMapping("/logIn")
    public boolean logIn(Bruker bruker){
        String sql = "SELECT count(*) FROM Bruker WHERE brukernavn = ? AND passord = ?";
        try{
            int funnetEnBruker  = db.queryForObject(sql,Integer.class,bruker.getBrukernavn(),bruker.getPassord());
            if(funnetEnBruker > 0){
                session.setAttribute("Innlogget", bruker);
                return true;
            };
        }
        catch(Exception e){
            return false;
        }
        return false;
    }
}
// if(session.getAttribute("Innlogget")!=null){
