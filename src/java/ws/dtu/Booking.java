/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.dtu;

/**
 *
 * @author Nichlas
 */
//@XmlType(propOrder={"bookingNumber", "booked" })
public class Booking {
    String bookingNumber = "";
    boolean booked = false;

    public Booking() {
    }

    
    
    public Booking(String bookingNumber, boolean isBooked) {
        this.bookingNumber = bookingNumber;
        this.booked = isBooked;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public boolean isBooked() {
        return booked;
    }
    
    
    
    
}
