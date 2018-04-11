package async.future;

import org.fluttercode.datafactory.impl.DataFactory;

public class PlaceOrder {
	public Order generateOrder() throws Exception {
		DataFactory df = new DataFactory();
		String uuid=df.getNumberText(6);
		String name=df.getRandomText(6);
		Product product=new Product(uuid, name);
		Order order=new Order(uuid);
		order.setProduct(product);
		Thread.sleep(3000);
		System.out.println("Order "+uuid+" created");
		return order;
	}

}
