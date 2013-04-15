package utvalimind.helpers;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

public class Election {
	public static Set<String> generalResultsChannelKeys = new HashSet<String>(); 
	public static Set<String> areaOrPartyResultsChannelKeys = new HashSet<String>(); 
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	 /* Create a remote service proxy to talk to the server-side Greeting
	  service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	//This is entry point method.
	public void onModuleLoad() {
		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (event.getValue().equals("results"))
					createResultsTable();
				else if (event.getValue().indexOf("areaResults") >= 0) {
					int areaId = Integer.parseInt(event.getValue().substring(11));
					createAreaResultsTable(areaId);
				}
				else if (event.getValue().indexOf("partyResults") >= 0) {
					int partyId = Integer.parseInt(event.getValue().substring(12));
					createPartyResultsTable(partyId);
				}
				else if (event.getValue().equals("candidates"))
					createCandidatesTable(0, "");
			}
		});
		History.fireCurrentHistoryState();


	}

	public static void addHistoryItem(String item) {
		History.newItem(item);
	}
	public static native void createResultsTable() /*-{
	$wnd.createResultsTable();
}-*/;

public static native void createAreaResultsTable(int areaId) /*-{
$wnd.createAreaResultsTable(areaId);
}-*/;

public static native void createPartyResultsTable(int partyId) /*-{
$wnd.createPartyResultsTable(partyId);
}-*/;

public static native void createCandidatesTable(int selectedArea, String searchKeywords) /*-{
$wnd.createCandidatesTable(selectedArea, searchKeywords);
}-*/;

public static native void exportStaticMethodHistory() /*-{
$wnd.addHistoryItem = $entry(@utvalimind.helpers.Election::addHistoryItem(Ljava/lang/String;));
}-*/;
		
}
