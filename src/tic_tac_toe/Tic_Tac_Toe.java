/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Home PC
 */
public class Tic_Tac_Toe implements Serializable {
    static ArrayList <Integer> p1_Pos ;
    static ArrayList <Integer> p2_Pos ;
    static int p1_Wins;
    static int p2_Wins;
    static int Ties;
    static boolean continueGame = true;
    static char [][] gameBoard = generateFreshBoard();
    
    public static void main(String[] args) {
        p1_Wins = 0; p2_Wins = 0; Ties = 0;                        
        p1_Pos = new ArrayList<Integer>();
        p2_Pos = new ArrayList<Integer>(); 
        printGameBoard();
        while(continueGame){
            read("p1");
            read("p2");                     
        }
            System.out.println("Thank you for playing");                                
    }
    public static void checkWinner(){
        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);
        List <List> winning = new ArrayList <List>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);
        Scanner scan = new Scanner(System.in);
        String input;
        for(List l : winning) {
            if(p1_Pos.containsAll(l)){
                p1_Wins++;
                System.out.println("P1 has won the game");
                resetGame();
                System.out.println("Do you want to play again(Y/N)?:");
                input = scan.nextLine();
                if(input.equals("Y")) {continueGame = true;} else {continueGame = false;}
                return;
            } else if(p2_Pos.containsAll(l)){
                p2_Wins++;
                System.out.println("P2 has won the game");
                resetGame();  
                System.out.println("Do you want to play again(Y/N)?:");
                input = scan.nextLine();
                if(input.equals("Y")) {continueGame = true;} else {continueGame = false;}
                return;
            }else if (p1_Pos.size() + p2_Pos.size() == 9){
                Ties++;
                System.out.println("The game has ended in a tie");
                resetGame();
                System.out.println("Do you want to play again(Y/N)?:");
                input = scan.nextLine();
                if(input.equals("Y")) {continueGame = true;} else {continueGame = false;}
            }                       
        }
    }
    static char[][] generateFreshBoard(){
        char [][] newGameBoard = {{' ', '|', ' ', '|', ' '}, 
                               {'-', '+', '-', '+', '-'},
                               {' ', '|', ' ', '|', ' '},
                               {'-', '+', '-', '+', '-'},
                               {' ', '|', ' ', '|', ' '}, };
          return newGameBoard;
    }
    static void resetGame(){
        gameBoard = generateFreshBoard();
        p1_Pos = new ArrayList<Integer>();
        p2_Pos = new ArrayList<Integer>();                     
    }
    public static void printGameBoard()
    {
        for(char[] row: gameBoard){
            for(char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }
    public static void read(String player){
        if(!continueGame) return;
        Scanner scan = new Scanner(System.in);
        System.out.println("P1 placement: 1-9 | SAVE fileName | LOAD fileName");
        System.out.println(String.format("P1 won: %s times | P2 won: %s times | Ties: %s",p1_Wins,p2_Wins,Ties));
        String text = scan.nextLine();
        
          if(text.startsWith("SAVE") || text.startsWith("LOAD")){
                String fileName = text.split(" ")[1];
                if(text.startsWith("LOAD")){
                   Load(fileName); 
                   System.out.println("File has been loaded");
                   printGameBoard();
                }
                else{
                   Save(fileName); 
                   System.out.println("File has been saved");
                }
                
                return;
            }
          
            if(text.startsWith("QUIT")){
                continueGame = false;
                return;
            }
          
            try{
             int pos = Character.getNumericValue(text.charAt(0));
            
          
            while(p1_Pos.contains(pos) || p2_Pos.contains(pos)){
                System.out.println("Enter correct position");
                pos = scan.nextInt();       
            }
            place(pos, player);
            printGameBoard();
            checkWinner();
            } catch(Exception e){
                System.out.println("Wrong value entered!");
                
            }
    }
    static void Save(String fileName){
        try (FileOutputStream f = new FileOutputStream(fileName);
            ObjectOutput s = new ObjectOutputStream(f)) {
//            s.writeObject(Tic_Tac_Toe.p1_Pos);
//            s.writeObject(Tic_Tac_Toe.p2_Pos);
//            s.writeObject(Tic_Tac_Toe.p1_Wins);
//            s.writeObject(Tic_Tac_Toe.p2_Wins);
//            s.writeObject(Tic_Tac_Toe.Ties);
            s.writeObject(Tic_Tac_Toe.gameBoard);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
     static void Load(String fileName) {
        try (FileInputStream in = new FileInputStream(fileName);
                ObjectInputStream s = new ObjectInputStream(in)) {
            Tic_Tac_Toe.p1_Pos = (ArrayList) s.readObject();
            Tic_Tac_Toe.p2_Pos = (ArrayList) s.readObject();
            Tic_Tac_Toe.p1_Wins = (Integer) s.readObject();
            Tic_Tac_Toe.p2_Wins = (Integer) s.readObject();
            Tic_Tac_Toe.Ties = (Integer) s.readObject();
            Tic_Tac_Toe.gameBoard = (char[][]) s.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void place (int pos, String user){
        char symbol = ' ';
        if(user.equals("p1")){
            symbol = 'X';
            p1_Pos.add(pos);
        }else if(user.equals("p2")){
            symbol = 'O';
            p2_Pos.add(pos);
        }
        switch (pos) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }
    }
}