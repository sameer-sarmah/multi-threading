package async.future;

public class Address {

	private String firstName,lastName,city,street,PIN;

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getFirstName() {
		return firstName;
	}



	public String getLastName() {
		return lastName;
	}


	public String getPIN() {
		return PIN;
	}

	public Address(String firstName, String lastName, String city, String street, String pIN) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.street = street;
		PIN = pIN;
	}

	@Override
	public String toString() {
		return "Address [firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", street=" + street
				+ ", PIN=" + PIN + "]";
	}


	
	
}
