/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.dtu;

import com.niceview.HotelReservation;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 *
 * @author Nichlas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getHotelsResponse", propOrder = {
    "list"
})
public class GetHotelsResponse {
    ArrayList<HotelReservation> list = new ArrayList<>();

    public GetHotelsResponse() {
    }
    
    public GetHotelsResponse(ArrayList<HotelReservation> list) {
        this.list = list;
    }

    public ArrayList<HotelReservation> getList() {
        return list;
    }
    
    
}
