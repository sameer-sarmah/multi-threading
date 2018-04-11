package async.future;

import java.util.concurrent.CompletableFuture;

import org.fluttercode.datafactory.impl.DataFactory;

public class PurchaseProductDriver {

	public static void main(String[] args) throws InterruptedException {
		CompletableFuture<Integer> adidas = CompletableFuture.supplyAsync( () -> {
			return getProductPrice("Adidas tshirt");
		});
		CompletableFuture<Integer> puma = CompletableFuture.supplyAsync( () -> {
			return getProductPrice("Puma tshirt");
		});
		adidas.thenCombine(puma,(adidasValue,pumaValue)->{
			System.out.println("Adidas tshirt "+ adidasValue+", Puma tshirt "+pumaValue);
			return adidasValue+pumaValue;
		}).thenAccept((Integer total)->{
			System.out.println("cumulative value "+total);
		});
		
		
		CompletableFuture<Supplier> promise = CompletableFuture.supplyAsync( () -> {
			return "Adidas tshirt"; 
		}).thenCompose((String productName)->{
			return getSupplier("Adidas tshirt",2000);
		});
		promise.thenAccept((Supplier supplier)->{
			System.out.println(supplier);
		});
		
		Thread.sleep(10000);
	}

	public static CompletableFuture<Supplier> getSupplier(String productId,int delay){
		CompletableFuture<Supplier> promise = CompletableFuture.supplyAsync( () -> {
			DataFactory df = new DataFactory();
			String name=df.getBusinessName();
			String city=df.getCity();
			String id=df.getNumberText(6);
			String email=df.getEmailAddress();
			String phone=df.getNumberText(6);
			Supplier supplier=new Supplier(id, name, city, email, phone); 
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return supplier;
		});
		return promise;
	}
	
	
	public static int getProductPrice(String productName){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(productName.equals("Adidas tshirt")) {
			return 1000;
		}
		else if(productName.equals("Puma tshirt")){
			return 800;
		}
		else
		return 500;
	}
	
}
