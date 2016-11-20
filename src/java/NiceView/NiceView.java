/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NiceView;

import com.niceview.Exception_Exception;
import com.niceview.NiceViewService;
import javax.jws.WebService;

/**
 *
 * @author Nichlas
 */
@WebService(serviceName = "NiceViewService", portName = "NiceViewPort", endpointInterface = "com.niceview.NiceView", targetNamespace = "http://NiceView.com/", wsdlLocation = "WEB-INF/wsdl/NewWebServiceFromWSDL/Nichlas-PC_8080/NiceView/NiceViewService.wsdl")
public class NiceView {

    NiceViewService niceView = new NiceViewService();

    public java.util.List<com.niceview.Hotel> getHotels(java.lang.String city, javax.xml.datatype.XMLGregorianCalendar arrival, javax.xml.datatype.XMLGregorianCalendar departure) {
        //TODO implement this method
        return niceView.getNiceViewPort().getHotels(city, arrival, departure);        
    }

    public java.lang.Boolean bookHotel(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard) throws Exception_Exception {
        //TODO implement this method
        return niceView.getNiceViewPort().bookHotel(bookingNumber, creditcard);

    }

    public java.lang.Boolean cancelHotel(int bookingNumber) throws Exception_Exception {
        //TODO implement this method
        return niceView.getNiceViewPort().cancelHotel(bookingNumber);

    }
    
}
