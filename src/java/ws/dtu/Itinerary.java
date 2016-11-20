/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.dtu;

import java.util.ArrayList;

/**
 *
 * @author Nichlas
 */
public class Itinerary {

    ArrayList<String> flightsBookingNumbers = new ArrayList<String>();
    ArrayList<String> hotelsBookingNumbers = new ArrayList<String>();

    public void addFlight(String bookingNumber) {
        flightsBookingNumbers.add(bookingNumber);
    }

    public void addHotel(String bookingNumber) {
        hotelsBookingNumbers.add(bookingNumber);
    }
}
