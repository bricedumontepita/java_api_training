package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonHandler {

    public Map<String, String> getJson (String json, ObjectMapper mapper) throws IOException {
        return mapper.readValue(json, Map.class);
    }

    public Object parseJson (String jsonStr, Class classData, ObjectMapper mapper) throws IOException {
        try {
            Object requestData = mapper.readValue(jsonStr, classData);
            // pretty print
            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestData);
            System.out.println(prettyStaff1);
            return requestData;
        } catch (IOException e) {
            System.out.println("Erreur " + e);
            return null;
        }
    }

    public String toJson2 (Object request, ObjectMapper mapper) {
        try {
            String jsonString = mapper.writeValueAsString(request);


            // Java objects to JSON string - pretty-print
            String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
            System.out.println("send :");
            System.out.println(jsonInString2);

            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
