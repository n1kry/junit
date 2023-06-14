package util;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class CSVToDBConverter {
    public static final String CSV_FILE = "src/main/resources/cars.csv";
    private final CarsDBConnection dbConnection = new CarsDBConnection();

    private static final String QUERY = "INSERT INTO  car ("
            +"model ,"
            +"maker ,"
            +"carNumber ,"
            +"madeYear ,"
            +"startedRentOn ,"
            +"finishedRentOn)"
            +"VALUES (?,?,?,?,?,?);";

    public void convert() {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(QUERY);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM car;");

            CSVReader reader = new CSVReader(new FileReader(CSV_FILE));
            reader.readNext();
            String[] nextLine = reader.readNext();

            do {
                pstmt.setString(1,nextLine[0]);
                pstmt.setString(2,nextLine[1]);
                pstmt.setString(3,nextLine[2]);
                if (Integer.parseInt(nextLine[3]) > 1900) {
                    pstmt.setInt(4, Integer.parseInt(nextLine[3]));
                } else {
                    continue;
                }

                if (!nextLine[4].equals("")) {
                    pstmt.setDate(5, Date.valueOf(nextLine[4]));
                } else {
                    pstmt.setDate(5, null);
                }

                if (!nextLine[5].equals("")) {
                    pstmt.setDate(6, Date.valueOf(nextLine[5]));
                } else {
                    pstmt.setDate(6, null);
                }
                pstmt.executeUpdate();

            } while ((nextLine = reader.readNext()) != null);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
