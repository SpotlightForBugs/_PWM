public class Entry implements ComparableContent<Entry> {

    private String username;
    private String password;

    private List<String> scope = new List<String>();

    public Entry(String pUsername, String pPassword, String pScope) {
        this.scope.append(pScope); //like a website for example (e.g. google.com)
        this.username = pUsername;
        this.password = pPassword;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public List<String> getScope() {
        return this.scope;
    }


    @Override
    public boolean isGreater(Entry pContent) {
        return false;
    }

    /**
     * @param pContent das mit dem aufrufenden Objekt zu vergleichende Objekt vom
     *                 Typ ContentType
     * @return
     */
    @Override
    public boolean isEqual(Entry pContent) {
        return false;
    }

    /**
     * @param pContent das mit dem aufrufenden Objekt zu vergleichende Objekt vom
     *                 Typ ContentType
     * @return
     */
    @Override
    public boolean isLess(Entry pContent) {
        return false;
    }

    public String getScopeAsString() {
        String scopeString = "";
        this.scope.toFirst();
        while (this.scope.hasAccess()) {
            scopeString += this.scope.getContent() + " ";
            this.scope.next();
        }
        return scopeString;
    }
}
