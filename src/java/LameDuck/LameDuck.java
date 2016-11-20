/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LameDuck;

import com.dtu.mmmngg.CreditCardFaultMessage;
import com.dtu.mmmngg.MainWebService;
import javax.jws.WebService;

/**
 *
 * @author Nichlas
 */
@WebService(serviceName = "MainWebService", portName = "LameDuckWebServicePort", endpointInterface = "com.dtu.mmmngg.LameDuckWebService", targetNamespace = "http://MMMNGG.dtu.com/", wsdlLocation = "WEB-INF/wsdl/LameDuck/Nichlas-PC_8080/LameDuck/MainWebService.wsdl")
public class LameDuck {

    MainWebService lameDuck = new MainWebService();

    public java.lang.String hello(java.lang.String name) {
        //TODO implement this method
        return lameDuck.getLameDuckWebServicePort().hello(name);
    }

    public java.util.List<com.dtu.mmmngg.FlightInfoObject> getFlights(java.lang.String from, java.lang.String toDestination, javax.xml.datatype.XMLGregorianCalendar arg2) {
        //TODO implement this method
        return lameDuck.getLameDuckWebServicePort().getFlights(from, toDestination, arg2);

    }

    public boolean bookFlights(java.lang.String bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard) throws com.dtu.mmmngg.CreditCardFaultMessage {
        //TODO implement this method
        return lameDuck.getLameDuckWebServicePort().bookFlights(bookingNumber, creditCard);

    }

    public boolean cancelFlights(java.lang.String bookingnumber, int amount, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard) throws com.dtu.mmmngg.CreditCardFaultMessage {
        //TODO implement this method
        return lameDuck.getLameDuckWebServicePort().cancelFlights(bookingnumber, amount, creditCard);
    }
    
}
