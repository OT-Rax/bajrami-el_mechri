package umidity.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class DatabaseManager {

        public void addHumidity(HumidityRecord humidityRecord) throws IOException {
        try {
                final ObjectMapper objectMapper=new ObjectMapper();
                List<HumidityRecord> records=objectMapper.readValue(new File(humidityRecord.getCity_id()+".json"), new TypeReference<List<HumidityRecord>>(){});
                records.add(humidityRecord);
                //records.forEach(x -> System.out.println(x.toString()));
                ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
                System.out.println("ORA SCRIVO SUL FILE PERCHE' HO TROVATO!");
                writer.writeValue(Paths.get(humidityRecord.getCity_id()+".json").toFile(), records);
        }
        catch (MismatchedInputException e){
            System.out.println("Non ho trovato nessun record, CREO LA LISTA");
            List<HumidityRecord> records= new ArrayList<>();
            records.add(humidityRecord);
            final ObjectMapper objectMapper=new ObjectMapper();
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            System.out.println("Ora LA SCRIVO");
            writer.writeValue(Paths.get(humidityRecord.getCity_id()+".json").toFile(), records);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<HumidityRecord> getHumidity(int city_id) throws IOException {
        List<HumidityRecord> records=new ArrayList<>();
            try {
            final ObjectMapper objectMapper=new ObjectMapper();
            records=objectMapper.readValue(new File(city_id+".json"), new TypeReference<List<HumidityRecord>>(){});
        }catch (MismatchedInputException e){
            System.out.println("Non ho trovato nessun record, RITORNO UNA LISTA VUOTA!");
            return records;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        //records.forEach(x -> System.out.println(x.toString()));
        return records;
    }
}
