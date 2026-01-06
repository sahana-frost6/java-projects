import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

// =========================================================================
// 1. DATA AND MODEL CLASSES
// =========================================================================

// User class
class User {
    private String username, password, role;
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

// Event class
class Event {
    private String name, date, location, capacity;
    public Event(String name, String date, String location, String capacity) {
        this.name = name; this.date = date; this.location = location; this.capacity = capacity;
    }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String toString() {
        return "🗓️ Event Name: " + name + "\n📅 Date: " + date + "\n📍 Location: " + location +
               "\n👥 Capacity: " + capacity + "\n------------------------------\n";
    }
}

// Venue class
class Venue {
    private String name, location, capacity, details;
    public Venue(String name, String location, String capacity, String details) {
        this.name = name; this.location = location; this.capacity = capacity; this.details = details;
    }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getCapacity() { return capacity; }
    public String getDetails() { return details; }
    public String toString() {
        return "🏛️ Venue: " + name + "\n📍 Location: " + location + "\n👥 Capacity: " + capacity + 
               "\nℹ️ Details: " + details + "\n------------------------------\n";
    }
}

// Booking class
class Booking {
    private String eventName, userName, venueName, bookingDate;
    public Booking(String eventName, String userName, String venueName, String bookingDate) {
        this.eventName = eventName; this.userName = userName; this.venueName = venueName; this.bookingDate = bookingDate;
    }
    public String getVenueName() { return venueName; }
    public String getUserName() { return userName; } 
    public String toString() {
        return "🎫 Event: " + eventName + "\n👤 User: " + userName + "\n🏛️ Venue: " + venueName + 
               "\n📅 Date: " + bookingDate + "\n------------------------------\n";
    }
    public String bookingDate() { return bookingDate; } 
}

// Notification class
class Notification {
    private String message;
    public Notification(String message) { this.message = message; }
    public String toString() { return "🔔 " + message; }
}

// User database
class UserDatabase {
    private static ArrayList<User> users = new ArrayList<>();
    static {
        users.add(new User("admin", "admin123", "admin"));
        users.add(new User("siddu", "siddu2004", "user"));
        users.add(new User("shrihari", "shrihari2004", "user"));
        users.add(new User("ritesh", "ritesh2005", "user"));
    }
    public static User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) return user;
        }
        return null;
    }
    public static boolean registerUser(String username, String password, String role) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return false; 
        }
        users.add(new User(username, password, role));
        return true;
    }
    public static ArrayList<User> getUsers() { return users; }
}

// Global Database for events, venues, bookings, notifications
class AppDatabase {
    public static ArrayList<Venue> venues = new ArrayList<>();
    public static ArrayList<Booking> bookings = new ArrayList<>();
    public static ArrayList<Notification> notifications = new ArrayList<>();
    
    static {
        venues.add(new Venue("Grand Hall", "City Center", "500", "Large space for concerts and conferences."));
        venues.add(new Venue("Small Auditorium", "North Campus", "100", "Suitable for workshops and presentations."));
        bookings.add(new Booking("Tech Meetup", "siddu", "Grand Hall", "2025-12-15"));
        notifications.add(new Notification("Welcome to the system!"));
        notifications.add(new Notification("New venue added: Grand Hall."));
    }
}

// =========================================================================
// 2. USER ACCESS PANEL (Event Management Removed)
// =========================================================================

class UserAccessPanel extends JPanel implements ActionListener {
    private MainAppDashboard parent;
    private JTextArea displayArea = new JTextArea(10, 40);
    private JScrollPane scrollPane = new JScrollPane(displayArea);
    
    private JTextField eventNameField = new JTextField(10);
    private JTextField venueNameField = new JTextField(10);
    private JTextField dateField = new JTextField(10); 
    
    private User currentUser;

    private JButton bookEventButton = new JButton("Book Venue 🏛️"); 
    private JButton viewVenueDetailsButton = new JButton("View Venue Details ℹ️");


    public UserAccessPanel(MainAppDashboard parent, User user) {
        this.parent = parent;
        this.currentUser = user;
        setLayout(new BorderLayout(10, 10));
        
        JPanel metricsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        long userBookingCount = AppDatabase.bookings.stream()
                                                    .filter(b -> b.getUserName().equalsIgnoreCase(currentUser.getUsername()))
                                                    .count();
        
        // This metric now represents ALL posted events since the user can no longer distinguish 'My Events' easily
        long totalEventsCount = parent.getEvents().size(); 

        metricsPanel.add(createMetricBox("POSTED EVENTS", String.valueOf(totalEventsCount), new Color(100, 200, 100), "View Posted Events"));
        metricsPanel.add(createMetricBox("MY BOOKINGS", String.valueOf(userBookingCount), new Color(255, 100, 180), "Booking History 📜"));
        metricsPanel.add(createMetricBox("NOTIFICATIONS", String.valueOf(AppDatabase.notifications.size()), new Color(255, 180, 50), "View Notifications 🔔"));
        metricsPanel.add(createMetricBox("VIEW PROFILE", currentUser.getUsername().toUpperCase(), new Color(100, 150, 255), "View Profile 👤"));

        // --- Action Panel (Only Booking and Viewing) ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        bookEventButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        bookEventButton.addActionListener(this);
        viewVenueDetailsButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        viewVenueDetailsButton.addActionListener(this);

        actionPanel.add(bookEventButton);
        actionPanel.add(viewVenueDetailsButton);

        displayArea.setEditable(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Information & Log"));
        
        JPanel topContainer = new JPanel(new BorderLayout(10, 10));
        topContainer.add(metricsPanel, BorderLayout.NORTH);
        topContainer.add(actionPanel, BorderLayout.CENTER);

        add(topContainer, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createMetricBox(String title, String value, Color color, String actionCommand) {
        JPanel box = new JPanel();
        box.setLayout(new BorderLayout(5, 5));
        box.setBackground(color);
        box.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title, JLabel.LEFT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        
        JLabel valueLabel = new JLabel(value, JLabel.RIGHT);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        valueLabel.setForeground(Color.WHITE);
        
        JButton actionTrigger = new JButton(actionCommand);
        actionTrigger.setVisible(false);
        actionTrigger.addActionListener(this);
        
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setOpaque(false);
        labelPanel.add(titleLabel, BorderLayout.NORTH);
        labelPanel.add(valueLabel, BorderLayout.CENTER);
        
        box.add(labelPanel, BorderLayout.CENTER);
        box.add(actionTrigger, BorderLayout.SOUTH); 

        box.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionTrigger.doClick();
            }
        });
        
        return box;
    }

    private void showBookingForm() {
        if (parent.getEvents().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No events are currently scheduled. Please contact an administrator to post one.", "No Events Available", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        eventNameField.setText(""); venueNameField.setText(""); dateField.setText("");
        
        String eventNames = parent.getEvents().stream()
                                .map(Event::getName)
                                .collect(Collectors.joining(", "));
        
        form.add(new JLabel("Available Events:"));
        form.add(new JLabel("<html><b>" + eventNames + "</b></html>"));
        
        form.add(new JLabel("Event Name (to book venue for):")); form.add(eventNameField);
        form.add(new JLabel("Venue Name:")); form.add(venueNameField);
        form.add(new JLabel("Date (YYYY-MM-DD):")); form.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, form, "Book Venue for Your Event", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String eName = eventNameField.getText().trim();
            String vName = venueNameField.getText().trim();
            String date = dateField.getText().trim();

            if (eName.isEmpty() || vName.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All booking fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // --- Double Booking Conflict Check ---
            boolean conflict = AppDatabase.bookings.stream().anyMatch(b -> 
                b.getVenueName().equalsIgnoreCase(vName) && b.bookingDate().equals(date) 
            );

            if (conflict) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Booking Conflict! The venue " + vName + " is already booked for " + date + ".", 
                    "Booking Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if the event name exists (Crucial check)
            boolean eventExists = parent.getEvents().stream().anyMatch(ev -> ev.getName().equalsIgnoreCase(eName));
             if (!eventExists) {
                 JOptionPane.showMessageDialog(this, "Event not found. Please check the 'Available Events' list.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            // Check if the venue exists
            boolean venueExists = AppDatabase.venues.stream().anyMatch(v -> v.getName().equalsIgnoreCase(vName));
            if (!venueExists) {
                 JOptionPane.showMessageDialog(this, "Venue not found.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            // Booking is valid: proceed
            Booking newBooking = new Booking(eName, currentUser.getUsername(), vName, date);
            AppDatabase.bookings.add(newBooking);
            
            JOptionPane.showMessageDialog(this, "✅ Booking confirmed for your event: " + eName, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
            
            displayArea.setText("✅ Venue booking added for: " + eName + "\n" + newBooking.toString());
            parent.updateUserDashboard();
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        displayArea.setText(""); 

        if (e.getSource() == bookEventButton || command.contains("Book Venue")) {
            showBookingForm();
        } else if (command.contains("View Venues")) {
            StringBuilder sb = new StringBuilder("--- Available Venues ---\n");
            AppDatabase.venues.forEach(v -> sb.append(v.getName()).append(" (Capacity: ").append(v.getCapacity()).append(")\n"));
            displayArea.setText(sb.toString());
        } else if (command.contains("View Venue Details") || e.getSource() == viewVenueDetailsButton) {
            String name = JOptionPane.showInputDialog(this, "Enter Venue Name:");
            if (name != null && !name.trim().isEmpty()) {
                AppDatabase.venues.stream()
                    .filter(v -> v.getName().equalsIgnoreCase(name.trim()))
                    .findFirst()
                    .ifPresentOrElse(
                        v -> displayArea.setText(v.toString()),
                        () -> displayArea.setText("Venue details not found.")
                    );
            }
        } else if (command.contains("Booking History")) {
            StringBuilder sb = new StringBuilder("--- Your Booking History ---\n");
            boolean found = false;
            for (Booking b : AppDatabase.bookings) {
                if (b.getUserName().equalsIgnoreCase(currentUser.getUsername())) { 
                    sb.append(b.toString());
                    found = true;
                }
            }
            displayArea.setText(found ? sb.toString() : "No booking history found.");
        } else if (command.contains("View Notifications")) {
            StringBuilder sb = new StringBuilder("--- Notifications ---\n");
            AppDatabase.notifications.forEach(n -> sb.append(n.toString()).append("\n"));
            displayArea.setText(sb.toString());
        } else if (command.contains("View Profile")) {
            displayArea.setText("--- User Profile ---\n" + 
                                "Username: " + currentUser.getUsername() + "\n" +
                                "Role: " + currentUser.getRole().toUpperCase() + "\n");
        } else if (command.contains("View Posted Events")) {
            // Displays ALL posted events
            StringBuilder sb = new StringBuilder("--- All Posted Events ---\n");
            parent.getEvents().forEach(ev -> sb.append(ev.toString()));
            displayArea.setText(parent.getEvents().isEmpty() ? "No events posted." : sb.toString());
        }
    }
}

// =========================================================================
// 3. ADMIN ACCESS PANEL (UNCHANGED)
// =========================================================================

class AdminAccessPanel extends JPanel implements ActionListener {
    private MainAppDashboard parent;
    private JTextArea displayArea = new JTextArea(10, 40);
    private JScrollPane scrollPane = new JScrollPane(displayArea);
    
    public AdminAccessPanel(MainAppDashboard parent) {
        this.parent = parent;
        setLayout(new BorderLayout(10, 10));
        
        // --- 1. Metrics Panel ---
        JPanel metricsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        metricsPanel.add(createMetricBox("VENUES", String.valueOf(AppDatabase.venues.size()), new Color(100, 200, 100), "View Venues 🏛️")); 
        metricsPanel.add(createMetricBox("TOTAL BOOKINGS", String.valueOf(AppDatabase.bookings.size()), new Color(255, 100, 180), "View Bookings 📜")); 
        metricsPanel.add(createMetricBox("REGISTERED USERS", String.valueOf(UserDatabase.getUsers().size()), new Color(255, 180, 50), "View Users 👥")); 
        metricsPanel.add(createMetricBox("EVENTS POSTED", String.valueOf(parent.getEvents().size()), new Color(100, 150, 255), "View Posted Events")); 

        // --- 2. Action Panel (Empty or minimal actions for admin) ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // --- 3. Log Panel ---
        displayArea.setEditable(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Admin Activity Log / Details"));
        
        JPanel topContainer = new JPanel(new BorderLayout(10, 10));
        topContainer.add(metricsPanel, BorderLayout.NORTH);
        topContainer.add(actionPanel, BorderLayout.CENTER);

        add(topContainer, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createMetricBox(String title, String value, Color color, String actionCommand) {
        JPanel box = new JPanel();
        box.setLayout(new BorderLayout(5, 5));
        box.setBackground(color);
        box.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title, JLabel.LEFT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        
        JLabel valueLabel = new JLabel(value, JLabel.RIGHT);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        valueLabel.setForeground(Color.WHITE);
        
        JButton actionTrigger = new JButton(actionCommand);
        actionTrigger.setVisible(false);
        actionTrigger.addActionListener(this);
        
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setOpaque(false);
        labelPanel.add(titleLabel, BorderLayout.NORTH);
        labelPanel.add(valueLabel, BorderLayout.CENTER);
        
        box.add(labelPanel, BorderLayout.CENTER);
        box.add(actionTrigger, BorderLayout.SOUTH); 

        box.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionTrigger.doClick();
            }
        });
        
        return box;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        displayArea.setText(""); 

        if (command.contains("View Bookings 📜")) {
            StringBuilder sb = new StringBuilder("--- All Bookings ---\n");
            AppDatabase.bookings.forEach(b -> sb.append(b.toString()));
            displayArea.setText(AppDatabase.bookings.isEmpty() ? "No bookings found." : sb.toString());
        } else if (command.contains("View Venues 🏛️")) {
            StringBuilder sb = new StringBuilder("--- All Venues ---\n");
            AppDatabase.venues.forEach(v -> sb.append(v.toString()));
            displayArea.setText(sb.toString());
        } else if (command.contains("View Users 👥")) {
            StringBuilder sb = new StringBuilder("--- All Users ---\n");
            UserDatabase.getUsers().forEach(u -> sb.append("Username: ").append(u.getUsername())
                                          .append(", Role: ").append(u.getRole()).append("\n"));
            displayArea.setText(sb.toString());
        } else if (command.contains("View Posted Events")) {
            StringBuilder sb = new StringBuilder("--- All Posted Events ---\n");
            parent.getEvents().forEach(ev -> sb.append(ev.toString()));
            displayArea.setText(parent.getEvents().isEmpty() ? "No events posted." : sb.toString());
        }
    }
}

// =========================================================================
// 4. MAIN DASHBOARD (Controller)
// =========================================================================

class MainAppDashboard extends JFrame implements ActionListener {
    private User currentUser;
    private LoginFrame loginFrame;
    private JPanel contentPanel;
    
    private ArrayList<Event> events = new ArrayList<>();
    public ArrayList<Event> getEvents() { return events; } 

    private JButton logoutButton = new JButton("🚪 Back to Login");

    public MainAppDashboard(User user, LoginFrame loginFrame) {
        this.currentUser = user;
        this.loginFrame = loginFrame;
        
        // Ensure there is a base set of events for all users (Admin/General)
        if (events.isEmpty()) {
             events.add(new Event("Annual Conference", "2025-07-20", "Grand Hall", "500"));
             events.add(new Event("Quarterly Seminar", "2025-08-01", "Small Auditorium", "100"));
             events.add(new Event("Holiday Gala", "2025-12-10", "Grand Hall", "450"));
             events.add(new Event("Community Fair", "2026-01-15", "City Park", "1000"));
        }
        
        setTitle("🎯 Event Dashboard - " + user.getRole().toUpperCase());
        setSize(700, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.CYAN, getWidth(), getHeight(), Color.BLUE));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(700, 60));
        JLabel headerLabel = new JLabel("📅 Event Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.addActionListener(this);
        footerPanel.add(logoutButton);
        add(footerPanel, BorderLayout.SOUTH);
        
        contentPanel = new JPanel(new BorderLayout());
        if (currentUser.getRole().equals("admin")) {
            contentPanel.add(new AdminAccessPanel(this), BorderLayout.CENTER);
        } else {
            contentPanel.add(new UserAccessPanel(this, currentUser), BorderLayout.CENTER);
        }
        add(contentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void updateAdminDashboard() {
        if (currentUser.getRole().equals("admin")) {
            contentPanel.removeAll();
            contentPanel.add(new AdminAccessPanel(this), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
    
    public void updateUserDashboard() {
        if (currentUser.getRole().equals("user")) {
            contentPanel.removeAll();
            contentPanel.add(new UserAccessPanel(this, currentUser), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            this.dispose(); 
            if (loginFrame != null) {
                loginFrame.setVisible(true);
            } else {
                System.exit(0);
            }
        }
    }
}

// =========================================================================
// 6. LOGIN FRAME (HOME SCREEN) - CARD LAYOUT
// =========================================================================

class LoginFrame extends JFrame implements ActionListener {
    private JPanel cardPanel; 
    private CardLayout cardLayout = new CardLayout();
    
    private JTextField loginUsernameField = new JTextField(15);
    private JPasswordField loginPasswordField = new JPasswordField(15);
    private JButton userLoginButton = new JButton("🔑 USER LOGIN"); 
    private JButton adminLoginButton = new JButton("👑 ADMIN LOGIN"); 
    private JButton showRegisterButton = new JButton("📝 REGISTER");
    
    private JTextField regUsernameField = new JTextField(15);
    private JPasswordField regPasswordField = new JPasswordField(15);
    private JComboBox<String> regRoleBox = new JComboBox<>(new String[]{"user"});
    private JButton registerButton = new JButton("📝 REGISTER");
    private JButton backToLoginButton = new JButton("◀ Back to Login"); 

    public LoginFrame() {
        setTitle("🔐 Event Management Access");
        setSize(400, 300); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        cardPanel = new JPanel(cardLayout);
        
        JPanel loginView = createLoginView();
        JPanel registrationView = createRegistrationView();

        cardPanel.add(loginView, "LoginView");
        cardPanel.add(registrationView, "RegistrationView");
        
        add(cardPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel(String title, Color c1, Color c2) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(400, 60));
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createLoginView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel("Welcome Back!", new Color(50, 70, 150), new Color(100, 50, 120)), BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 248, 255)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel userLabel = new JLabel("Username:"); userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JLabel passLabel = new JLabel("Password:"); passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; inputPanel.add(loginUsernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; inputPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; inputPanel.add(loginPasswordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); 
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        userLoginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLoginButton.setBackground(new Color(75, 100, 200)); 
        userLoginButton.setForeground(Color.WHITE);
        userLoginButton.setFocusPainted(false);
        
        adminLoginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adminLoginButton.setBackground(new Color(255, 165, 0)); 
        adminLoginButton.setForeground(Color.BLACK);
        adminLoginButton.setFocusPainted(false);

        showRegisterButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        showRegisterButton.setBackground(new Color(150, 180, 255)); 
        showRegisterButton.setForeground(Color.BLACK);
        showRegisterButton.setFocusPainted(false);
        
        buttonPanel.add(userLoginButton);
        buttonPanel.add(adminLoginButton); 
        buttonPanel.add(showRegisterButton);

        userLoginButton.addActionListener(this);
        adminLoginButton.addActionListener(this);
        showRegisterButton.addActionListener(this);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createRegistrationView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel("Create Your Account", new Color(50, 150, 70), new Color(70, 180, 100)), BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 255, 240)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel userLabel = new JLabel("Username:"); userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JLabel passLabel = new JLabel("Password:"); passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JLabel roleLabel = new JLabel("Role:"); roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; inputPanel.add(regUsernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; inputPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; inputPanel.add(regPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; inputPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; inputPanel.add(regRoleBox, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 255, 240));
        
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setBackground(new Color(50, 150, 70)); 
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        
        backToLoginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backToLoginButton.setBackground(new Color(200, 200, 200)); 
        backToLoginButton.setForeground(Color.BLACK);
        backToLoginButton.setFocusPainted(false);

        buttonPanel.add(backToLoginButton); 
        buttonPanel.add(registerButton);

        registerButton.addActionListener(this);
        backToLoginButton.addActionListener(this);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (e.getSource() == showRegisterButton) {
            cardLayout.show(cardPanel, "RegistrationView");
            return;
        }
        if (e.getSource() == backToLoginButton) {
            cardLayout.show(cardPanel, "LoginView");
            return;
        }
        
        if (e.getSource() == registerButton) {
            String username = regUsernameField.getText().trim();
            String password = new String(regPasswordField.getPassword()).trim();
            String role = (String) regRoleBox.getSelectedItem(); 

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password are required.");
                return;
            }

            if (UserDatabase.registerUser(username, password, role)) {
                JOptionPane.showMessageDialog(this, "✅ Registration successful! Please log in.");
                cardLayout.show(cardPanel, "LoginView");
                regUsernameField.setText("");
                regPasswordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Username already exists. Please choose another.");
            }
            return;
        }
        
        // --- Handle Login Actions ---
        
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword()).trim();
        User user = UserDatabase.authenticate(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "❌ Invalid Username or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean successfulLogin = false;
        if (e.getSource() == userLoginButton) {
            if (user.getRole().equals("user") || user.getRole().equals("admin")) {
                 JOptionPane.showMessageDialog(this, "✅ User Login Successful! Welcome " + user.getUsername());
                 successfulLogin = true;
            }
        } else if (e.getSource() == adminLoginButton) {
            if (user.getRole().equals("admin")) {
                JOptionPane.showMessageDialog(this, "👑 Admin Login Successful! Welcome " + user.getUsername());
                successfulLogin = true;
            } else {
                JOptionPane.showMessageDialog(this, "❌ Access Denied: This button is for administrators only.", "Access Denied", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        if (successfulLogin) {
            new MainAppDashboard(user, this);
            this.setVisible(false);
            loginUsernameField.setText("");
            loginPasswordField.setText("");
        }
    }
}


// =========================================================================
// 7. MAIN APPLICATION ENTRY POINT
// =========================================================================

public class EventManagementApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}