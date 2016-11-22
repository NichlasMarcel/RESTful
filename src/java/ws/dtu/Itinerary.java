/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.dtu;

import com.niceview.HotelReservation;
import java.util.ArrayList;

/**
 *
 * @author Nichlas
 */
public class Itinerary {

    ArrayList<String> flightsBookingNumbers = new ArrayList<String>();
    ArrayList<HotelReservation> hotelsReservations = new ArrayList<HotelReservation>();

    public void addFlight(String bookingNumber) {
        flightsBookingNumbers.add(bookingNumber);
    }

    public void addHotel(HotelReservation hotelReservation) {
        hotelsReservations.add(hotelReservation);
    }
}
