package async.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OrderPipelineDriver {

	public static void main(String[] args) {

		try {
			asPromise();
			//asDeferred();
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public static void asPromise() {
		  Consumer<Order> consumer=(Order o)->{
	        	System.out.println(o);
	        };
			CompletableFuture<Order> promise = CompletableFuture.supplyAsync(() -> {
				PlaceOrder order = new PlaceOrder();
				try {
					return order.generateOrder();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			})		
			.thenApply((Order order) -> {
				AddCourierDetails courier = new AddCourierDetails(order);
				try {
					return courier.addCourierDetails();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return order;
			})
			.thenApply((Order order) -> {
				AddShipmentDetails shipment = new AddShipmentDetails(order);
				try {
					return shipment.addAddress();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return order;
			});
			
			promise.thenAccept(consumer);
			System.out.println("thenAccept is Non blocking");
		
			
	}
	
	
	public static void asDeferred() {
		  Consumer<Order> consumer=(Order o)->{
	        	System.out.println(o);
	        };		
	        
			CompletableFuture<Order> promise = new CompletableFuture<Order>();	
			promise.thenApply((Order order) -> {
				AddCourierDetails courier = new AddCourierDetails(order);
				try {
					return courier.addCourierDetails();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return order;
			})
			.thenApply((Order order) -> {
				AddShipmentDetails shipment = new AddShipmentDetails(order);
				try {
					return shipment.addAddress();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return order;
			});
			
			promise.thenAccept(consumer);


			PlaceOrder placeOrder = new PlaceOrder();
			try {
				promise.complete(placeOrder.generateOrder());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			System.out.println("thenAccept is Non blocking");

			
	}

}
