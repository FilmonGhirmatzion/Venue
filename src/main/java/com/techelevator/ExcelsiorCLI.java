package com.techelevator;

import java.util.List;
import javax.sql.DataSource;
import com.techelevator.datebaseclasses.*;
import com.techelevator.datebaseclasses.jdbc.JDBCReservationDAO;
import com.techelevator.datebaseclasses.jdbc.JDBCSpaceDAO;
import com.techelevator.datebaseclasses.jdbc.JDBCVenueDAO;
import org.apache.commons.dbcp2.BasicDataSource;

public class ExcelsiorCLI {
	private static final String MAIN_MENU_LIST_VENUES = "List Venues";
	private static final String MAIN_MENU_QUIT = "Quit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] {MAIN_MENU_LIST_VENUES, MAIN_MENU_QUIT};

	private static final String SECOND_MENU_VIEW_SPACES = "View Spaces";
	private static final String SECOND_MENU_SEARCH_RESERVATIONS = "Search for Reservation";
	private static final String SECOND_MENU_RETURN = "Return To Previous Screen";
	private static final String[] SECOND_MENU_OPTIONS = new String[] {SECOND_MENU_VIEW_SPACES, SECOND_MENU_SEARCH_RESERVATIONS, SECOND_MENU_RETURN};

	private static final String THIS_IS_A_TEST_gap = "";
	private static final String THIS_IS_A_TEST_RETURN_gap = "";
	private static final String[] TEST_MENU_OPTIONS_gap = new String[] { THIS_IS_A_TEST_gap, THIS_IS_A_TEST_RETURN_gap};

	private Menu menu;
	private VenueDAO venueDAO;
	private SpaceDAO spaceDAO;
	private ReservationDAO reservationDAO;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		ExcelsiorCLI application = new ExcelsiorCLI(dataSource);
		application.run();
	}

	public ExcelsiorCLI(DataSource datasource) {
		this.menu = new Menu(System.in, System.out);

		venueDAO = new JDBCVenueDAO(datasource);
		spaceDAO = new JDBCSpaceDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
	}

	public void run() {
		while(true) {
			menu.printHeading("What would you like to do?");
			String choice = (String)menu.getChoiceFromMainMenuOptions(MAIN_MENU_OPTIONS);

			if(choice.equals(MAIN_MENU_LIST_VENUES)) {
				handleViewSpaces(0);
			}
			else if(choice.equalsIgnoreCase(MAIN_MENU_QUIT)) {
				System.exit(0);
			}
		}
	}

	private void handleViewSpaces(int userInput) {
		menu.printHeading("Which venue would you like to view?");
		List<Venue> listOfVenues = venueDAO.getAllVenues();

		if(listOfVenues.size() > 0) {
			Venue selectedVenue = (Venue) menu.getChoiceFromVenueOptions(listOfVenues.toArray());
		} else {
			System.out.println("\n*** No results ***");
		}

		String choice = (String)menu.getChoiceFromVenueOptions(SECOND_MENU_OPTIONS);
		Venue venue = venueDAO.getAllVenues().get(userInput);

		if(choice.equals(SECOND_MENU_VIEW_SPACES)) {
			handleViewSpaces(venue);
		}

		else if(choice.equals(SECOND_MENU_RETURN)) {
			return;
		}
	}

	private void handleViewSpaces(Venue venue) {
		menu.printHeading(venue.getName());
		System.out.println(String.format("%-5s%-32s%-10s%-12s%s", " ", "Name", "Open", "Close", "Daily Rate"));
		List<Space> venues = spaceDAO.getSpacesById(venue.getId());
		for (Space camp : venues)
		{
			System.out.println(camp);
		}
	}

}