package com.tsystems.javaschool.timber.logiweb.rs.beans;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.CargoState;
import com.tsystems.javaschool.timber.logiweb.service.dto.CargoDto;
import org.codehaus.jackson.map.ObjectMapper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class CargoBean {

    private static final String RS_CHANGE_STATE_URL = "http://localhost:8080/logiweb/cargo/change-state";

    private int id = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCargoShippedStatus() throws IOException {
        setCargoStatus(CargoState.SHIPPED);
    }

    public void setCargoDeliveredStatus() throws IOException {
        setCargoStatus(CargoState.DELIVERED);
    }

    private void setCargoStatus(CargoState newState) throws IOException {
        Client client = Client.create();
        WebResource webResource = client.resource(RS_CHANGE_STATE_URL);
        ObjectMapper mapper = new ObjectMapper();
        CargoDto cargo = new CargoDto();
        cargo.setId(this.id);
        cargo.setState(newState);
        String JSONCargoDto = mapper.writeValueAsString(cargo);
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, JSONCargoDto);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
    }
}
