package com;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DecisionMakingTreeUI extends JFrame {
    private final DecisionMakingTree decisionMakingTree;
    private JLabel questionLabel;
    private JPanel buttonPanel;

    public DecisionMakingTreeUI(DecisionMakingTree decisionMakingTree) {
        this.decisionMakingTree = decisionMakingTree;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Decision Making Tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(questionLabel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new GridBagLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        displayCurrentNode();

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void displayCurrentNode() {
        Node currentNode = decisionMakingTree.getCurrentNode();
        if (currentNode != null) {
            questionLabel.setText(currentNode.getData());
            if (currentNode.getIsEndOfSearch()) {
                questionLabel.setForeground(Color.GREEN);
            } else {
                questionLabel.setForeground(Color.BLACK);
            }
            buttonPanel.removeAll();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 10, 5, 10);

            List<Link> possibleLinks = decisionMakingTree.getPossibleLinks();
            if (possibleLinks != null) {
                for (Link link : possibleLinks) {
                    JButton button = createStyledButton(link.getData());
                    button.addActionListener(e -> moveToNextNode(link));
                    buttonPanel.add(button, gbc);
                }
            }

            JButton backButton = createStyledButton("Назад");
            backButton.addActionListener(e -> moveToPreviousNode());
            gbc.gridy = possibleLinks != null ? possibleLinks.size() : 0;
            buttonPanel.add(backButton, gbc);

            JButton helpButton = createStyledButton("Помощь");
            helpButton.addActionListener(e -> showHelpDialog());
            gbc.gridy++;
            buttonPanel.add(helpButton, gbc);

            JButton exitButton = createStyledButton("Выход");
            exitButton.addActionListener(e -> System.exit(0));
            gbc.gridy++;
            buttonPanel.add(exitButton, gbc);

            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No more nodes available.");
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(240, 240, 240));
        button.setFocusPainted(false);
        return button;
    }

    private void moveToNextNode(Link link) {
        decisionMakingTree.moveToNextNode(link);
        displayCurrentNode();
    }

    private void moveToPreviousNode() {
        decisionMakingTree.moveToPreviousNode();
        displayCurrentNode();
    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this, "Вдумчиво отвечайте на вопросы, чтобы получить ответ от экспертной системы",
                "Помощь", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DecisionMakingTree decisionMakingTree = DecisionMakingTreeInitializer.initializeDecisionMakingTreeFromExcel();
                if (decisionMakingTree != null) {
                    new DecisionMakingTreeUI(decisionMakingTree);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to initialize Decision Making Tree.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        });
    }
}
