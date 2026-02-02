
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private String operator;
    private double num1, num2, result;

    public Calculator() {
        setTitle("Calculator App");
        setSize(350, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Display screen
        display = new JTextField();
        display.setBounds(30, 30, 280, 50);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display);

        // Buttons grid including Cancel and Backspace
        String[] buttonLabels = { 
            "C", "⌫", "", "",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        int x = 30, y = 100;

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton btn = new JButton(buttonLabels[i]);
            btn.setBounds(x, y, 65, 50);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            add(btn);

            x += 70;
            if ((i + 1) % 4 == 0) {
                x = 30;
                y += 60;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // CANCEL OPTION – erase everything on display
        if (command.equals("C")) {
            resetAll();
            return;
        }

        // BACKSPACE OPTION – delete last character
        if (command.equals("⌫")) {
            String text = display.getText();
            if (text.equals("Error")) {
                resetAll();
            } else if (text.length() > 0) {
                display.setText(text.substring(0, text.length() - 1));
            }
            return;
        }

        // If number or decimal
        if (command.matches("[0-9.]")) {
            if (display.getText().equals("Error")) {
                display.setText("");
            }
            display.setText(display.getText() + command);
            return;
        }

        // If operator
        if (command.matches("[+\\-*/]")) {
            String text = display.getText();
            if (text.isEmpty() || text.equals("Error")) {
                return;
            }

            operator = command;
            num1 = Double.parseDouble(text);
            display.setText("");
            return;
        }

        // When '=' is pressed
        if (command.equals("=")) {
            String text = display.getText();
            if (text.isEmpty() || operator == null) {
                return;
            }

            num2 = Double.parseDouble(text);

            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/": 
                    if (num2 == 0) {
                        display.setText("Error");
                        operator = null;
                        return;
                    }
                    result = num1 / num2; 
                    break;
            }

            display.setText(String.valueOf(result));
            operator = null;
        }
    }

    // NECESSARY RESET METHOD
    private void resetAll() {
        display.setText("");
        operator = null;
        num1 = num2 = result = 0;
    }

    private void resetAfterError() {
        if (display.getText().equals("Error")) {
            resetAll();
        }
    }

    public static void main(String[] args) {
        new Calculator().setVisible(true);
    }
}
