import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeTracker {

    private JFrame frame;
    private JTextField nameField, rollField, marksField;
    private JTextArea reportArea;

    // Store student data using ArrayList
    private ArrayList<Student> studentList = new ArrayList<>();

    // Student class
    class Student {
        String name;
        String roll;
        double marks;

        Student(String name, String roll, double marks) {
            this.name = name;
            this.roll = roll;
            this.marks = marks;
        }
    }

    public StudentGradeTracker() {
        initialize();
    }

    private void initialize() {

        frame = new JFrame("Student Grade Tracker System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 50, 100));
        headerPanel.setPreferredSize(new Dimension(100, 110));

        JLabel title = new JLabel("STUDENT GRADE TRACKER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        frame.add(headerPanel, BorderLayout.NORTH);

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(52, 73, 94));

        // ================= FORM PANEL =================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(52, 73, 94));
        formPanel.setBorder(new EmptyBorder(40, 200, 20, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 22);

        // Student Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(300, 45));
        nameField.setFont(labelFont);
        formPanel.add(nameField, gbc);

        // Roll Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setForeground(Color.WHITE);
        rollLabel.setFont(labelFont);
        formPanel.add(rollLabel, gbc);

        gbc.gridx = 1;
        rollField = new JTextField(20);
        rollField.setPreferredSize(new Dimension(300, 45));
        rollField.setFont(labelFont);
        formPanel.add(rollField, gbc);

        // Marks
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setForeground(Color.WHITE);
        marksLabel.setFont(labelFont);
        formPanel.add(marksLabel, gbc);

        gbc.gridx = 1;
        marksField = new JTextField(20);
        marksField.setPreferredSize(new Dimension(300, 45));
        marksField.setFont(labelFont);
        formPanel.add(marksField, gbc);

        // ================= BUTTON PANEL =================
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(52, 73, 94));

        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(10, 30, 10, 30);
        btnGbc.gridy = 0;

        JButton addBtn = createButton("Add Student");
        JButton reportBtn = createButton("Generate Report");
        JButton refreshBtn = createButton("Refresh");

        Dimension btnSize = new Dimension(190, 50);
        addBtn.setPreferredSize(btnSize);
        reportBtn.setPreferredSize(btnSize);
        refreshBtn.setPreferredSize(btnSize);

        btnGbc.gridx = 0;
        buttonPanel.add(addBtn, btnGbc);

        btnGbc.gridx = 1;
        buttonPanel.add(reportBtn, btnGbc);

        btnGbc.gridx = 2;
        buttonPanel.add(refreshBtn, btnGbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ================= REPORT SECTION =================
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(new TitledBorder(
                new LineBorder(Color.GRAY, 2),
                "Report Section",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 18)
        ));

        reportArea = new JTextArea();
        reportArea.setFont(new Font("Consolas", Font.PLAIN, 18));
        reportArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new Dimension(100, 200));

        reportPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(reportPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);

        // ================= BUTTON ACTIONS =================
        addBtn.addActionListener(e -> addStudent());
        reportBtn.addActionListener(e -> generateReport());
        refreshBtn.addActionListener(e -> refresh());

        frame.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(new Color(0, 168, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // ================= ADD STUDENT =================
    private void addStudent() {

        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String marksText = marksField.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || marksText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        try {
            double marks = Double.parseDouble(marksText);

            studentList.add(new Student(name, roll, marks));

            nameField.setText("");
            rollField.setText("");
            marksField.setText("");
            reportArea.setText("");

            JOptionPane.showMessageDialog(frame, "Data Added Successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter valid marks!");
        }
    }

    // ================= GENERATE REPORT =================
   private void generateReport() {

    reportArea.setText("");

    if (studentList.isEmpty()) {
        reportArea.setText("No student data available.");
        return;
    }

    double total = 0;
    double highest = studentList.get(0).marks;
    double lowest = studentList.get(0).marks;

    reportArea.append("===== STUDENT REPORT =====\n\n");

    int count = 1;  // To display Student 1, Student 2, etc.

    for (Student s : studentList) {

        reportArea.append("Student " + count + "\n");
        reportArea.append("Name: " + s.name + "\n");
        reportArea.append("Roll: " + s.roll + "\n");
        reportArea.append("Marks: " + s.marks + "\n");
        reportArea.append("---------------------------------\n");

        total += s.marks;

        if (s.marks > highest) {
            highest = s.marks;
        }

        if (s.marks < lowest) {
            lowest = s.marks;
        }

        count++;
    }

    double average = total / studentList.size();

    reportArea.append("\n===== SUMMARY =====\n");
    reportArea.append("Total Students: " + studentList.size() + "\n");
    reportArea.append("Average Marks: " + average + "\n");
    reportArea.append("Highest Marks: " + highest + "\n");
    reportArea.append("Lowest Marks: " + lowest + "\n");
}

    // ================= REFRESH =================
    private void refresh() {
        nameField.setText("");
        rollField.setText("");
        marksField.setText("");
        reportArea.setText("");
        studentList.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTracker::new);
    }
}
