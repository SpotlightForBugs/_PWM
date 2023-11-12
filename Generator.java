import java.security.SecureRandom;

public class Generator {

  // Characters for generating passwords
  private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
  private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String DIGITS = "0123456789";
  private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

  // Generate a password
  public String generate(
      int length,
      boolean mixedCase,
      boolean specialCharacters,
      boolean allowNumbers,
      boolean withDashes) {
    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder();

    // Create a character set based on criteria
    String characters = LOWERCASE_LETTERS;
    if (mixedCase) {
      characters += UPPERCASE_LETTERS;
    }
    if (allowNumbers) {
      characters += DIGITS;
    }
    if (specialCharacters) {
      characters += SPECIAL_CHARACTERS;
    }

    // Add characters to the password
    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(characters.length());
      char randomChar = characters.charAt(randomIndex);
      password.append(randomChar);

      // Add a dash every 4 characters if 'withDashes' is true and not at the end of the password
      if (withDashes && i % 4 == 3 && i != length - 1) {
        password.append('-');
      }
    }

    return password.toString();
  }

  public static void main(String[] args) {
    Generator passwordGenerator = new Generator();

    // Example usage:
    String password = passwordGenerator.generate(12, true, true, true, true);
    System.out.println("Generated Password: " + password);
  }
}
