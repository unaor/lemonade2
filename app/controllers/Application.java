package controllers;

import models.User;
import play.*;
import play.api.data.validation.ValidationError;
import play.api.i18n.Messages.Message;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.*;
import play.mvc.Http.Context;
import com.feth.play.module.mail.Mailer.Mail.Body;
import com.feth.play.module.mail.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Start page"));
    }
    
    public static Result login(){
    	return ok (views.html.login.login.render("Please Login"));
    }
    
    public static Result authenticate(){
    	String email =Form.form().bindFromRequest().get("email");
    	String password = Form.form().bindFromRequest().get("password");
    	flash().put("error", "Username or password incorrect");
    	session().clear();
        session("email",email);
    	return redirect(routes.Application.login());
    }
    
    public static Result register(){
    	return ok(register.render(Form.form(User.class)));
    }
    
    public static Result doRegistration(){
    	Form<User> form = Form.form(User.class).bindFromRequest();
    	boolean cleared =true;
    	if(form.hasErrors()){
			cleared=false;
			return badRequest(register.render(form));
		}
    	String repeatedPassowrd = Form.form().bindFromRequest().get("repeatPassword");
    	if(!form.get().password.equals(repeatedPassowrd)){
    		form.reject("repeatPassword", Messages.get("password.not.match"));
    		cleared = false;
    	}
    	if(User.getUserByEmail(form.get().email)!=null){
    		form.reject("email", Messages.get("email.occupied",form.get().email));
    		cleared=false;
    	}
    	if(!cleared){
    		return badRequest(register.render(form));
    	}
    	User.createUser(form.get());
    	final Body body = new Body("this is a text",views.html.email.welcome.render().toString());
    	Mailer.getDefaultMailer().sendMail("Subject - you have Uris app", body, form.get().email);
    	return ok("yeah");
    }
  
}
