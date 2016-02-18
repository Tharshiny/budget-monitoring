package controllers;

import models.Operation;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class OperationController extends Controller{

	static Form<Operation> operationForm=Form.form(Operation.class);

	public Result add(){
		Form<Operation> filledForm=operationForm.bindFromRequest();
		Result result=null;
		if(filledForm.hasErrors()){
			result=badRequest();
		}else{
			Operation.add(filledForm.get());
			result=ok(Json.toJson(Operation.all()));
		}	
		return result;
	}

	public Result listAll(){
		return ok(Json.toJson(Operation.all()));
	}

	public Result display(String id){

		Operation operation=Operation.findById(id);

		Result result=null;
		if(operation == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(operation));
		}
		return result;
	}

	public Result displaytitle(String title){
		Operation operation=Operation.findByTitle(title);

		Result result=null;
		if(operation == null){
			result=notFound();
		}else{
			result=ok(Json.toJson(operation));
		}
		return result;
	}

	public Result delete(String id){
		Operation operation=Operation.findById(id);

		Result result=null;
		if(operation == null){
			result=notFound();
		}else{
			Operation.delete(operation);
			result=ok(Json.toJson("deleted"));
		}
		return result;

	}

	public Result update(String id){
		Result result=null;
		Form<Operation> filledForm=operationForm.bindFromRequest();
		
		if (filledForm.hasErrors()) {			
			result = badRequest();
		} else {
			Operation.update(id, filledForm.get());
			result =  display(id);
		}

		return result;
	}

	public Result addTag(String id, String tag){
		if(!Operation.isTagAdded(id, tag)) Operation.addTag(id, tag);
		return display(id);
	}


}
