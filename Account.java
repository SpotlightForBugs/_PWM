// Account part of the password manager
// Last modified: 9.11.2023

import java.io.*;

/** */
public class Account implements ComparableContent<Account> {
  private BinarySearchTree<Entry> enTree; // contains all the username/password pairs
  // master password for the account
  private String masterPassword;

  private String hashedPassword;
  // account name
  private String accountName;

  private List<Entry> entryList = new List<Entry>();

  public Account(String pAccountName, String pMasterPassword) {
    // constructor
    this.accountName = pAccountName;
    this.masterPassword = pMasterPassword;
    this.hashedPassword = AccountCrypt.generate(pMasterPassword, pAccountName);
    this.enTree = new BinarySearchTree<Entry>();
  }

  @Override
  public boolean isGreater(Account pContent) {
    // account names are unique
    return this.accountName.compareTo(pContent.accountName) > 0;
  }

  @Override
  public boolean isEqual(Account pContent) {
    return this.hashedPassword.equals(pContent.hashedPassword);
  }

  @Override
  public boolean isLess(Account pContent) {
    return this.accountName.compareTo(pContent.accountName) < 0;
  }

  public String getAccountName() {
    return accountName;
  }

  public boolean isCorrectPassword(String pPassword) {
    // returns the password if the password is correct, null otherwise
    return AccountCrypt.verify(pPassword, this.hashedPassword, this.accountName);
  }

  public void addEntry(String pUsername, String pPassword, String pScope) {
    // adds an entry to the account
    Entry newEntry = new Entry(pUsername, pPassword, pScope, this);
    this.enTree.insert(newEntry);
  }

  public Entry[] getEntriesAsArray() {

    List<Entry> entries = getEntries();
    // count the number of entries using a while loop
    int count = 0;
    entries.toFirst();
    while (entries.hasAccess()) {
      count++;
      entries.next();
    }
    // create an array of the correct size
    Entry[] entryArray = new Entry[count];
    // add each entry to the array using a for loop
    entries.toFirst();
    for (int i = 0; i < count; i++) {
      entryArray[i] = entries.getContent();
      entries.next();
    }
    return entryArray;
  }

  private List<Entry> getEntries() {
    this.entryList = new List<Entry>();
    getEntries(this.enTree);
    return this.entryList;
  }

  private void getEntries(BinarySearchTree<Entry> enTree) {

    // recursively traverse the tree and add each entry to the array

    this.entryList.append(enTree.getContent());
    if (enTree.getLeftTree() != null) {
      getEntries(enTree.getLeftTree());
    }
    if (enTree.getRightTree() != null) {
      getEntries(enTree.getRightTree());
    }
  }

  public void debug_print() {
    System.out.println("Account: " + this.accountName + " " + this.masterPassword);
    Entry[] entries = getEntriesAsArray();
    for (Entry entry : entries) {
      System.out.println(
          entry.getUsername() + " " + entry.getPassword() + " " + entry.getScopeAsString());
    }
  }

  public void deleteEntry(Entry entry) {
    this.enTree.remove(entry);
  }

  public void saveAsJSON(String fileName) {
    List<Entry> entries = getEntries();
    StringBuilder json =
        new StringBuilder(
            "{\n"
                + "  \"accountName\": \""
                + this.accountName
                + "\",\n"
                + "  \"masterPassword\": \""
                + this.masterPassword
                + "\",\n"
                + "  \"entries\": [\n");

    entries.toFirst();
    while (entries.hasAccess()) {
      json.append("    {\n" + "      \"username\": \"")
          .append(entries.getContent().getUsername())
          .append("\",\n")
          .append("      \"password\": \"")
          .append(entries.getContent().getPassword())
          .append("\",\n")
          .append("      \"scope\": \"")
          .append(entries.getContent().getScopeAsString())
          .append("\"\n")
          .append("    }");

      entries.next();
      if (entries.hasAccess()) {
        json.append(",\n");
      } else {
        json.append("\n");
      }
    }

    json.append("  ]\n").append("}");

    System.out.println(json.toString());

    try {
      FileWriter fileWriter = new FileWriter(fileName);
      fileWriter.write(json.toString());
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
