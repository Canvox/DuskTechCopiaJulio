package net.dusktech.com.dusktechcopiajulio;

import java.util.ArrayList;

/**
 * Created by INTEL-COREi7 on 28/7/2017.
 */

public class UserInformation {

    //public String score;
    public ArrayList scores = new ArrayList();
    public ArrayList informacion = new ArrayList();

    /*public UserInformation(){

    }*/

    /*public UserInformation(String score) {
        this.score = score;
    }

    public UserInformation(ArrayList scores) {
        this.scores = scores;
    }*/

    public UserInformation(ArrayList scores, ArrayList informacion) {
        this.scores = scores;
        this.informacion = informacion;
    }
}
