
package hospital;

import hospital.Roster;

public class Hospital {

    public static void main(String[] args) {

        Roster r = new Roster('A');
        r.readShiftSystem();
        r.readPersonnelCharacteristics();
        r.readMonthlyRosterRules();
        r.readCyclicRoster();
    }
    
}
