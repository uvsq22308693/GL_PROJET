import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryMenu extends JFrame {

    private JTree tree;
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea fileContentTextArea;

    public DirectoryMenu() {
        super("Gestionnaire des fichiers");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Projet");
        File projectDir = new File(System.getProperty("user.dir"));
        addNodes(root, projectDir);

        tree = new JTree(root);
        tree.setRootVisible(true);
        JScrollPane treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setPreferredSize(new Dimension(200, 300));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));

        searchButton = new JButton("Chercher");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.addActionListener(e -> displayFileContent());

        fileContentTextArea = new JTextArea();
        JScrollPane textAreaScrollPane = new JScrollPane(fileContentTextArea);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(treeScrollPane, BorderLayout.WEST);
        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(textAreaScrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addNodes(DefaultMutableTreeNode parentNode, File parentFile) {
        if (!parentFile.isDirectory()) {
            return;
        }

        DefaultMutableTreeNode childNode;
        for (File file : parentFile.listFiles()) {
            childNode = new DefaultMutableTreeNode(file.getName());
            parentNode.add(childNode);

            if (file.isDirectory()) {
                addNodes(childNode, file);
            }
        }
    }

    private void displayFileContent() {
        String filePath = searchField.getText();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            fileContentTextArea.setText(content);
        } catch (Exception e) {
            fileContentTextArea.setText("Erreur : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DirectoryMenu();
        });
    }
}
