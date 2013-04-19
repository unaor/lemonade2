package models;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.codec.DecoderException;

import com.avaje.ebean.validation.Email;

import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import utils.PasswordEncoder;
import utils.UserUtils;

@Entity
public class User extends Model {
	
	private static final long serialVersionUID = 1L;
	@Id
	public Long id;
	@Email
	@Column(unique=true)
	@Required
	public String email;
	@Required @MinLength(value=2) @MaxLength(value=15)
	public String password;
	@Required @MinLength(value=2) @MaxLength(value=15)
	public String name;
	@Required @MinLength(value=2) @MaxLength(value=15)
	public String lastName;
	public boolean admin;
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date registrationDate;
	
	public static final Finder<Long ,User> find = new Finder<Long ,User>(Long.class ,User.class);
	
	
	public  static long createUser(User user){
		user.admin=true;
		user.registrationDate= new Date();
		UserUtils.prepareUser(user);
		encryptPassword(user);
		user.save();
		Logger.debug("Saved user to DB id: " + user.id);
		return user.id;
	}
	
	public static User getUserByEmail(String email){
		User user = find.where().eq("email", email).findUnique();
		return user;		
	}
	
	public static void encryptPassword(User user) {
		try {
			user.password= PasswordEncoder.encode(user.password, user.registrationDate.toString());
		} catch (NoSuchAlgorithmException e) {
			Logger.error("Could not encode password "+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			Logger.error("Could not encode password "+e.getMessage());
		} catch (DecoderException e) {
			Logger.error("Could not encode password "+e.getMessage());
		}
	}
	
	public static String encryptPassword(String password , String salt) {
		String HashedPassword= new String();
		try {
			HashedPassword= PasswordEncoder.encode(password, salt);
		} catch (NoSuchAlgorithmException e) {
			Logger.error("Could not encode password "+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			Logger.error("Could not encode password "+e.getMessage());
		} catch (DecoderException e) {
			Logger.error("Could not encode password "+e.getMessage());
		}
		return HashedPassword;
	}
	
	public static User authenticate(String email , String password){
		User user = getUserByEmail(email);
		if(user ==null){
			Logger.debug("Failed authentication: didnt find the following email: "+email);
			return null;
		}
		String hashedPassword = encryptPassword(password, user.registrationDate.toString());
		if(!hashedPassword.equals(user.password)){
			Logger.debug("Failed authentication: password not match email: "+email);
			return null;
		}	
		return user;		
	}
	

}
