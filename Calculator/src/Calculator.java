import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {

    // Define the width and height of the calculator window
    int boardWidth = 360;
    int boardHeight = 540;

    // Define custom colors for the calculator interface using RGB values
    Color customLightGray = new Color(211, 211, 211);
    Color customDarkGray = new Color(100, 100, 100);
    Color customBlack = new Color(15, 15, 15);
    Color customOrange = new Color(255, 170, 0);

    // Define the values for the buttons on the calculator, as well as arrays for
    // the right and top symbols for easy reference
    String[] buttonValues = {
            "AC", "±", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };
    String[] rightSymbols = { "÷", "×", "-", "+", "=" };
    String[] topSymbols = { "AC", "±", "%" };

    JFrame frame = new JFrame("Calculator"); // Create a new JFrame with the title "Calculator"
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // The 2 operands and the operator
    String operandA = "0";
    String operator = null;
    String operandB = null;

    Calculator() {
        // Set up the main frame of the calculator
        frame.setSize(boardWidth, boardHeight); // Set the size of the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setResizable(false); // Prevent the frame from being resized
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation when user clicks the
                                                              // close button
        frame.setLayout(new BorderLayout()); // Set the layout manager for the frame to BorderLayout which allows us to
                                             // add components to the north, south, east, west, and center of the frame

        // Set up the display label
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        // Set up the display panel
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH); // Add the display panel to the north region of the frame

        // Set up the buttons panel
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel, BorderLayout.CENTER); // Add the buttons panel to the center region of the frame

        // Loop through buttonValues list and add each button to buttonsPanel
        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) { // operators
                        if (buttonValue == "=") {
                            if (operandA != null) {
                                operandB = displayLabel.getText();
                                double numA = Double.parseDouble(operandA);
                                double numB = Double.parseDouble(operandB);

                                if (operator == "+") {
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                } else if (operator == "-") {
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                } else if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                } else if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }
                        } else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                operandA = displayLabel.getText();
                                displayLabel.setText("0");
                                operandB = "0";
                            }
                            operator = buttonValue;
                        }

                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (buttonValue == "±") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));

                        } else if (buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    } else { // digits or . or √(sqrt)
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if (buttonValue == "√") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay = Math.pow(numDisplay, 0.5);
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                }
            });
        }
        frame.setVisible(true); // Make the frame visible
    }

    void clearAll() {
        operandA = "0";
        operator = null;
        operandB = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) { // whole number
            return Integer.toString((int) numDisplay);
        }

        return Double.toString(numDisplay);
    }
}
