package async.future;

public class Order {
  
	private String id;
	private Address address;
	private Product product;
	private Courier courier;
	
	public Order(String id) {
		super();
		this.id = id;
	}	
	
	public String getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Courier getCourier() {
		return courier;
	}
	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", address=" + address + ", product=" + product + ", courier=" + courier + "]";
	}
	
	
	
}
