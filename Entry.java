public class Entry implements ComparableContent<Entry> {

  private String username;
  private String password;
  public final Account account;

  private List<String> scope = new List<String>();

  public Entry(String pUsername, String pPassword, String pScope, Account pOwner) {
    this.scope.append(pScope); // like a website for example (e.g. google.com)
    this.username = pUsername;
    this.password = pPassword;
    this.account = pOwner;
    System.out.println(
        "Entry created: " + this.username + " " + this.password + " " + getScopeAsString());
  }

  /**
   * @return the username of the entry
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * @return the password of the entry
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * @return the scope of the entry
   */
  public List<String> getScope() {
    return this.scope;
  }

  /**
   * @return the scope of the entry as a String
   */
  private int getScopeSize() {
    int size = 0;
    this.scope.toFirst();
    while (this.scope.hasAccess()) {
      size++;
      this.scope.next();
    }
    return size;
  }

  @Override
  public boolean isGreater(Entry pContent) {
    return this.getScopeAsString().compareTo(pContent.getScopeAsString()) > 0;
  }

  /**
   * @param pContent das mit dem aufrufenden Objekt zu vergleichende Objekt vom Typ ContentType
   * @return
   */
  @Override
  public boolean isEqual(Entry pContent) {
    return this.getScopeAsString().equals(pContent.getScopeAsString());
  }

  /**
   * @param pContent das mit dem aufrufenden Objekt zu vergleichende Objekt vom Typ ContentType
   * @return
   */
  @Override
  public boolean isLess(Entry pContent) {
    return this.getScopeAsString().compareTo(pContent.getScopeAsString()) < 0;
  }

  public String getScopeAsString() {
    StringBuilder scopeString = new StringBuilder();
    this.scope.toFirst();
    while (this.scope.hasAccess()) {
      scopeString.append(this.scope.getContent()).append(" ");
      this.scope.next();
    }
    return scopeString.toString();
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setScope(String scope) {
    this.scope = new List<String>();
    this.scope.append(scope);
  }

  public Account getAccount() {
    return this.account;
  }
}
