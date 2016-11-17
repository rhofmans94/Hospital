
package hospital;

public class Shift {
    
    private String shiftId;
    private int startTime;
    private int endTime;
    private int requiredNurses;

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getRequiredNurses() {
        return requiredNurses;
    }

    public void setRequiredNurses(int requiredNurses) {
        this.requiredNurses = requiredNurses;
    }
    
    
}
