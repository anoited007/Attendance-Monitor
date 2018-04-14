package controllers;

import helpers.InputHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author mga
 */
public class AttendanceController {

    private final Repository repository;

    /**
     *
     */
    public AttendanceController() {
        InputHelper input = new InputHelper();
        char choice = input.readCharacter("Do you want to load from an existing attendance file? (Y/N)");
        if (choice == 'y' || choice == 'Y') {
            String fileName = input.readString("Enter the filename: ");
            repository = new Repository(fileName);
        } else {
            repository = new Repository();
        }
    }

    /**
     *
     */
    public void run() {
        boolean finished = false;

        do {
            char choice = displayAttendanceMenu();
            switch (choice) {
                case 'A':
                    addSwipe();
                    break;
                case 'B':
                    listSwipes();
                    break;
                case 'C':
                    listSwipesByCardIdOrderedByDateTime(); // 
                    break;
                case 'D':
                    listSwipeStatistics(); //
                    break;
                case 'Q':
                    InputHelper inputHelper = new InputHelper();
                    String fileName = inputHelper.readString("Enter filename");
                    repository.store(fileName);
                    finished = true;
            }
        } while (!finished);
    }

    private char displayAttendanceMenu() {
        InputHelper inputHelper = new InputHelper();
        System.out.print("\nA. Add Swipe");
        System.out.print("\tB. List Swipes");
        System.out.print("\tC. List Swipes In Date Time Order");
        System.out.print("\tD. List Swipes Which Match Card Id");
        System.out.print("\tQ. Quit\n");
        return inputHelper.readCharacter("Enter choice", "ABCDQ");
    }

    private void addSwipe() {
        System.out.format("\033[31m%s\033[0m%n", "Add Swipe");
        System.out.format("\033[31m%s\033[0m%n", "=========");
        InputHelper inputHelper = new InputHelper();
        String room = "General";
        char choice = inputHelper.readCharacter("Are you adding for a visitor? (Y/N)", "YN");
        if (choice == 'y' || choice == 'Y') {
            String cardId = inputHelper.readString("Enter card ID");
            String visitorName = inputHelper.readString("Enter visitor's name");
            String visitorCompany = inputHelper.readString("Enter visitor's company");

            VisitorSwipe visitorSwipe = new VisitorSwipe(cardId, room, visitorName, visitorCompany);
            repository.add(visitorSwipe);
        } else {
            String cardId = inputHelper.readString("Enter card ID");
            Swipe swipe = new Swipe(cardId, room);
            repository.add(swipe);
        }
    }

    private void listSwipes() {
        System.out.format("\033[31m%s\033[0m%n", "Swipes");
        System.out.format("\033[31m%s\033[0m%n", "======");
        Collections.sort(this.repository.getItems());
        Iterator<Swipe> iterator = this.repository.getItems().listIterator();
        // No need to cast to Swipe because the type was specified in the iterator.
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }

    }

    private void listSwipesByCardIdOrderedByDateTime() {
        System.out.format("\033[31m%s\033[0m%n", "Swipes By Card Id");
        System.out.format("\033[31m%s\033[0m%n", "=================");

        InputHelper input = new InputHelper();
        String cardId = input.readString("Enter card Id");
        //Sorting the list before filtering with card id.
        List<Swipe> filteredByCardId = this.repository.getItems();
        Collections.sort(filteredByCardId, Swipe.SwipeDateTimeComparator.reversed());
        Iterator iterator = filteredByCardId.listIterator();
        while (iterator.hasNext()) {

            Swipe swipe = (Swipe) iterator.next();
            if (swipe.getCardId().equalsIgnoreCase(cardId)) {
                System.out.println(swipe.toString());
            }
        }
    }

    private void listSwipeStatistics() {
        System.out.format("\033[31m%s\033[0m%n", "Swipe Statistics for room");
        System.out.format("\033[31m%s\033[0m%n", "=========================");
        List<Swipe> filteredByRoom = new ArrayList<>();
        InputHelper inputHelper = new InputHelper();
        String room = inputHelper.readString("Enter Room: ");
        Iterator iterator = repository.getItems().listIterator();
        while (iterator.hasNext()) {
            Swipe swipe = (Swipe) iterator.next();
            if (swipe.getRoom().equalsIgnoreCase(room)) {
                filteredByRoom.add(swipe);
            }
        }
        //Ensuring that our filtered rooms returned Something before we move on
        if(!(filteredByRoom.isEmpty())){
                    
    
        filteredByRoom.sort(Swipe.SwipeDateTimeComparator);
        String recentDate = Swipe.formatSwipeDateTime(Collections.max(filteredByRoom).getSwipeDateTime());
        
        //String recentDate = Swipe.formatSwipeDateTime(filteredByRoom.get(filteredByRoom.size() - 1).getSwipeDateTime());
        System.out.println("There are " + filteredByRoom.size() + " swiped cards in room "
                + room + " and date for the last swiped card is "
                + recentDate);
    }
        
        else
            System.out.println("There is no statistics for the specified room");
        
    }
}
