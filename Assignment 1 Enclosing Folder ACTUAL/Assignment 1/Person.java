public class Person {
    private int id;
    private String name;
    private int level;
    private int destination;
    private boolean aboard;
    
    public Person(int id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.destination = level;
        this.aboard = false;
        
        
    }
    
    public boolean hasId(int id) {
        return this.id == id;
        
    }
    
    public boolean matchesCurrentDirection(int direction) {
        int persondirection = destination - level;
        if (persondirection > 0 && direction == 1)
            return true;
            else if (persondirection < 0 && direction == -1) 
            return true;
            else 
            return false;
    }
    
    public void changeLevel(int levelAdder) {
        level += levelAdder;
    }
    
    public boolean getPersonAboardStatus(boolean status) {
        return aboard == status;
    }
    
    public void setPersonAboardStatus(boolean status) {
        aboard = status;
    }
    
    public boolean destinationMatchesCurrentLevel(int level) {
        return destination == level;
    }
    
    public int getPersonDirection() {
        return destination - level;
    }
    
    public int getTravelDirection(int liftlevel) {
        if (liftlevel < level)
            return 1;
        else if (liftlevel > level)
            return -1;
            else 
            return 0;
    }
    
    public boolean levelMatchesCurrentLevel(int level) {
        return this.level == level;
    }
    
    public boolean isOnEntrance(int entrance) {
        return level == entrance;
    }
    
    public void setDestination(int destination) {
        this.destination = destination;
    }
    //@Override
    public String toString() {
        String s = "";
        if (aboard == true)
            s += name + "(" + id + ")" + " on level " + level + " going to level " + destination;
            else if (level != destination && aboard == false)
            s += name + "(" + id + ")" + " on level " + level + " waiting to go to level " + destination;
            else
            s += name + "(" + id + ")" + " on level " + level;
        return s;
    }
    
    public int getId() {
        return id;
        
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getDestination() {
        return destination;
    }
    
    // NEEDS WORK/ FOR TESTING:
    public void gettingOnLift() {
        aboard = true;
    }
    
    
}