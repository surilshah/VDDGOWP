package finalyearproject;

import java.util.*;
import java.io.*;

public class Tagger {

    String c;
    Identifier x = new Identifier();

    public Tagger(String str) {
        c = str;
    }

    public String[][] tag() throws IOException {
        SpeechToText st = new SpeechToText();
        String tag, line, temp;
        int[] a;
        String dir = System.getProperty("user.dir");
        Scanner sc = new Scanner(System.in);
        String word[] = c.split(" ");
        String[][] sentence = new String[word.length][2];
        for (int i = 0; i < word.length; i++) {
            BufferedReader in = null;
            temp = word[i];
            int result = 0;
            if (word[i].equalsIgnoreCase("zero")) {
                result += 0;
            } else if (word[i].equalsIgnoreCase("one") || word[i].equalsIgnoreCase("first")) {
                result += 1;
            } else if (word[i].equalsIgnoreCase("two") || word[i].equalsIgnoreCase("second")) {
                result += 2;
            } else if (word[i].equalsIgnoreCase("three") || word[i].equalsIgnoreCase("third")) {
                result += 3;
            } else if (word[i].equalsIgnoreCase("four") || word[i].equalsIgnoreCase("fourth")) {
                result += 4;
            } else if (word[i].equalsIgnoreCase("five") || word[i].equalsIgnoreCase("fifth")) {
                result += 5;
            } else if (word[i].equalsIgnoreCase("six") || word[i].equalsIgnoreCase("sixth")) {
                result += 6;
            } else if (word[i].equalsIgnoreCase("seven") || word[i].equalsIgnoreCase("seventh")) {
                result += 7;
            } else if (word[i].equalsIgnoreCase("eight") || word[i].equalsIgnoreCase("eight")) {
                result += 8;
            } else if (word[i].equalsIgnoreCase("nine") || word[i].equalsIgnoreCase("ninth")) {
                result += 9;
            } else if (word[i].equalsIgnoreCase("ten")) {
                result += 10;
            } else if (word[i].equalsIgnoreCase("eleven")) {
                result += 11;
            } else if (word[i].equalsIgnoreCase("twelve")) {
                result += 12;
            } else if (word[i].equalsIgnoreCase("thirteen")) {
                result += 13;
            } else if (word[i].equalsIgnoreCase("fourteen")) {
                result += 14;
            } else if (word[i].equalsIgnoreCase("fifteen")) {
                result += 15;
            } else if (word[i].equalsIgnoreCase("sixteen")) {
                result += 16;
            } else if (word[i].equalsIgnoreCase("seventeen")) {
                result += 17;
            } else if (word[i].equalsIgnoreCase("eighteen")) {
                result += 18;
            } else if (word[i].equalsIgnoreCase("nineteen")) {
                result += 19;
            } else if (word[i].equalsIgnoreCase("twenty")) {
                result += 20;
            }
            if (result != 0) {
                String strI = String.valueOf(result);
                word[i] = strI;
            } else if (word[i].equalsIgnoreCase("colour")) {
                word[i] = "color";
            }
            
            DBManager db = new DBManager();
            sentence[i][0] = word[i];
            sentence[i][1] = db.getTag(word[i]);
            db.close();

            if (sentence[i][1].equals("VB")) {
                x.selectAction(temp);
            }
        }

        a = x.returnAction();
        for (int y = 0; y < sentence.length; y++) // combines table one or table 1 to ---> table1
        {
            if ((a[1] == 1 || a[2] == 1) && sentence[y][1].equalsIgnoreCase("Noun") && sentence[y + 1][1].equalsIgnoreCase("Number")) {
                sentence[y][0] += sentence[y + 1][0];
                sentence[y][1] = "ID";
                sentence[y + 1][0] = "";
                sentence[y + 1][1] = "";
            } else if (sentence[y][1].equalsIgnoreCase("Words") && (sentence[y + 1][0].equalsIgnoreCase("Area") || sentence[y + 1][0].equalsIgnoreCase("Box"))) //text area 1 ----> textarea1
            {
                if ((a[1] == 1 || a[2] == 1) && sentence[y + 2][1].equalsIgnoreCase("Number")) {
                    sentence[y][0] = sentence[y][0] + sentence[y + 1][0] + sentence[y + 2][0];
                    sentence[y][1] = "ID";
                    sentence[y + 1][0] = sentence[y + 1][1] = sentence[y + 2][0] = sentence[y + 2][1] = "";
                } else {
                    sentence[y][0] = sentence[y][0] + sentence[y + 1][0];
                    sentence[y][1] = "Noun";
                    sentence[y + 1][0] = sentence[y + 1][1] = "";
                }
            }
        }

        return sentence;
    }
}
