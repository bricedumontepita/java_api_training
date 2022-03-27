package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JsonHandler {

    public Map<String, String> getJson (String json, ObjectMapper mapper) throws IOException {
        return mapper.readValue(json, Map.class);
    }

    public Map<String, String> isJson (String jsonInString, ObjectMapper mapper) {
        try {
            mapper.readTree(jsonInString);
            Map<String, String> json = this.getJson(jsonInString, mapper);
            ArrayList<String> keysRequired = new ArrayList<>();
            keysRequired.add("url");
            keysRequired.add("id");
            keysRequired.add("message");
            for (Map.Entry<String, String> pair : json.entrySet()) {
                System.out.println(pair.getKey());
                int index = keysRequired.indexOf(pair.getKey());
                if (index != -1) {
                    keysRequired.remove(index);
                } else {
                    return null;
                }
            }
            if (keysRequired.size() == 0) {
                return json;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
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

            System.out.println(jsonString);

            // Java objects to JSON string - pretty-print
            String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);

            System.out.println(jsonInString2);

            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String toJson (Map<String, String> val, ObjectMapper mapper) throws IOException {
        ObjectNode user = mapper.createObjectNode();
        for (Map.Entry<String, String> pair : val.entrySet()) {
            user.put(pair.getKey(), pair.getValue());
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
    }
}
