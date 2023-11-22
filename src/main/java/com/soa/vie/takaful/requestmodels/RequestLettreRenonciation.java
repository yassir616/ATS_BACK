package com.soa.vie.takaful.requestmodels;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLettreRenonciation {

    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date date = new Date();*/
    private String date;
    private String ville;
    private String motif;
    private String nomParticipant;
    private String adresse;
    private String cin;

}
