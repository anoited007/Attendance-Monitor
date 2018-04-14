package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 *
 * @author Immmanuel
 */
public class Swipe implements Comparable<Swipe> {

    /**
     * ID of the swipe
     */
    protected final int id;

    /**
     * ID of the swipe card
     */
    protected String cardId;

    /**
     * name of the room
     */
    protected String room;

    /**
     * date and time of swipe
     */
    protected final Calendar swipeDateTime;

    private static int lastSwipeIdUsed = 0;
    static final char EOLN = '\n';
    static final String QUOTE = "\"";

    /**
     * no argument constructor
     */
    public Swipe() {
        this.id = ++lastSwipeIdUsed;
        this.cardId = "Unknown";
        this.room = "Unknown";
        this.swipeDateTime = getNow();
    }

    /**
     *
     * @param cardId
     * @param room
     */
    public Swipe(String cardId, String room) {
        this.id = ++lastSwipeIdUsed;
        this.cardId = cardId;
        this.room = room;
        this.swipeDateTime = getNow();
    }

    /**
     *
     * @param swipeId
     * @param cardId
     * @param room
     * @param swipeDateTime
     */
    public Swipe(int swipeId, String cardId, String room, Calendar swipeDateTime) {
        this.id = swipeId;
        this.cardId = cardId;
        this.room = room;
        this.swipeDateTime = swipeDateTime;
        if (swipeId > Swipe.lastSwipeIdUsed) {
            Swipe.lastSwipeIdUsed = swipeId;
        }
    }

    private Calendar getNow() {
        Calendar now = Calendar.getInstance();
        return now;
    }

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    // Methods required: getters, setters, hashCode, equals, compareTo, comparator
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Calendar getSwipeDateTime() {
        return swipeDateTime;
    }

    public static int getLastSwipeIdUsed() {
        return lastSwipeIdUsed;
    }

    /**
     *
     * @param calendar calendar object to be formatted.
     * @return string of formatted date and time.
     */
    public static String formatSwipeDateTime(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    /**
     *
     * @return a string with details of the swipe.
     */
    @Override
    public String toString() {
        return "\nSwipe Id: " + this.id + " - Card Id: " + this.cardId
                + " - Room: " + this.room + " - Swiped: " + formatSwipeDateTime(this.swipeDateTime);
    }

    public String toString(char delimiter) {
        return EOLN + Integer.toString(this.id) + delimiter
                + QUOTE + this.cardId + QUOTE + delimiter
                + QUOTE + this.room + QUOTE + delimiter
                + QUOTE + formatSwipeDateTime(this.swipeDateTime) + QUOTE;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + this.cardId.hashCode();
        hash = 23 * hash + this.room.hashCode();
        hash = 23 * hash + this.swipeDateTime.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Swipe) {
            Swipe swipe = (Swipe) obj;
            return this.id == swipe.id
                    && this.cardId.equals(swipe.cardId)
                    && this.room.equals(swipe.room)
                    && this.swipeDateTime.equals(swipe.swipeDateTime);

        } else {
            return false;
        }
    }

    // Annoymous class that implements Comparator.
    public static Comparator<Swipe> SwipeDateTimeComparator
            = new Comparator<Swipe>() {
        @Override
        public int compare(Swipe swipe1, Swipe swipe2) {
            return swipe1.swipeDateTime.compareTo(swipe2.swipeDateTime);
        }
    };

    @Override
    public int compareTo(Swipe compareSwipe) {
        return this.id - compareSwipe.id;
    }
}
