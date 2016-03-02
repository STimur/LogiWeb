package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import com.tsystems.javaschool.timber.logiweb.view.util.TruckSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample controller which shows how to use jQuery jTable for CRUD
 *
 */
public class TruckJSController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TruckService truckService = Services.getTruckService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TruckJSController() {}

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("action")!=null){
            List<Truck> trucks=new ArrayList<Truck>();
            String action=(String)request.getParameter("action");
            //Create Gson object and register custom serializer
            Gson gson = new GsonBuilder().registerTypeAdapter(Truck.class, new TruckSerializer())
                    .create();
            response.setContentType("application/json");

            if(action.equals("list")){
                try{
                    //Fetch Data from User Table
                    trucks=truckService.findAll();

                    //Convert Java Object to Json
                    String listData = gson.toJson(trucks);
                    //JsonArray jsonArray = element.getAsJsonArray();
                    //String listData=jsonArray.toString();
                    //Return Json in the format required by jTable plugin
                    listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";
                    response.getWriter().print(listData);
                }catch(Exception ex){
                    String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
                    response.getWriter().print(error);
                    ex.printStackTrace();
                }
            }
            else if(action.equals("create") || action.equals("update")){
                Truck truck=new Truck();
                if(request.getParameter("id")!=null){
                    String id=request.getParameter("id");
                    truck.setId(Integer.valueOf(id));
                }
                if(request.getParameter("regNumber")!=null){
                    String regNumber=request.getParameter("regNumber");
                    truck.setRegNumber(regNumber);
                }
                if(request.getParameter("shiftSize")!=null){
                    String shiftSize=request.getParameter("shiftSize");
                    truck.setShiftSize(Integer.valueOf(shiftSize));
                }
                if(request.getParameter("capacity")!=null){
                    String capacity=request.getParameter("capacity");
                    truck.setCapacity(Integer.valueOf(capacity));
                }
                if(request.getParameter("state")!=null){
                    String state=request.getParameter("state");
                    truck.setState(state);
                }
                try{
                    if(action.equals("create")){//Create new record
                        truckService.create(truck);
                        trucks.add(truck);
                        //Convert Java Object to Json
                        String json=gson.toJson(truck);
                        //Return Json in the format required by jTable plugin
                        String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";
                        response.getWriter().print(listData);
                    }else if(action.equals("update")){//Update existing record
                        truckService.update(truck);
                        String listData="{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                }catch(Exception ex){
                    String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
                    response.getWriter().print(error);
                }
            }else if(action.equals("delete")){//Delete record
                try{
                    if(request.getParameter("id")!=null){
                        String id=request.getParameter("id");
                        truckService.delete(Integer.parseInt(id));
                        String listData="{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                }catch(Exception ex){
                    String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
                    response.getWriter().print(error);
                }
            }
        }
    }

}
