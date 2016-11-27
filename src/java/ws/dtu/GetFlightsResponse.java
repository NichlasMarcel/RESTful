/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.dtu;

import com.dtu.mmmngg.FlightInfoObject;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Nichlas
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFlightsResponse", propOrder = {
    "list"
})
public class GetFlightsResponse {
    ArrayList<FlightInfoObject> list = new ArrayList<>();

    public GetFlightsResponse() {
    }

    
    public GetFlightsResponse(ArrayList<FlightInfoObject> list) {
        this.list = list;
    }

    public ArrayList<FlightInfoObject> getList() {
        return list;
    }
    
    
    
}
