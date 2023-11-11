// Account part of the password manager
// Last modified: 9.11.2023

/** */
public class Account implements ComparableContent<Account> {
  private BinarySearchTree<Entry> enTree; //contains all the username/password pairs
  // master password for the account
  private String masterPassword;
  // account name
  private String accountName;

  public Account(String pAccountName, String pMasterPassword) {
    // constructor
    this.accountName = pAccountName;
    this.masterPassword = pMasterPassword;
    this.enTree = new BinarySearchTree<Entry>();
  }

  @Override
  public boolean isGreater(Account pContent) {
    //account names are unique
    return this.accountName.compareTo(pContent.accountName) > 0;
  }

  @Override
  public boolean isEqual(Account pContent) {
    return this.accountName.equals(pContent.accountName) && this.masterPassword.equals(pContent.masterPassword);
  }

  @Override
  public boolean isLess(Account pContent) {
    return this.accountName.compareTo(pContent.accountName) < 0;
  }

  public String getAccountName() {
    return accountName;
  }

  public boolean isCorrectPassword(String pPassword) {
    //returns the password if the password is correct, null otherwise
      return pPassword.equals(this.masterPassword);
  }

  public void addEntry(String pUsername, String pPassword, String pScope) {
    //adds an entry to the account
    Entry newEntry = new Entry(pUsername, pPassword, pScope);
    this.enTree.insert(newEntry);
  }


  public Entry[] getEntriesAsArray() {

    List<Entry> entries = getEntries();
    //count the number of entries using a while loop
    int count = 0;
    entries.toFirst();
    while (entries.hasAccess()) {
        count++;
        entries.next();
    }
    //create an array of the correct size
    Entry[] entryArray = new Entry[count];
    //add each entry to the array using a for loop
    entries.toFirst();
    for (int i = 0; i < count; i++) {
        entryArray[i] = entries.getContent();
        entries.next();
    }
    return entryArray;

  }

  private List<Entry> getEntries() {

    return getEntries(this.enTree, new List<>());
  }

  private List<Entry> getEntries(BinarySearchTree<Entry> enTree, List<Entry> entryList) {

    //recursively traverse the tree and add each entry to the array
    if (enTree == null) {
        return entryList;
    }
    entryList.append(enTree.getContent());
    if (enTree.getLeftTree() != null) {
        getEntries(enTree.getLeftTree(), entryList);
    }
    if (enTree.getRightTree() != null) {
        getEntries(enTree.getRightTree(), entryList);
    }
    return entryList;
  }
}
