package controllers;

import models.Business;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
@Authenticated(Secured.class)
public class Dashboard extends Controller {
	
	public static Result index(){
		return ok(views.html.dashboard.index.render("Welcome"));
	}
	
	public static Result firstTime(){
		return ok(views.html.dashboard.first_time.render(Form.form(Business.class)));
	}
	
	public static Result saveBusiness(){
		Business business = new Business(session().get("email"));
		//set the rest of the properties
		Business.saveBusiness(business);
		flash().put("success", "The information was saved");
		return redirect(routes.Dashboard.index());
	}

}
