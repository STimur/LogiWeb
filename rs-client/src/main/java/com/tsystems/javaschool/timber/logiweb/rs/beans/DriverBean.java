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

    private static final String RS_CHANGE_STATE_URL = "http://localhost:8080/logiweb/driver/change-state";
    private static final String RS_OPEN_SHIFT_URL = "http://localhost:8080/logiweb/driver/open-shift";
    private static final String RS_CLOSE_SHIFT_URL = "http://localhost:8080/logiweb/driver/close-shift";

    private int id = 5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDriverDriveStatus() throws IOException {
        invokeRestService(RS_CHANGE_STATE_URL, DriverState.DRIVE);
    }

    public void setDriverRestStatus() throws IOException {
        invokeRestService(RS_CHANGE_STATE_URL, DriverState.REST);
    }

    public void setDriverShiftStatus() throws IOException {
        invokeRestService(RS_CHANGE_STATE_URL, DriverState.SHIFT);
    }

    public void openDriverShift() throws IOException {
        invokeRestService(RS_OPEN_SHIFT_URL, DriverState.DRIVE);
    }

    public void closeDriverShift() throws IOException {
        invokeRestService(RS_CLOSE_SHIFT_URL, DriverState.REST);
    }

    private void invokeRestService(String restURL, DriverState newState) throws IOException {
        Client client = Client.create();
        WebResource webResource = client.resource(restURL);
        ObjectMapper mapper = new ObjectMapper();
        DriverDto driver = new DriverDto();
        driver.setId(this.id);
        driver.setState(newState);
        String JSONDriverDto = mapper.writeValueAsString(driver);
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, JSONDriverDto);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
    }
}
