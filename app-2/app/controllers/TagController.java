package controllers;

import models.Tag;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class TagController extends Controller{

	static Form<Tag> tagForm=Form.form(Tag.class);
	
	public Result create(){
		Form<Tag> filledForm=tagForm.bindFromRequest();
		Result result=null;
		if(filledForm.hasErrors()){
			result=badRequest();
		}else{
			Tag.create(filledForm.get());
			result=ok(Json.toJson(Tag.all()));
		}	
		return result;
	}
	
	public Result listAll(){
		return ok(Json.toJson(Tag.all()));
	}
	
	public Result display(String id){

		Tag tag=Tag.findById(id);
		
		Result result=null;
		if(tag == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(tag));
		}
		return result;
	}
	
	public Result delete(String id){
		Tag tag=Tag.findById(id);
		
		Result result=null;
		if(tag == null){
			result=notFound();
		}else{
			Tag.delete(tag);
			result=ok(Json.toJson("deleted"));
		}
		return result;
	}
}
