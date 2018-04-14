package daos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author Immanuel
 */
public class DAOTextImpl implements DAOInterface {

    private static final char DELIMITER = ',';

    @Override
    public Repository load(String fileName) {
        Repository repository = new Repository();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            String[] data;
            while (line != null) {
                data = line.split(Character.toString(DELIMITER));
                int id = Integer.parseInt(data[0]);
                String cardId = stripQuotes(data[1]);
                String room = stripQuotes(data[2]);

                Date date = null;
                try {
                    date = dateFormat.parse(stripQuotes(data[3]));
                } catch (ParseException ex) {
                    Logger.getLogger(DAOTextImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                Calendar swipeDateTime = Calendar.getInstance();
                swipeDateTime.setTime(date);

                Swipe swipe = null;
                if (data.length == 6) {
                    String visitorName = stripQuotes(data[4]);
                    String visitorCompany = stripQuotes(data[5]);
                    swipe = new VisitorSwipe(id, cardId, room, swipeDateTime, visitorName, visitorCompany);
                } else {
                    swipe = new Swipe(id, cardId, room, swipeDateTime);
                }
                repository.add(swipe);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(DAOTextImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return repository;
    }

    @Override
    public void store(String fileName, Repository repository) {
        try {
            try (PrintWriter writer = new PrintWriter(fileName)) {
                writer.write(repository.toString(DELIMITER));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAOTextImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String stripQuotes(String string) {
        return string.substring(1, string.length() - 1);
    }
}
