import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        new BMIFrame();
    }
}

class BMIFrame extends JFrame {

    JPanel introPanel;
    JPanel bmiPanel;

    public BMIFrame() {
        setLayout(null);

        introPanel = new IntroPage(this);
        bmiPanel = new BMIPage(this);

        introPanel.setBounds(0, 0, 450, 300);
        bmiPanel.setBounds(0, 0, 450, 300);

        add(introPanel);
        add(bmiPanel);

        bmiPanel.setVisible(false);

        setTitle("BMI Calculator");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void showIntro() {
        introPanel.setVisible(true);
        bmiPanel.setVisible(false);
    }

    public void showBMI() {
        introPanel.setVisible(false);
        bmiPanel.setVisible(true);
    }
}

class IntroPage extends JPanel {

    public IntroPage(BMIFrame frame) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
        setBackground(new Color(255, 200, 220));

        JLabel title = new JLabel("BMI Calculator");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(new Color(255, 200, 220));

        JLabel designer = new JLabel("Designed by: Aylar");
        designer.setOpaque(true);
        designer.setBackground(new Color(255, 200, 220));

        JButton start = new JButton("Start");
        start.addActionListener(e -> frame.showBMI());

        add(title);
        add(designer);
        add(start);
    }
}

class BMIPage extends JPanel {

    JTextField heightInput;
    JTextField weightInput;
    JLabel result;

    public BMIPage(BMIFrame frame) {

        setLayout(new GridLayout(7, 1, 5, 5));
        setBackground(new Color(255, 200, 220));

        JLabel hLabel = new JLabel("Height (cm):");
        hLabel.setOpaque(true);
        hLabel.setBackground(new Color(255, 200, 220));

        JLabel wLabel = new JLabel("Weight (kg):");
        wLabel.setOpaque(true);
        wLabel.setBackground(new Color(255, 200, 220));

        heightInput = new JTextField();
        weightInput = new JTextField();

        result = new JLabel("Result:", JLabel.CENTER);
        result.setOpaque(true);
        result.setBackground(new Color(255, 200, 220));

        JButton calc = new JButton("Calculate BMI");
        JButton back = new JButton("Back");

        calc.addActionListener(calculateBMI());
        back.addActionListener(e -> frame.showIntro());

        add(hLabel);
        add(heightInput);
        add(wLabel);
        add(weightInput);
        add(calc);
        add(result);
        add(back);
    }

    private ActionListener calculateBMI() {
        return e -> {
            try {
                double h = Double.parseDouble(heightInput.getText());
                double w = Double.parseDouble(weightInput.getText());

                double bmi = BMILogic.calculateBMI(h, w);
                String status = BMILogic.getStatus(bmi);

                result.setText("BMI: " + String.format("%.2f", bmi) + " - " + status);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter valid numbers!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        };
    }
}

class BMILogic {

    public static double calculateBMI(double h, double w) {
        double heightMeter = h / 100;
        return w / (heightMeter * heightMeter);
    }

    public static String getStatus(double bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }
}