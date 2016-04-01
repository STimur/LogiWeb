package com.tsystems.javaschool.timber.logiweb.rs.beans;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.service.dto.DriverDto;
import org.codehaus.jackson.map.ObjectMapper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class DriverBean {

    private static final String RS_SERVER_URL = "http://localhost:8080/logiweb/driver/change-state";

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDriveStatus() throws IOException {
        Client client = Client.create();
        WebResource webResource = client.resource(RS_SERVER_URL);
        ObjectMapper mapper = new ObjectMapper();
        DriverDto driver = new DriverDto();
        driver.setId(this.id);
        driver.setState(DriverState.DRIVE);
        String JSONDriverDto = mapper.writeValueAsString(driver);
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, JSONDriverDto);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP erro code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        DriverDto newDriver = mapper.readValue(output, DriverDto.class);
        System.out.println("Old car: " + driver);
        System.out.println("Output from Server ....");
        System.out.println(newDriver);
    }

    public void setRestStatus() {

    }

    public void setShiftStatus() {

    }
}
