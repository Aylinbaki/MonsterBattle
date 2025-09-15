
package monsterbattle;

class Monster {
    private final String id;
    private final String name;
    private int health;
    private int attack;
    private int defense;
    private int level;

    public Monster(String id, String name, int health, int attack, int defense, int level) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public int getHealth() { return health; }

    public int getAttack() { return attack; }

    public int getDefense() { return defense; }

    public int getLevel() { return level; }

    public void levelUp() {
        this.level++;
        this.health += 3;
        this.attack += 1;
        this.defense += 2;
    }

    @Override
    public String toString() {
        return id + ": " + name + " (Level:"+ level +"  Health:"+health +"  Attack:"+attack+"  Defense:"+defense+")";
        
    }
}