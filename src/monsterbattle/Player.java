
package monsterbattle;

import java.util.ArrayList;
import java.util.List;


class Player {
    private final String name;
    private final List<Monster> monsters;
    private int winningStreak;
    

    public Player(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
        this.winningStreak=0;
        
    }

    public String getName() { return name; }
    public List<Monster> getMonsters() { return monsters; }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }
    
    public int getConsecutiveWins(){ return winningStreak; }
    
    public void updateConsecutiveWins(){
        winningStreak++;
    }
    public void resetConsecutiveWins(){
        winningStreak = 0;
    }
}

