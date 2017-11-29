import java.util.*;

public class Building {
    private int entrance = 2;
    private int bottom = 1;
    private int top = 6;
    private LinkedList<Lift> lifts = new LinkedList<Lift>();
    private LinkedList<Person> people = new LinkedList<Person>();
    private String mode;
    
    public static void main(String[] args) {
        Building building = new Building();
        building.use();
        
    }
    
    public Building() {
        mode = readMode();
        lifts.add(new Lift(1, 1, 6, entrance));
        lifts.add(new Lift(2, 2, 6, entrance));
        lifts.add(new Lift(3, 2, 5, entrance));
    }
    
    public void use() {
        if (mode.contains("a"))
                showLifts();
        char choice; 
        while ((choice = readChoice()) != 'x') {
            
            switch (choice) {
                case 'a': add(); break;
                case 'r': remove(); break;
                case 'p': showPeople(); break;
                case 'c': callLift(); break;
                case 'l': showLifts(); break;
                case 'o': operate(); break;
                default: help(); break;
            }
            if (mode.contains("a"))
                showLifts();
        }
        
        
    }
    
    public void add() {
        int personid = readId();
        Person matchingperson = person(personid);
        if (matchingperson == null) {
            people.add(new Person(personid, readName(), 2));
        }
            else 
            System.out.println("ID already exists");
    }
    
    public void remove() {
        int personid = readId();
        Person matchingperson = person(personid);
        if (matchingperson != null  && matchingperson.isOnEntrance(entrance) && !(isOnLiftOrInQueue(matchingperson)))
            people.remove(matchingperson);
            else 
            System.out.println("No such person"); // OR the person is in a lift, in a queue, or not on level 2 
        
    }
    
    public boolean isOnLiftOrInQueue(Person matchingperson) {
        for (Lift lift : lifts)
            if ((lift.isOnLift(matchingperson.getId()) || (lift.isInQueue(matchingperson.getId())))) {
                return true;
            }
        return false;
    }
    
    public void showPeople() {
        for (Person person : people) 
           System.out.println(person);
        
    }
    
    public void callLift() {
        Person matchingperson = person(readId());
        if (matchingperson != null) {
            int max = 0;
            int i = 0;
            int maxindex = 0;
            int selectedDestination = readDestination();
            if (selectedDestination > top || selectedDestination < bottom)
                System.out.println("No suitable lift found");
                else {
            matchingperson.setDestination(selectedDestination); 
            LinkedList<Integer> suitabilities = new LinkedList<Integer>();   
                for (Lift lift : lifts) {
                int suitability = lift.suitability(top - bottom, matchingperson.getLevel(), matchingperson.getDestination());
                suitabilities.add(suitability);
            }
            for (int suitability : suitabilities) {
                if (suitability > max) {
                    max = suitability;
                    maxindex = i;
                }
                i++;
            }
            lifts.get(maxindex).addQueuer(matchingperson);
            }
        }   
        else 
        System.out.println("No such person");
    }
    
    public void showLifts() {
        if (mode.contains("i")) {
            printIMode();
        }
        if (mode.contains("w")) {
            printWMode();
        }
        for (Lift lift : lifts)
            System.out.println(lift);
    }
    
    public void printIMode() {
        int liftcount = 0;
        int realcount = 0;
        System.out.print("Idle:    ");
        for (int i = bottom; i < top + 1; i++) {
            for (Lift lift : lifts) {
                for (Person person : people) {
                    if (person.levelMatchesCurrentLevel(i) && !(isOnLiftOrInQueue(person))) {
                        ++liftcount;
                    }
                    if (liftcount == lifts.size()) {
                        ++realcount;
                        liftcount = 0;
                    }
                 
                }
            }
            
            if (realcount > 0) 
                System.out.print(realcount);
                else 
                System.out.print(" ");
            
            
            realcount = 0;
            
        }
        System.out.println();
    }
    
    public void printWMode() {
        int count = 0;
        System.out.print("Waiting: ");
        for (int i = bottom; i < top + 1; i++) {
            for (Lift lift : lifts) {
                for (Person person : people) {
                    if (person.levelMatchesCurrentLevel(i) && lift.isInQueue(person.getId())) {
                        ++count;
                    }
                }
            }
            if (count > 0) 
                System.out.print(count);
                else 
                System.out.print(" ");
            
            count = 0;
        }
        System.out.println();
    }
    
    public void operate() {
        for (Lift lift :lifts)
            lift.operate();
    }
    
    public Person person(int id) {
        for (Person person : people) 
            if (person.hasId(id))
                return person;
            return null;
    }
    
    public void help() {
        System.out.println("Menu");
        System.out.println("a = add person");        
        System.out.println("r = remove person");
        System.out.println("p = show people");
        System.out.println("c = call lift");
        System.out.println("l = show lifts");
        System.out.println("o = operate");
    
    }
    
    
    private String readMode() {
        System.out.print("Mode: ");
        return In.nextLine();
        
    }
    
    private char readChoice() {
        System.out.print("Choice (a/r/p/c/l/o/x): ");
        return In.nextChar();
        
    }
    
    private int readId() {
        System.out.print("Person ID: ");
        return In.nextInt();
    }
    
    private String readName() {
        System.out.print("Name: ");
        return In.nextLine();
    }
    
    private int readDestination() {
        System.out.print("Destination level: ");
        return In.nextInt();
    }
    
    // NOTES FOR THE MODE PART, use switch and case along with .contains, to check whether the string entered
    // by the user contains any of the qulifying characters, and if it does, make particular boolean 
    //values true.
    
    
    
}