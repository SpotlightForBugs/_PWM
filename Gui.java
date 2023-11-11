import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    private PWM pwm = new PWM();
    private JFrame frame;
    private JPanel panel;
    private JTextField accountField;
    private JPasswordField passwordField;

    public Gui() {
        pwm.addAccount("test", "test");
        pwm.addAccount("","");
    }

    private void startScreen() {
        frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        panel = new JPanel(new GridLayout(4, 1));

        JLabel label = new JLabel("Enter your account:");
        panel.add(label);

        accountField = new JTextField(20);
        panel.add(accountField);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> showPasswordScreen());
        panel.add(nextButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showPasswordScreen() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        JLabel label = new JLabel("Enter your master password:");
        panel.add(label);

        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String account = accountField.getText();
            String password = new String(passwordField.getPassword());
            if (pwm.login(account, password)) {
                showOverviewScreen(account, password);
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong username or password");
            }
        });
        panel.add(loginButton);

        frame.setVisible(true);
    }

    private void showOverviewScreen(String username, String password) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        JLabel label = new JLabel("Overview of " + username);
        panel.add(label);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> showAddScreen(username, password));
        panel.add(addButton);

        JButton showButton = new JButton("Show");
        showButton.addActionListener(e -> showShowScreen(username, password));
        panel.add(showButton);

        frame.setVisible(true);
    }

    private void showAddScreen(String username, String password) {
        JFrame addFrame = new JFrame("Add");
        addFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addFrame.setSize(300, 300);

        JPanel addPanel = new JPanel(new GridLayout(5, 1));

        JLabel label = new JLabel("Enter your username:");
        addPanel.add(label);

        JTextField usernameField = new JTextField(20);
        addPanel.add(usernameField);

        JLabel label2 = new JLabel("Enter your password:");
        addPanel.add(label2);

        JTextField passwordField = new JTextField(20);
        addPanel.add(passwordField);

        JLabel label3 = new JLabel("Enter the scope:");
        addPanel.add(label3);

        JTextField scopeField = new JTextField(20);
        addPanel.add(scopeField);

        JButton addButton2 = new JButton("Add");
        addButton2.addActionListener(e -> {
            String username_entry = usernameField.getText();
            String password_entry = passwordField.getText();
            String scope_entry = scopeField.getText();
            pwm.getAccount(username, password).addEntry(username_entry, password_entry, scope_entry);
            addFrame.dispose();
        });
        addPanel.add(addButton2);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> addFrame.dispose());
        addPanel.add(cancelButton);

        addFrame.add(addPanel);
        addFrame.setVisible(true);
    }

    private void showShowScreen(String username, String password) {
        Account account = pwm.getAccount(username, password);
        Entry[] entries = account.getEntriesAsArray();
        JFrame showFrame = new JFrame("Show");
        showFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showFrame.setSize(400, 300);

        JPanel showPanel = displayPasswordList(entries, showFrame);

        showFrame.add(showPanel);
        showFrame.setVisible(true);
    }

    private static JPanel displayPasswordList(Entry[] entries, JFrame showFrame) {
        JPanel showPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Entries:");
        label.setHorizontalAlignment(JLabel.CENTER);
        showPanel.add(label, BorderLayout.NORTH);

        String[] columnNames = {"Username", "Password", "Scope"};
        Object[][] rowData = new Object[entries.length][3];

        for (int i = 0; i < entries.length; i++) {
            rowData[i][0] = entries[i].getUsername();
            System.out.println(entries[i].getUsername());
            rowData[i][1] = entries[i].getPassword();
            rowData[i][2] = entries[i].getScopeAsString();
        }

        JTable table = new JTable(rowData, columnNames);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        showPanel.add(scrollPane, BorderLayout.CENTER);

        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(e -> showFrame.dispose());
        showPanel.add(okayButton, BorderLayout.SOUTH);

        JButton copyButton = new JButton("Copy");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    StringBuilder selectedText = new StringBuilder();
                    for (int row : selectedRows) {
                        for (int i = 0; i < table.getColumnCount(); i++) {
                            selectedText.append(table.getValueAt(row, i)).append(" ");
                        }
                        selectedText.append("\n");
                    }
                    copyToClipboard(selectedText.toString().trim());
                }
            }
        });
        showPanel.add(copyButton, BorderLayout.EAST);

        return showPanel;
    }


    private static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gui gui = new Gui();
            gui.startScreen();
        });
    }
}
