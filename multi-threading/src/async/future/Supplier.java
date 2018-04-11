package async.future;

public class Supplier {
	private String id,name,city,email,phone;

	public Supplier(String id, String name, String city, String email, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.email = email;
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public String toString() {
		return "Supplier [name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}
	
	
}
