package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Business extends Model {

	@Id
	public Long id;
	@OneToOne
	public User owner;
	@Required
	public Long rnc;
	public String businessName;
	public String address;
	public String phoneNumber;
	public String faxNumber;
	public String email;
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date startDate;
	
	public Business(){};
	
	public Business(String ownerEmail){
		this.owner = User.getUserByEmail(ownerEmail);
	}
	
	public static Model.Finder<Long,Business> find = new Model.Finder(Long.class, Business.class);
	
	public static Business getBusiness(String ownerEmail){
		return find.where().eq("owner.email",ownerEmail).findUnique();
	}
	
	public static void saveBusiness(Business business){
		business.save();
	}
	
}
