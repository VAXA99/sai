package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DecisionMakingTreeUI extends JFrame {
    private DecisionMakingTree decisionMakingTree;
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
        add(questionLabel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new GridLayout(0, 1));
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
            buttonPanel.removeAll();

            List<Link> possibleLinks = decisionMakingTree.getPossibleLinks();
            if (possibleLinks != null) {
                for (Link link : possibleLinks) {
                    JButton button = new JButton(link.getData());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            moveToNextNode(link);
                        }
                    });
                    buttonPanel.add(button);
                }
            }

            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moveToPreviousNode();
                }
            });
            buttonPanel.add(backButton);

            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No more nodes available.");
        }
    }


    private void moveToNextNode(Link link) {
        decisionMakingTree.moveToNextNode(link);
        displayCurrentNode();
    }

    private void moveToPreviousNode() {
        decisionMakingTree.moveToPreviousNode();
        displayCurrentNode();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
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
            }
        });
    }
}