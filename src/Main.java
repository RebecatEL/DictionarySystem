import java.io.*;
import java.net.URL;
import java.util.*;


public class Main {
    public static void main(String[] args) throws Exception {

        Dictionary d1 = new Dictionary();
        Scanner sc = new Scanner(System.in);
        // Preload wordList.txt
        URL url = Main.class.getResource("wordList.txt");
        assert url != null;
        File preload = new File(url.getPath());
        Scanner sctxt = new Scanner(preload);
        if (preload.exists()){
            while (sctxt.hasNextLine()){
                String s = sctxt.nextLine();
                if (!isNumeric(s)){
                    d1.add(s);
                }
            }
        }else System.out.println("File not exist.");
        sctxt.close();

        // Menu
        boolean menu_flag = true;
        do {
            System.out.println("*** Dictionary system ***");
            System.out.println("1. Add new word");
            System.out.println("2. Delete word");
            System.out.println("3. Get meaning");
            System.out.println("4. Dictionary list");
            System.out.println("5. Spell check a text file");
            System.out.println("6. Exit");
            System.out.println("");
            System.out.println("Please input option number: ");
            String choice = sc.nextLine();
            isNumeric(choice);
            while (!isNumeric(choice) || Integer.parseInt(choice) <1 || Integer.parseInt(choice) >6) {
                System.out.println("Invalid input. Please input number between 1-6: ");
                choice = sc.nextLine();
                isNumeric(choice);
            }
            int option = Integer.parseInt(choice);
            boolean chk_exist;
            switch (option) {
                case 1 -> { // Add new word
                    boolean chk_add;
                    String new_word;
                    System.out.println("Please enter the word you want to add: ");
                    new_word = sc.nextLine();
                    chk_exist = d1.exists(new_word);
                    while (chk_exist) {
                        System.out.println("The word is existed. Please enter the other word: ");
                        new_word = sc.nextLine();
                        chk_exist = d1.exists(new_word);
                    }
                    System.out.println("Please enter the meaning of the word: ");
                    String new_meaning = sc.nextLine();
                    chk_add = d1.add(new_word, new_meaning);
                    // error handling
                    if (!chk_add) System.out.println("Cannot add new word.");
                    System.out.println("");
                }
                case 2 -> { // Delete word
                    String del_word;
                    boolean chk_del;
                    System.out.println("Please enter a word to delete: ");
                    del_word = sc.nextLine();
                    chk_del = d1.delete(del_word);
                    while (!chk_del) {
                        System.out.println("Delete not successful. Please enter another word: ");
                        del_word = sc.nextLine();
                        chk_del = d1.delete(del_word);
                    }
                    System.out.println("Delete successful!");
                    System.out.println("");
                }
                case 3 -> { // Get meaning
                    String get_word;
                    String chk_word;
                    System.out.println("Please enter a word to check the meaning: ");
                    get_word = sc.nextLine();
                    chk_word = d1.getMeaning(get_word);
                    while (Objects.equals(chk_word, "N/A")){
                        System.out.println("The word is not exist. Please enter another word: ");
                        get_word = sc.nextLine();
                        chk_word = d1.getMeaning(get_word);
                    }
                    System.out.println("Meaning: " + chk_word);
                }
                case 4 -> {  // Dictionary List
                    System.out.println(d1.printWordList());
                }
                case 5 -> {  // Spell check a text file
                    ArrayList<String> temp_word = new ArrayList<>();
                    System.out.println("Please enter the file name: ");
                    String file_name = sc.nextLine();
                    URL spellurl = Main.class.getResource(file_name);
                    assert spellurl != null;
                    File file_check = new File(spellurl.getPath());

                    Scanner spell_chk = new Scanner(file_check);
                    String str;
                    while (spell_chk.hasNext()){
                        str = spell_chk.next().toLowerCase().replaceAll("[,.]","");
                        chk_exist = d1.exists(str);
                        if (!chk_exist){
                            temp_word.add(str);
                        }
                    }
                    // print words that are not exist in dictionary
                    System.out.println("Below words are not existed in Dictionary: ");
                    for (String word: temp_word){
                        System.out.println(word);
                    }
                    System.out.println("");
                    spell_chk.close();
                }
                case 6 -> {
                    menu_flag = false;
                    System.exit(0);
                }
                default -> {
                    System.out.println("Invalid menu option, please enter number between 1-6.");
                }
            }
        }while(menu_flag);
    }

    public static boolean isNumeric(String s){
        try{
            int i = Integer.parseInt(s);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}