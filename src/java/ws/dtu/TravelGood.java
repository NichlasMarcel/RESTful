package ws.dtu;

import com.dtu.mmmngg.CreditCardFaultMessage;
import com.dtu.mmmngg.FlightInfoObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niceview.Exception_Exception;
import com.niceview.HotelReservation;
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
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    private static final ArrayList<Itinerary_2> itineraries = new ArrayList<>();

//    private static ArrayList<FlightBooked> booked_flights = new ArrayList();
//    private static ArrayList<HotelBooked> booked_hotels = new ArrayList();
    private static final ArrayList<Booking> booked_flights = new ArrayList<>();
    private static final ArrayList<Booking> booked_hotels = new ArrayList<>();

    public TravelGood() {

        Itinerary_2 i = new Itinerary_2();
        i.flightsBookingNumbers.add(new Booking("safasdfa", true));
        itineraries.add(i);
    }



    @Path("TravelGood/hotels")
    public static class TravelGoodHotels {

        @GET
        @Produces({MediaType.APPLICATION_JSON})
        public GetHotelsResponse getHotelsREST(
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

                GetHotelsResponse response = new GetHotelsResponse();
                response.list.addAll(getHotels(city, arrival_date_xml, departure_date_xml));
                return response;

            } catch (Exception e) {

            }
            return new GetHotelsResponse();
        }
    }

    @Path("TravelGood/flights")
    public static class TravelGoodFlights {

        @GET
        @Produces({MediaType.APPLICATION_JSON})
        public GetFlightsResponse getFlightsREST(
                @QueryParam("from") String from,
                @QueryParam("to") String to,
                @QueryParam("date") String date) {

            SimpleDateFormat converter = new SimpleDateFormat("dd/MM/yyyy");
            String exceptionMessage = "";
            try {
                Date date_obj = converter.parse(date);

                GregorianCalendar c = new GregorianCalendar();

                c.setTime(date_obj);

                XMLGregorianCalendar date_xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                //return new GetFlightsResponse(test);
                GetFlightsResponse response = new GetFlightsResponse();
                response.list.addAll(getFlights(from, to, date_xml));
                return response;
            } catch (Exception e) {
                System.out.println(e);
                exceptionMessage = e.getMessage();
            }
//            ArrayList<FlightInfoObject> fuck = new ArrayList<>();
//            FlightInfoObject flight = new FlightInfoObject();
//            flight.setBookingNumber(exceptionMessage);
//            fuck.add(flight);
//            return fuck;
            return new GetFlightsResponse();
        }
    }

    @Path("TravelGood/itineraries")
    public static class TravelGoodItineraries {

        @POST
        @Consumes({MediaType.APPLICATION_JSON})
        @Produces({MediaType.APPLICATION_JSON})
        public Response createItineraryREST(String json) {
            try {

                ObjectMapper mapper = new ObjectMapper();
                Itinerary_2 i = mapper.readValue(json, Itinerary_2.class);
                itineraries.add(i);
                if (itineraries.contains(i)) {
                    return Response.created(URI.create("TravelGood/itineraries/" + (itineraries.size() - 1))).build();
                } else {
                    return Response.notModified().build();
                }
            } catch (Exception e) {
                return Response.notModified().build();

            }
        }

        @Path("/{id}")
        @GET
        @Produces({MediaType.APPLICATION_JSON})
        public String getItineraryREST(@PathParam("id") int id) {
            Itinerary_2 i;
            if (itineraries.size() <= id) {
                return "{}";
            } else {
                i = itineraries.get(id);
                ObjectMapper m = new ObjectMapper();
                try {
                    return m.writeValueAsString(i);
                } catch (Exception e) {
                    return "{}";
                }
            }
        }

        @Path("/{id}")
        @DELETE
        public Response deleteItinerary(@PathParam("id") int id) {
            if (itineraries.size() <= id) {
                return Response.status(Response.Status.CONFLICT).build();
            }

            itineraries.remove(id);
            return Response.accepted().build();
        }

        @Path("/{id}/book")
        @POST
        @Consumes({MediaType.APPLICATION_JSON})
        @Produces({MediaType.APPLICATION_JSON})
        public Response bookItineraryREST(@PathParam("id") int id, CreditCardInfoType creditCard) {
            Itinerary_2 itinerary = itineraries.get(id);
            CreditCardInfoType creditcard = creditCard;
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                creditcard = mapper.readValue(creditCard, CreditCardInfoType.class);
//
//            } catch (Exception e) {
//                return Response.status(Response.Status.CONFLICT).build();
//
//            }
            boolean somethingFailed = false;
            // Book all hotels
            for (Booking bookingHotel : itinerary.hotelsReservations) {
                try {
                    bookHotel(Integer.parseInt(bookingHotel.getBookingNumber()), creditcard);
                    bookingHotel.booked = true;
                    booked_hotels.add(bookingHotel);
                } catch (Exception e) {
                    somethingFailed = true;
                }

            }

            // Book all flights
            for (Booking bookingFlight : itinerary.flightsBookingNumbers) {
                try {
                    bookFlights("" + bookingFlight, creditcard);
                    bookingFlight.booked = true;
                    booked_flights.add(bookingFlight);
                } catch (Exception e) {
                    somethingFailed = true;
                }
            }

            if (somethingFailed) {
                cancelItineraryREST(id, creditcard);
                return Response.status(Response.Status.CONFLICT).build();
            }
            return Response.accepted().build();
        }

        @POST
        @Path("/{id}/cancelBook")
        @Consumes({MediaType.APPLICATION_JSON})
        @Produces({MediaType.APPLICATION_JSON})
        public Response cancelItineraryREST(@PathParam("id") int id, CreditCardInfoType creditCard) {
          

            boolean success = true;
            CreditCardInfoType creditcard = creditCard;

            Itinerary_2 i = itineraries.get(id);

            for (Booking bookingHotel : i.hotelsReservations) {
                try {
                    if(bookingHotel.getBookingNumber().equals("1"))
                        throw new Exception();
                                
                    bookingHotel.booked = false;
                    booked_hotels.remove(bookingHotel);
                    cancelHotel(Integer.parseInt(bookingHotel.getBookingNumber()));
                    
                } catch (Exception e) {
                    System.out.println("Something fucked up when cancelling");
                    success = false;
                }

            }

            // Book all flights
            for (Booking bookingFlight : i.flightsBookingNumbers) {
                try {
                    // Skal ændres
                    bookingFlight.booked = false;
                    booked_flights.remove(bookingFlight);
                    cancelFlights(bookingFlight.getBookingNumber(), 100, creditcard);
                } catch (Exception e) {
                    System.out.println("Something fucked up when cancelling");
                    success = false;
                }
            }

            if (!success) {
                return Response.status(Response.Status.CONFLICT).build();
            }

            return Response.accepted().build();
        }

        @POST
        @Path("/{id}/hotels")
        @Consumes("application/json")
        @Produces("application/json")
        public Response addHotelREST(
                @PathParam("id") int id,
                HotelReservation hotelReservation) {

            if (itineraries.size() - 1 < id) {
                return Response.status(Response.Status.CONFLICT).build();
            }

            for (Booking book : booked_hotels) {
                if (book.bookingNumber.equals(hotelReservation.getBookingNumber())) {
                    return Response.status(Response.Status.NOT_ACCEPTABLE).build();
                }
            }

            Itinerary_2 itinerary = itineraries.get(id);
            itinerary.addHotel("" + hotelReservation.getBookingNumber(), false);

//            return Response.ok().build();
            return Response.created(URI.create("itineraries/" + id + "/hotels/"
                    + hotelReservation)).build();
        }

        @POST
        @Path("/{id}/flights")
        @Consumes("application/json")
        @Produces("application/json")
        public Response addFlightREST(
                @PathParam("id") int id,
                FlightInfoObject flight) {

            if (itineraries.size() - 1 < id) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            for (Booking book : booked_flights) {
                if (book.getBookingNumber().equals(flight.getBookingNumber())) {
                    return Response.status(Response.Status.NOT_ACCEPTABLE).build();
                }
            }
            Itinerary_2 itinerary = itineraries.get(id);
            itinerary.addFlight(flight.getBookingNumber(), false);

            return Response.created(URI.create("itineraries/" + id + "/flights/"
                    + flight)).build();
        }

        @DELETE
        @Path("/{id}/hotels/{bookingNumber}")
        public Response removeHotelREST(
                @PathParam("id") int id,
                @PathParam("bookingNumber") String bookingNumber) {
            // Check that the itinerary is there
            if (itineraries.size() - 1 < id) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Itinerary_2 itinerary = itineraries.get(id);
            // Find the requested hotel
            for (Booking book : itinerary.hotelsReservations) {
                if (book.bookingNumber.equals(bookingNumber)) {
                    itinerary.hotelsReservations.remove(book);
                    return Response.status(Response.Status.OK).build();
                }
            }

            return Response.status(Response.Status.NOT_FOUND).build();
        }

        @DELETE
        @Path("/{id}/flights/{bookingNumber}")
        public Response removeFlightREST(
                @PathParam("id") int id,
                @PathParam("bookingNumber") String bookingNumber) {
            // Check that the itinerary is there
            if (itineraries.size() - 1 < id) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Itinerary_2 itinerary = itineraries.get(id);
            // Find the requested flight

            for (Booking book : itinerary.flightsBookingNumbers) {
                if (book.bookingNumber.equals(bookingNumber)) {
                    itinerary.flightsBookingNumbers.remove(book);
                    return Response.status(Response.Status.OK).build();
                }
            }

            return Response.status(Response.Status.NOT_FOUND).build();

        }

    }

    private static boolean bookFlights(java.lang.String bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard) throws CreditCardFaultMessage {
        com.dtu.mmmngg.MainWebService service = new com.dtu.mmmngg.MainWebService();
        com.dtu.mmmngg.LameDuckWebService port = service.getLameDuckWebServicePort();
        return port.bookFlights(bookingNumber, creditCard);

    }

    private static boolean cancelFlights(java.lang.String bookingnumber, int amount, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCard) throws CreditCardFaultMessage {
        com.dtu.mmmngg.MainWebService service = new com.dtu.mmmngg.MainWebService();
        com.dtu.mmmngg.LameDuckWebService port = service.getLameDuckWebServicePort();
        return port.cancelFlights(bookingnumber, amount, creditCard);
    }

    private static Boolean bookHotel(int bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditcard) throws Exception_Exception {
        com.niceview.NiceViewService service = new com.niceview.NiceViewService();
        com.niceview.NiceView port = service.getNiceViewPort();
        return port.bookHotel(bookingNumber, creditcard);
    }

    private static Boolean cancelHotel(int bookingNumber) throws Exception_Exception {
        com.niceview.NiceViewService service = new com.niceview.NiceViewService();
        com.niceview.NiceView port = service.getNiceViewPort();
        return port.cancelHotel(bookingNumber);
    }

    private static java.util.List<com.niceview.HotelReservation> getHotels(java.lang.String city, javax.xml.datatype.XMLGregorianCalendar arrival, javax.xml.datatype.XMLGregorianCalendar departure) {
        com.niceview.NiceViewService service = new com.niceview.NiceViewService();
        com.niceview.NiceView port = service.getNiceViewPort();
        return port.getHotels(city, arrival, departure);
    }

    private static java.util.List<com.dtu.mmmngg.FlightInfoObject> getFlights(java.lang.String from, java.lang.String toDestination, javax.xml.datatype.XMLGregorianCalendar arg2) {
        com.dtu.mmmngg.MainWebService service = new com.dtu.mmmngg.MainWebService();
        com.dtu.mmmngg.LameDuckWebService port = service.getLameDuckWebServicePort();
        return port.getFlights(from, toDestination, arg2);
    }

}
