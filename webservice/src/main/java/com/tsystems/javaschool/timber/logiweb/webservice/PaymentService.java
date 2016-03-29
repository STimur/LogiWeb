package com.tsystems.javaschool.timber.logiweb.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tsystems.javaschool.timber.logiweb.webservice.transaction.TransactionBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/payment")
public class PaymentService {

    @Autowired
    TransactionBo transactionBo;

    /*@Autowired
    DriverService driverService;*/

    @GET
    @Path("/save")
    public Response savePayment() {

        String result = transactionBo.save();

        return Response.status(200).entity(result).build();

    }

    /*@GET
    @Path("/driver/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Driver getDriver(@PathParam("driverId") int driverId) {
        return driverService.findById(driverId);
    }*/

}
