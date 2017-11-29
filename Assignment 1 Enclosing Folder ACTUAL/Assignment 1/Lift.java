import java.util.*;

public class Lift {
    private int number;
    private int bottom;
    private int top;
    private int level;
    private int direction;
    private LinkedList<Person> passengers = new LinkedList<Person>();
    private LinkedList<Person> queue = new LinkedList<Person>();

    public Lift(int number, int bottom, int top, int level) {
        this.number = number;
        this.bottom = bottom;
        this.top = top;
        this.level = level;
        direction = 0;
    }
    
    public int suitability(int buildingdistance, int personlevel, int persondestination) {
        int persondirection = persondestination - personlevel;
        int liftcallerdist = Math.abs(level - personlevel);
        int travelupaway = Math.abs(level + 1 - personlevel);
        int traveldownaway = Math.abs(level - 1 - personlevel);
        // lift's service range doesnt include the caller's service current level or destination 
        if (!((bottom <= personlevel && top >= personlevel) && (bottom <= persondestination && top >= persondestination))) {
            return 0;
        }
        // the lift is moving away from the caller
        else if ((direction == 1 && liftcallerdist < travelupaway) || (direction == -1 && liftcallerdist < traveldownaway)) {
            return 1;
        }
        // lift is moving in the opposite direction to the caller
        else if ((persondirection < 0 && direction == 1) || (persondirection > 0 && direction == -1)) {
            return buildingdistance + 1 - liftcallerdist;
        }
        // any other case
        else 
        return buildingdistance + 2 - liftcallerdist;
    }
    
    public void operate() {
        if (direction == 0) {
            if (passengers.size() > 0) {
                direction = setDirection(getDirectionOfFirstPassenger());
            }
            else if (queue.size() > 0) {
                direction = getDirectionToFirstQueuer();
                if (direction == 0) {
                    direction = setDirection(getDirectionOfFirstQueuer());
                }
            }    
            
        
    }
    
            
            if (anyBoarders() || anyAlighters()) {
            // boarding
            for (Iterator<Person> it = queue.iterator(); it.hasNext();) {
                Person queuer = it.next();
                if (queuer.levelMatchesCurrentLevel(level) && queuer.matchesCurrentDirection(direction)) {
                    board(queuer);
                    it.remove();
                }
            }
           
            //alighting
            for (Iterator<Person> it = passengers.iterator(); it.hasNext();) {
                Person passenger = it.next();
                if (passenger.destinationMatchesCurrentLevel(level)) {
                    alight(passenger);
                    it.remove();
                }
            }
            
            if (passengers.size() == 0) 
                direction = 0;
            
            
        }
        else 
        {
            
            moveLiftAndPassengers(direction);
            if (level == top || level == bottom)
                direction = 0;
        }
        
        
        
        
    }
    
    public boolean anyBoarders() {
        for (Person queuer : queue) {
            if (queuer.levelMatchesCurrentLevel(level) && queuer.matchesCurrentDirection(direction)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean anyAlighters() {
        for (Person passenger : passengers) {
            if (passenger.destinationMatchesCurrentLevel(level)) {
                return true;
            }
        }
        return false;
    }
    
    public void board(Person person) {
        passengers.add(person);
        person.setPersonAboardStatus(true);
        //queue.remove(person);
    }
    
    public void alight(Person person) {
        person.setPersonAboardStatus(false);
        //passengers.remove(person);
    }
    
    public int getDirectionToFirstQueuer() {
        return queue.get(0).getTravelDirection(level);
    }
    
    public int getDirectionOfFirstPassenger() {
        return passengers.get(0).getPersonDirection();
    }
    
    public int getDirectionOfFirstQueuer() {
        return queue.get(0).getPersonDirection();
    }
    
    public int setDirection(int persondirection) {
       if (persondirection < 0)
           return -1;
           else if (persondirection > 0)
           return 1;
           else
           return 0;
    }
    
    public void moveLiftAndPassengers(int direction) {
        if (direction == -1)  {
                level--;
                for (Person person : passengers)
                    person.changeLevel(-1);
                }
            else if (direction == 1) {
                level++;
                for (Person person : passengers)
                    person.changeLevel(+1);
                }
    }
    
    // probably unnecessary, because the direction HAS to be 0 for anybody to board or alight the lift
    private boolean personMatchesDirection(int persondirection) {
        return (persondirection < 0 && direction == -1) || (persondirection > 0 && direction == 1); 
    }
   
    @Override
    public String toString() {
        String stringlift = "Lift " + number + " ";
        for (int j = 0; j < bottom; j++) {
        stringlift += " ";
    }
    stringlift += "|";
    for (int i = bottom; i < top + 1; i++) {
        if (i == level)
        stringlift += "" + passengers.size();
        else
        stringlift += "-";
    }
    stringlift += "|";
    if (direction == -1)
        stringlift += " DOWN";
        else if 
        (direction == 1)
        stringlift += " UP";
    return stringlift;
    }
    
    public boolean isOnLift(int id) {
        for (Person passenger : passengers) {
            if (passenger.hasId(id))
                return true;
        }
        return false;  
        // another way to check this would be to check if the person's "aboard" boolean value is
        // true
    }
    
    public boolean isInQueue(int id) {
        for (Person queuer : queue) {
            if (queuer.hasId(id))
                return true;
        }
        return false; 
        
    }
    
    public void addQueuer(Person person) {
        queue.add(person);
    }
    
}
