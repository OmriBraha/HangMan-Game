import java.util.Random;

// Game Logics class
public class Logic {
	private int lives = 7;
	private int totalGuesses;
	private String hiddenWord;
	private WordsReader words = new WordsReader();
	private String astric;
	private boolean correctGuess;
	
	// Constructor
	public Logic() {
		this.setHiddenWord(shuffle());
		this.setTotalGuesses(totalGuesses);
		this.astric = new String(new char[hiddenWord.length()]).replace("\0", "*");
		this.setCorrectGuess(correctGuess);
	}

	
	// Getters & Setters
	public int getTotalGuesses() {
		return totalGuesses;
	}

	public void setTotalGuesses(int totalGuesses) {
		this.totalGuesses = totalGuesses;
	}

	public String getHiddenWord() {
		return hiddenWord;
	}

	public void setHiddenWord(String hiddenWord) {
		this.hiddenWord = hiddenWord;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public String getAstric() {
		return astric;
	}

	public void setAstric(String astric) {
		this.astric = astric;
	}
	

	public boolean isCorrectGuess() {
		return correctGuess;
	}

	public void setCorrectGuess(boolean correctGuess) {
		this.correctGuess = correctGuess;
	}

	
	// Restart game method
	public void restartGame() {
		this.setHiddenWord(shuffle());
		this.astric = new String(new char[hiddenWord.length()]).replace("\0", "*");
	}
	
	// Shuffles words method
	private String shuffle() {
		return words.getData().get(new Random().nextInt(words.getData().size()));
	}
	
	// Check user's guess method
	public String checkGuess(String guess) {
		String newGuess = "";
		this.setCorrectGuess(false);
		for ( int i = 0; i < this.getHiddenWord().length(); i++ ) {
			if (this.getHiddenWord().toLowerCase().charAt(i) == guess.toLowerCase().charAt(0) ) {
				newGuess += guess.charAt(0);
				this.setCorrectGuess(true);
			} else if ( this.astric.charAt(i) != '*' ) {
				newGuess += this.getHiddenWord().charAt(i);
			} else {
				newGuess += "*";
			}
		}
		this.setCorrectGuess(correctGuess);
		this.setAstric(newGuess);
		return newGuess;
	}
}
