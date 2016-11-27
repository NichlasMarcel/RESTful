package ws.dtu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author Nichlas
 */
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "itinerary", propOrder = {
//    "flightsBookingNumbers",
//    "hotelsBookingNumbers"
//})
//@XmlType(name = "getFlightsResponse", propOrder = {"flightsBookingNumbers","hotelsReservations"})
public class Itinerary_2 {
    public ArrayList<Booking> flightsBookingNumbers = new ArrayList<>();
    public ArrayList<Booking> hotelsReservations = new ArrayList<>();

    public void addFlight(String bookingNumber, boolean booked) {
        flightsBookingNumbers.add(new Booking(bookingNumber, booked));
    }

    public void addHotel(String bookingNumber, boolean booked) {
        hotelsReservations.add(new Booking(bookingNumber, booked));
    }

    public Boolean checkBooked() {
        ArrayList<Booking> allBookings = new ArrayList();
        allBookings.addAll(flightsBookingNumbers);
        allBookings.addAll(hotelsReservations);

        for (Booking book : allBookings) {
            if (book.isBooked()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Booking> getFlightsBookingNumbers() {
        return flightsBookingNumbers;
    }

    public ArrayList<Booking> getHotelsReservations() {
        return hotelsReservations;
    }

}
