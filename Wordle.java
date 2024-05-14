import java.util.*;

public class Wordle {
    public static void main(String[] args) {
        String[] wordbank = {"HELLO" , "WORLD" , "LEARN" , "VIDHI" , "CRAMP"};
        String answer = wordbank[new Random().nextInt(wordbank.length)].toUpperCase();
        WordleBoard board = new WordleBoard(wordbank);
        Scanner in = new Scanner(System.in);
        Display display = new Display(board,in);
        while(!board.isgameover()){
            display.print();
            display.promptguess();
        }
        display.print();
        if (board.didwin()) {
            System.out.println("Congratulations, you guessed correctly");
        } else {
            System.out.println("Sorry, you did not guess correctly");
        }
        System.out.println("The answer was " + board.getword());
        in.close();
    }
    public static class Display{
        WordleBoard board;
        Scanner in;
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public Display(WordleBoard board, Scanner input) {
            this.board = board;
            this.in = input;
        }
        public void print() {
            printGuesses();
            printBlanks();
            //clear();
        }
        
        public String promptguess(){
            while(true){
                System.out.println("Enter a word : ");
                String guess = in.nextLine().toUpperCase();
                if(guess.length() != board.getword().length()){
                    System.out.println("Enter a word of length "+ board.getword().length());
                }else{
                    board.guess(guess);
                    return guess;
                }
            }
        }
        public void printGuesses(){
            StringBuilder b = new StringBuilder();
            for(String s : board.guesses){
                for(int i =0 ; i<s.length() ; i++){
                    String ans = board.getword();
                    char c = s.charAt(i);
                    if(ans.charAt(i)==c){
                        b.append(ANSI_GREEN + c + ANSI_RESET);
                    }
                    else if (ans.contains(Character.toString(c))){
                        b.append(ANSI_YELLOW + c + ANSI_RESET);
                    }else{
                        b.append(c);
                    }
                    b.append("|");
                }
                b.setLength((b.length() - 1));
            System.out.println(b.toString());
            b = new StringBuilder();
                
            }
        }
        public void printBlanks() {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < board.getattemptsremainning(); i++) {
                for (int j = 0; j < board.getword().length(); j++) {
                    b.append("_|");
                }
                b.setLength(b.length() - 1);
                System.out.println(b.toString());
                b = new StringBuilder();
            }
        }
    }
    public static class WordleBoard{
        private int rowLength;
        private int attemptsremaining;
        private int attempts;
        private String word;
        List<String> guesses;
        public WordleBoard(String[] words){
            this.word = words[new Random().nextInt(words.length)].toUpperCase();
            rowLength=word.length();
            attempts=6;
            attemptsremaining=attempts;
            guesses = new ArrayList<String>();
            System.out.println(word);
        }
        public int getrowlength(){
            return rowLength;
        }
        public void setrowlength(int rowlength){
            this.rowLength=rowLength;
        }
        public int getattemptsremainning(){
            return attemptsremaining;
        }
        public void setattemptsremainning(int attemptsremaining){
            this.attemptsremaining=attemptsremaining;
        }
        public int getattempts(){
            return attempts;
        }
        public void setattempts(int attempts){
            this.attempts=attempts;
        }
        public String getword(){
            return word;
        }
        public void setword(String word){
            this.word=word;
        }
        public List<String> getGuesses() {
            return guesses;
        }
        public void setGuesses(List<String> guesses) {
            this.guesses = guesses;
        }
        public boolean isgameover(){
            return attemptsremaining==0 || didwin();
        }
        public boolean didwin(){
            return guesses.contains(word);
        }   
        public void guess(String str){
            if(word.length()==str.length()){
                guesses.add(str);
                attemptsremaining--;
            }
        }
     }
}
