package async.future;

import org.fluttercode.datafactory.impl.DataFactory;

public class AddShipmentDetails {

	public AddShipmentDetails(Order order) {
		super();
		this.order = order;
	}

	private Order order;

	public Order addAddress() throws Exception {
		DataFactory df = new DataFactory();
		String firstName=df.getFirstName();
		String lastName=df.getLastName();
		String city=df.getCity();
		String PIN=df.getNumberText(6);
		String street=df.getStreetName();
		Address address=new Address(firstName, lastName, city, street, PIN);
		order.setAddress(address);
		Thread.sleep(2000);
		System.out.println("Order for "+firstName+" "+lastName +" is ready to be sent to courier" );
		return order;
	}

}
