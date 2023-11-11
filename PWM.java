public class PWM {

    //has a BinarySearchTree of Accounts
    private BinarySearchTree<Account> accounts = new BinarySearchTree<Account>();


    public List<Account> getAccounts() {

        //recursively traverse the tree and add each account to the array, using an overloaded method getAccounts
        return getAccounts(accounts, new List<Account>());
    }


    private List<Account> getAccounts(BinarySearchTree<Account> pTree, List<Account> pList) {
        //recursively traverse the tree and add each account to the array
        if (pTree == null) {
            return pList;
        }
        pList.append(pTree.getContent());
        if (pTree.getLeftTree() != null) {
            getAccounts(pTree.getLeftTree(), pList);
        }
        if (pTree.getRightTree() != null) {
            getAccounts(pTree.getRightTree(), pList);
        }
        return pList;
    }

    public Account[] getAccountsAsArray() {
        List<Account> accounts = getAccounts();
        //count the number of accounts using a while loop
        int count = 0;
        accounts.toFirst();
        while (accounts.hasAccess()) {
            count++;
            accounts.next();
        }
        //create an array of the correct size
        Account[] accountArray = new Account[count];
        //add each account to the array using a for loop
        accounts.toFirst();
        for (int i = 0; i < count; i++) {
            accountArray[i] = accounts.getContent();
            accounts.next();
        }
        return accountArray;

    }

    public void addAccount(String pAccountName, String pMasterPassword) {
        //adds an account to the password manager
        Account newAccount = new Account(pAccountName, pMasterPassword);
        this.accounts.insert(newAccount);
    }

    public boolean login(String pAccountName, String pMasterPassword) {
        //returns true if the account exists and the password is correct, false otherwise
        Account account = new Account(pAccountName, pMasterPassword);
        return this.accounts.search(account) != null;
    }

    public Account getAccount(String username, String password) {
        //returns the account if the account exists and the password is correct, null otherwise
        Account account = new Account(username, password);
        return this.accounts.search(account);
    }

}





