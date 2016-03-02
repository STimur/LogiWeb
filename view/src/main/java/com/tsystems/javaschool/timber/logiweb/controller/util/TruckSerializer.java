package com.tsystems.javaschool.timber.logiweb.controller.util;

import com.google.gson.*;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.lang.reflect.Type;

public class TruckSerializer implements JsonSerializer<Truck> {
    @Override
    public JsonElement serialize(final Truck truck, final Type type, final JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(truck.getId()));
        result.add("regNumber", new JsonPrimitive(truck.getRegNumber()));
        result.add("regNumber", new JsonPrimitive(truck.getRegNumber()));
        result.add("shiftSize", new JsonPrimitive(truck.getShiftSize()));
        result.add("capacity", new JsonPrimitive(truck.getCapacity()));
        result.add("state", new JsonPrimitive(truck.getState()));
        if (truck.getCity() != null) {
            result.add("city", new JsonPrimitive(truck.getCity().getName()));
        }
        else {
            result.add("city", new JsonPrimitive("-----"));
        }
        return result;
    }
}