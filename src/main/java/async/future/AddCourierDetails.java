package async.future;

import org.fluttercode.datafactory.impl.DataFactory;

public class AddCourierDetails {

	public AddCourierDetails(Order order) {
		super();
		this.order = order;
	}

	private Order order;
	

	public Order addCourierDetails() throws Exception {
		DataFactory df = new DataFactory();
		String uuid=df.getNumberText(6);
		String company=df.getBusinessName();
	    Courier courier=new Courier(uuid, company);
		order.setCourier(courier);
		Thread.sleep(2000);
		System.out.println("Courier details attached,courier number "+uuid+" and company "+company+".Order is ready to be sent to be dispatched" );
		return order;
	}

}
