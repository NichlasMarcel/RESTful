package ws.dtu;

import LameDuck.LameDuck;
import NiceView.NiceView;
import com.dtu.mmmngg.FlightInfoObject;

import com.niceview.Hotel;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nichlas
 */
public class TravelGood {

    //static private ArrayList<Itinerary> itineraries = new ArrayList<Itinerary>();
    private static final NiceView niceView = new NiceView();
    private static final LameDuck lameDuck = new LameDuck();
    private static final CreditCardInfoType creditcard = new CreditCardInfoType();

    private static final Map<Long, Itinerary> itineraries = new HashMap<>();

    public TravelGood() {
        creditcard.setName("Anne Strandberg");
        creditcard.setNumber("50408816");
        CreditCardInfoType.ExpirationDate exp = new CreditCardInfoType.ExpirationDate();
        exp.setMonth(5);
        exp.setYear(9);
        creditcard.setExpirationDate(exp);
        //itineraries.put((long) 1, new Itinerary());
    }

    @Path("TravelGood/hotels")
    public static class TravelGoodHotels {

        @GET
        @Produces({MediaType.APPLICATION_JSON})
        public List<Hotel> getHotels(
                @QueryParam("city") String city,
                @QueryParam("arrival") String arrival,
                @QueryParam("departure") String departure) {

            SimpleDateFormat converter = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date arrival_date = converter.parse(arrival);
                Date departure_date = converter.parse(departure);

                GregorianCalendar c = new GregorianCalendar();
                GregorianCalendar c2 = new GregorianCalendar();

                c.setTime(arrival_date);
                c2.setTime(departure_date);
                XMLGregorianCalendar arrival_date_xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                XMLGregorianCalendar departure_date_xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);

                return niceView.getHotels(city, arrival_date_xml, departure_date_xml);

            } catch (Exception e) {

            }
            return new ArrayList<Hotel>();
        }
    }

    @Path("TravelGood/flights")
    public static class TravelGoodFlights {

        @GET
        @Produces({MediaType.APPLICATION_JSON})
        public List<FlightInfoObject> getFlights(
                @QueryParam("from") String from,
                @QueryParam("to") String to,
                @QueryParam("date") String date) {

            SimpleDateFormat converter = new SimpleDateFormat("dd/MM/yyyy");
            String exceptionMessage = "";
            try {
                Date departure_date = converter.parse(date);

                GregorianCalendar c = new GregorianCalendar();

                c.setTime(departure_date);

                XMLGregorianCalendar departure_date_xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

                return lameDuck.getFlights(from, to, departure_date_xml);
            } catch (Exception e) {
                System.out.println(e);
                exceptionMessage = e.getMessage();
            }
//            ArrayList<FlightInfoObject> fuck = new ArrayList<>();
//            FlightInfoObject flight = new FlightInfoObject();
//            flight.setBookingNumber(exceptionMessage);
//            fuck.add(flight);
//            return fuck;
            return new ArrayList<FlightInfoObject>();
        }
    }
    
//    @Path("TravelGood/{id}/book")
//    public static class TravelGoodItinerariesBook {
//        @GET
//        @Produces("application/json")
//        private Response bookItinerary(@PathParam("id") long id) {
//              System.out.println(id);
//              return Response.ok().build();
//        }
//    }

    @Path("TravelGood/itineraries")
    public static class TravelGoodItineraries {

        @GET
        @Produces({MediaType.APPLICATION_JSON})
        public Map<Long, Itinerary> getAllItineraries() {
            return itineraries;
        }

        @POST
        @Consumes("application/json")
        @Produces("application/json")
        public Response createItinerary(Itinerary i) {
            itineraries.put((long) itineraries.keySet().size(), i);
            return Response.created(URI.create("itineraries/" + (itineraries.keySet().size() - 1))).build();
        }

        @GET
        @Path("/{id}")
        @Produces("application/json")
        public Response getItinerary(@PathParam("id") long id) {
            if (!itineraries.containsKey(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(itineraries.get(id)).build();
        }
        
        @POST
        @Path("/{id}/book")
        @Consumes("text/plain")
        @Produces("application/json")
        public Response bookItinerary(@PathParam("id") long id, String text) {
            return Response.ok(itineraries.get(id)).build();
        }
        
//        @GET
//        @Path("/{id}/book")
//        @Produces("application/json")
//        private Response bookItinerary(@PathParam("id") long id) {
//              System.out.println(id);
//              return Response.ok().build();
////            Itinerary itinerary = itineraries.get(id);
////            boolean somethingFailed = false;
////            // Book all hotels
////            for (String bookingHotel : itinerary.hotelsBookingNumbers) {
////                try {
////                    niceView.bookHotel(Integer.parseInt(bookingHotel), creditcard);
////                } catch (Exception e) {
////                    somethingFailed = true;
////                }
////
////            }
////
////            // Book all flights
////            for (String bookingFlight : itinerary.flightsBookingNumbers) {
////                try {
////                    lameDuck.bookFlights("" + bookingFlight, creditcard);
////                } catch (Exception e) {
////                    somethingFailed = true;
////                }
////            }
////
////            if (somethingFailed) {
////                cancelItinerary(itinerary);
////                return Response.status(Response.Status.CONFLICT).build();
////            }
////            return Response.accepted().build();
//        }

        @POST
        @Consumes("application/json")
        @Produces("application/json")
        private Response cancelItinerary(Itinerary itinerary) {
            boolean success = true;
            for (String bookingHotel : itinerary.hotelsBookingNumbers) {
                try {
                    niceView.cancelHotel(Integer.parseInt(bookingHotel));
                } catch (Exception e) {
                    System.out.println("Something fucked up when cancelling");
                    success = false;
                }

            }

            // Book all flights
            for (String bookingFlight : itinerary.flightsBookingNumbers) {
                try {
                    // Skal ændres
                    lameDuck.cancelFlights("" + bookingFlight, 100, creditcard);
                } catch (Exception e) {
                    System.out.println("Something fucked up when cancelling");
                    success = false;
                }
            }
            
            if(!success)
                return Response.status(Response.Status.CONFLICT).build();
            
            return Response.accepted().build();
        }

        @POST
        @Path("/{id}/hotels")
        @Consumes("text/plain")
        @Produces("application/json")
        public Response addHotel(
                @PathParam("id") long id,
                String bookingNumber) {

            if (!itineraries.containsKey(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Itinerary itinerary = itineraries.get(id);
            itinerary.addHotel(bookingNumber);

//            return Response.ok().build();
            return Response.created(URI.create("itineraries/" + id + "/hotels/"
                    + bookingNumber)).build();
        }

        @POST
        @Path("/{id}/flights")
        @Consumes("text/plain")
        @Produces("application/json")
        public Response addFlight(
                @PathParam("id") long id,
                int bookingNumber) {

            if (!itineraries.containsKey(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Itinerary itinerary = itineraries.get(id);
            itinerary.addFlight("" + bookingNumber);
            return Response.created(URI.create("itineraries/" + id + "/flights/"
                    + bookingNumber)).build();
        }

        @DELETE
        @Path("/{id}/hotels/{bookingNumber}")
        public Response removeHotel(
                @PathParam("id") long id,
                @PathParam("bookingNumber") String bookingNumber) {
            // Check that the itinerary is there
            if (!itineraries.containsKey(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Itinerary itinerary = itineraries.get(id);
            // Find the requested hotel
            if (itinerary.hotelsBookingNumbers.contains(bookingNumber)) {
                itinerary.hotelsBookingNumbers.remove(bookingNumber);
                return Response.accepted().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();

            }
        }

        @DELETE
        @Path("/{id}/flights/{bookingNumber}")
        public Response removeFlight(
                @PathParam("id") long id,
                @PathParam("bookingNumber") String bookingNumber) {
            // Check that the itinerary is there
            if (!itineraries.containsKey(id)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Itinerary itinerary = itineraries.get(id);
            // Find the requested hotel
            if (itinerary.flightsBookingNumbers.contains(bookingNumber)) {
                itinerary.flightsBookingNumbers.remove(bookingNumber);
                return Response.accepted().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

    }
}
