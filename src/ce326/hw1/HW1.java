/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw1;
import java.util.Scanner;

/**
 *
 * @author Panagiotis Tsatanis
 */
public class HW1 {
    public static void main(String[] args) {
        Scanner Sc = new Scanner(System.in);
        String Choice;
        String Word;
        Trie CompressedTrie = new Trie();
        while( true ) {
            System.out.println("?: ");
            
            if( Sc.hasNext() == false ) { break; }
            Choice = Sc.next();
            
            if(Choice.equals("-i")) {
                Word = Sc.next();
                if( CompressedTrie.InsertNode(Word.toLowerCase()) != 0 ) {
                    System.out.println("ADD " + Word + " OK");
                }
                else {
                    System.out.println("ADD " + Word + " NOK");
                }
                
            }
            else if(Choice.equals("-r")) {
                Word = Sc.next();
                if( CompressedTrie.DeleteNode(Word.toLowerCase()) !=0 ) {
                    System.out.println("RMV " + Word + " OK");
                }
                else {
                    System.out.println("RMV " + Word + " NOK");
                }
            }
            else if(Choice.equals("-f")) {
                Word = Sc.next();
                if( CompressedTrie.SearchNode(Word.toLowerCase()) == true ) {
                    System.out.println("FND " + Word + " OK");
                }
                else {
                    System.out.println("FND " + Word + " NOK");
                }
            }
            else if(Choice.equals("-p")) {
                System.out.print("PreOrder: ");
                CompressedTrie.PreOrder(CompressedTrie.getRoot());
                System.out.println();
            }
            else if(Choice.equals("-d")) {
                System.out.println("\n***** Dictionary *****");
                StringBuffer Buffer = new StringBuffer();
                CompressedTrie.Dictionary(CompressedTrie.getRoot(), Buffer);
                System.out.println();
            }
            else if(Choice.equals("-w")) {
                Word = Sc.next();
                int RequiredDistance = Sc.nextInt();
                StringBuffer Buffer = new StringBuffer();
                System.out.println("\nDistant words of " + Word + " (" + RequiredDistance + "):");
                CompressedTrie.DistantWords(CompressedTrie.getRoot(), Buffer, Word.toLowerCase(), RequiredDistance);
                System.out.println();
            }
            else if(Choice.equals("-s")) {
                Word = Sc.next();
                System.out.println("\nWords with suffix " + Word + ":");
                StringBuffer Buffer = new StringBuffer();
                CompressedTrie.WordsWithSuffix(CompressedTrie.getRoot(),Buffer,Word.toLowerCase());
                System.out.println();
            }
            else if(Choice.equals("-q")) {
                System.out.println("Bye bye!");
                break;
            }
        }
    }
}
