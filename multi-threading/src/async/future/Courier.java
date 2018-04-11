package async.future;

public class Courier {
 private String id;
 private String company;

public String getId() {
	return id;
}

public Courier(String id, String company) {
	super();
	this.id = id;
	this.company = company;
}

public String getCompany() {
	return company;
}

@Override
public String toString() {
	return "Courier [id=" + id + ", company=" + company + "]";
}


 
 
 
}
