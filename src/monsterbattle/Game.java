/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monsterbattle;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class Game {

    private MyHashMap<String, Monster> allMonstersHash;
    private TreeMap<String, Monster> sortedMonstersTree;
    private Player player1;
    private Player player2;

    public Game() {
        allMonstersHash = new MyHashMap<>();
        createMonstersFromFile("monsters.txt");
    }

    private void createMonstersFromFile(String filename) {
        
        try (Scanner scanner = new Scanner(new File(filename))) {
            
            while (scanner.hasNextLine()) {
                
                String id = scanner.nextLine().trim();
                String name = scanner.nextLine().trim();
                int health = Integer.parseInt(scanner.nextLine().trim());
                int attack = Integer.parseInt(scanner.nextLine().trim());
                int defense = Integer.parseInt(scanner.nextLine().trim());
                int level = Integer.parseInt(scanner.nextLine().trim());

                Monster monster = new Monster(id, name, health, attack, defense, level);
                allMonstersHash.put(id, monster);
                
            }
            
        } catch (IOException e) {
            
            System.err.println("Error : " + e.getMessage());
            
        }
        
    }

    public void startGame() {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Player 1 name: ");
        player1 = new Player(scanner.nextLine());

        System.out.print("Enter Player 2 name: ");
        player2 = new Player(scanner.nextLine());

        
        System.out.println(player1.getName() + ", Which feature would you like to sort by? (Health, Attack, Defense, Level, Name)");
        String player1Choice = scanner.nextLine().toLowerCase();
        sortAndShowAvailableMonsters(player1Choice);
        System.out.println();
        selectMonsters(player1, scanner);

        
        System.out.println(player2.getName() + ", Which feature would you like to sort by? (Health, Attack, Defense, Level, Name)");
        String player2Choice = scanner.nextLine().toLowerCase();
        sortAndShowAvailableMonsters(player2Choice);
        System.out.println();
        selectMonsters(player2, scanner);

        
        while (true) {
            play(scanner);

            if (player1.getMonsters().isEmpty() || player2.getMonsters().isEmpty()) {
                System.out.println("Game Over!");
                break;
            }
            if (player1.getConsecutiveWins() == 3) {
                System.out.println(player1.getName() + " 3 wins in a row! Game Over!");
                break;
            } else if (player2.getConsecutiveWins() == 3) {
                System.out.println(player2.getName() + " 3 wins in a row! Game Over!");
                break;
            }
        }
    }

    private void sortAndShowAvailableMonsters(String attribute) {
        
        List<Monster> monsters = new ArrayList<>(allMonstersHash.values());
        
        List<Monster> sortedMonsters = sortMonsters(monsters, attribute);
        System.out.println("According to the  "+ attribute + " list of the monsters: ");
        for (Monster monster : sortedMonsters) {
            System.out.println(monster);
        }
    }
    
    private List<Monster> sortMonsters(List<Monster> monsters, String attribute) {
        List<Monster> sortedMonsters = new ArrayList<>(monsters);

        
        for (int i = 0; i < sortedMonsters.size(); i++) {
            for (int j = i + 1; j < sortedMonsters.size(); j++) {
                Monster m1 = sortedMonsters.get(i);
                Monster m2 = sortedMonsters.get(j);

                int comparisonResult = compareMonsters(m1, m2, attribute);

                
                if (comparisonResult < 0) {
                    sortedMonsters.set(i, m2);
                    sortedMonsters.set(j, m1);
                }
            }
        }

        return sortedMonsters;
    }
    private int compareMonsters(Monster m1, Monster m2, String attribute) {
        
        switch (attribute) {
            case "health":
                return Integer.compare(m1.getHealth(), m2.getHealth());
            case "attack":
                return Integer.compare(m1.getAttack(), m2.getAttack());
            case "defense":
                return Integer.compare(m1.getDefense(), m2.getDefense());
            case "level":
                return Integer.compare(m1.getLevel(), m2.getLevel());
            case "name":
                return m1.getName().compareTo(m2.getName());
            default:
                throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }
        
    }

    private void selectMonsters(Player player, Scanner scanner) {
        System.out.print("Now monsters ranked by choice.  ");
        System.out.println("Select 10 monsters: ");
        System.out.println("Enter  all the Monsters ID you want to choose: ");
        
        for (int i = 0; i < 10; i++) {
            String id = scanner.nextLine();
            if (allMonstersHash.containsKey(id)) {
                player.addMonster(allMonstersHash.get(id));
                allMonstersHash.remove(id);
            } else {
                System.out.println("Invalid Monster ID. Try again..");
                i--;
            }
            
        }
        System.out.println();
    }

    private void play(Scanner scanner) {
        
        Monster player1Monster = selectBattleMonster(player1, scanner);
        Monster player2Monster = selectBattleMonster(player2, scanner);

        if (player1Monster == null || player2Monster == null) {
            return;
        }

        
        int player1AllOfPower = player1Monster.getHealth() + player1Monster.getAttack() + player1Monster.getDefense();
        int player2AllOfPower = player2Monster.getHealth() + player2Monster.getAttack() + player2Monster.getDefense();

        if (player1AllOfPower > player2AllOfPower) {
            
            System.out.println();
            System.out.println(player1.getName() + " WIN!");
            System.out.println();
            player1Monster.levelUp();
            player2.removeMonster(player2Monster);
            player1.updateConsecutiveWins();
            player2.resetConsecutiveWins();

        } else if (player2AllOfPower > player1AllOfPower) {
            
            System.out.println();
            System.out.println(player2.getName() + " WIN!");
            System.out.println();
            player2Monster.levelUp();
            player1.removeMonster(player1Monster);
            player2.updateConsecutiveWins();
            player1.resetConsecutiveWins();

        } else {
            
            System.out.println();
            System.out.println("TIE!");
            System.out.println();
            player1.resetConsecutiveWins();
            player2.resetConsecutiveWins();
        }

        
        System.out.println(player1.getName() + " monsters:");
            for (Monster monster : player1.getMonsters()) {
            System.out.println(monster);
        }

        System.out.println(player2.getName() + " monsters:");
            for (Monster monster : player2.getMonsters()) {
            System.out.println(monster);
        }
    }
    

    private Monster selectBattleMonster(Player player, Scanner scanner) {
        
        System.out.println(player.getName() + ", select monster for battle:");
        System.out.println("->");
        for (Monster monster : player.getMonsters()) {
            System.out.println(monster);
        }

        String id = scanner.nextLine();

        for (Monster monster : player.getMonsters()) {
            if (monster.getId().equals(id)) {
                return monster;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        
        Game game = new Game();
        game.startGame();
        
    }
}
