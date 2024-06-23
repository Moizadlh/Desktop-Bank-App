package BMS;

//#region imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
//#endregion

public class Driver extends JPanel{
    //#region Parameters
    private JFrame frame;
    private JPanel mainPanel;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private Boolean isRegister = false ; 
    private Boolean Bankuser = false;
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    Font f=new Font("Arial",Font.BOLD,15);
    LineBorder lineBorder = new LineBorder(Color.BLACK, 2);
    Scanner sc = new Scanner(System.in);

    private Bank myBank;
    // private boolean bankAdmin = false;
    //#endregion

    //#region Constructor/Initializer
    public Driver(Bank Mybank) {
        this.myBank = Mybank;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Banking System");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setTitle(myBank.getName() + " Mangement System");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // This method will be called when the user clicks the close button
                // You can add your custom code here
                Store();
                // Close the window
                frame.dispose();
            }
        });

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(null); // Set layout to null for absolute positioning
        frame.add(mainPanel);

        // showWelcomePage();
        showWelcomePage();

        frame.setVisible(true);
    }
    //#endregion
    
    //#region Dialog box functions
    private static String showPasswordInputDialog() {
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Enter Your Password:", passwordField};
        
        int option = JOptionPane.showOptionDialog(
                null,
                message,
                "Password Input",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        if (option == JOptionPane.OK_OPTION) {
            return new String(passwordField.getPassword());
        } 
        else {
            return null; 
        }
    }

    // Register first
    private static int showCustomMessageDialog() {
        Object[] options = {"Signup", "Cancel"};
        return JOptionPane.showOptionDialog(
                null,
                "You have to login first.",
                "Information",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1] // Default button (in this case, 'Cancel')
        );
    }
    //#endregion
    
    //#region WELCOME PAGE

    private void showWelcomePage() {
        frame.setSize(900, 600);
        mainPanel.removeAll();
        // mainPanel.setBackground(Color.decode("#0e3c4f"));

        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\Bank1.jpeg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JLabel welcomeLabel = new JLabel("========== Welcome to " + myBank.getName().toUpperCase()+" ==========");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(40,30, 800, 50);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setForeground(Color.decode("#12a68c"));
        mainPanel.add(welcomeLabel);

        JButton bankInfoButton = new JButton("Bank Info");
        JButton clientLoginButton = new JButton("Use as Guest/Client");
        JButton adminLoginButton = new JButton("Login as Admin");
        JButton exitButton = new JButton("Exit");

        int buttonWidth = 160;
        int buttonHeight = 30;
        int buttonX = (frame.getWidth() - buttonWidth) / 2;

        bankInfoButton.setBounds(buttonX-250, 180, buttonWidth, buttonHeight);
        clientLoginButton.setBounds(buttonX-250, 230, buttonWidth, buttonHeight);
        adminLoginButton.setBounds(buttonX -250, 280, buttonWidth, buttonHeight);
        exitButton.setBounds(buttonX-250, 330, buttonWidth, buttonHeight);

        // Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        bankInfoButton.setFont(f);
        bankInfoButton.setBackground(Color.decode("#527A7A"));
        bankInfoButton.setForeground(Color.BLACK);
        bankInfoButton.setBorder(lineBorder);
        bankInfoButton.setCursor(handCursor);

        clientLoginButton.setFont(f);
        clientLoginButton.setBackground(Color.decode("#527A7A"));
        clientLoginButton.setForeground(Color.BLACK);
        clientLoginButton.setBorder(lineBorder);
        clientLoginButton.setCursor(handCursor);

        adminLoginButton.setFont(f);
        adminLoginButton.setBackground(Color.decode("#527A7A"));
        adminLoginButton.setForeground(Color.BLACK);
        adminLoginButton.setBorder(lineBorder);
        adminLoginButton.setCursor(handCursor);

        exitButton.setFont(f);
        exitButton.setBackground(Color.decode("#1a5e79"));
        exitButton.setForeground(Color.BLACK);
        exitButton.setBorder(lineBorder);
        exitButton.setCursor(handCursor);

        try {

            BufferedImage image = loadImage("C:\\Users\\PMYLS\\Desktop\\BMS\\Bank2.jpeg");
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            imageLabel.setBounds(100,160,700,270);
            // mainPanel.add(imageLabel);
        
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        bankInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement Bank Info functionality
                showBankInfo();
            }
        });

        clientLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement Login as Client functionality
                showClientPage();
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement Login as Admin functionality
                String adminName = JOptionPane.showInputDialog("Enter Admin name:");
                if (adminName != null && myBank.IsAdmin(adminName)) {
                    String adminPassword = showPasswordInputDialog();
                    if (adminPassword != null && myBank.IsAdpassw(adminPassword)) {
                        JOptionPane.showMessageDialog(null, "Yes, you are an Admin");
                        // BankAdmin = true;
                        showAdminPage();  // Redirect to the Admin page after successful login
                    } 
                    else {
                        if (adminPassword!=null) {
                            JOptionPane.showMessageDialog(null, "Invalid Admin password");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Login canceled succesfully");
                        }
                    }
                }
                else {
                    if (adminName!=null) {
                        JOptionPane.showMessageDialog(null, "Invalid Admin name");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Login canceled succesfully");
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Exit functionality
                Store();
                System.exit(0);
            }
        });

        mainPanel.add(bankInfoButton);
        mainPanel.add(clientLoginButton);
        mainPanel.add(adminLoginButton);
        mainPanel.add(exitButton);
        mainPanel.add(backgroundLabel);
        mainPanel.revalidate();
        mainPanel.repaint();
        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#region Bankinfo Page
    private void showBankInfo() {
        mainPanel.removeAll();
        // mainPanel.setBackground(Color.GRAY);
        
        JLabel bankLabel = new JLabel("======== " + myBank.getName().toUpperCase() + " A Best Bank ========");
        bankLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bankLabel.setHorizontalAlignment(JLabel.CENTER);
        bankLabel.setForeground(Color.decode("#12a68c"));
        bankLabel.setBounds(200, 30, 500, 50);

        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\Bank1.jpeg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        try {

            BufferedImage image = loadImage("C:\\Users\\PMYLS\\Desktop\\BMS\\logo1.png");
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            imageLabel.setBounds(220,20,60,70);
            mainPanel.add(imageLabel);

            BufferedImage image2 = loadImage("C:\\Users\\PMYLS\\Desktop\\BMS\\logo1.png");
            JLabel imageLabel2 = new JLabel(new ImageIcon(image2));
            imageLabel2.setBackground(null);
            imageLabel2.setBounds(620,20,60,70);
            mainPanel.add(imageLabel2);
        
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        JLabel bankDescriptiom= new JLabel("<html>"+ myBank.getName().toUpperCase() +" emerges as a paragon of efficiency in the banking sector. Renowned for its stellar customer service and cutting-edge technology. The bank ensures a seamless and secure experience.The bank prioritizes innovation, Regularly updating its digital platforms to provide customers a secure banking experience.</html>");
        bankDescriptiom.setFont(new Font("Arial", Font.BOLD, 15));
        bankDescriptiom.setHorizontalAlignment(JLabel.CENTER);
        bankDescriptiom.setForeground(Color.WHITE);
        bankDescriptiom.setBounds(60, 170, 400, 150);
        
        mainPanel.add(bankLabel);
        mainPanel.add(bankDescriptiom);

        JButton BackButton = new JButton("Back");
        BackButton.setBounds(150, 340, 100, 30);

        BackButton.setFont(f);
        BackButton.setBackground(Color.decode("#1a5e79"));
        BackButton.setForeground(Color.BLACK);
        BackButton.setBorder(lineBorder);
        BackButton.setCursor(handCursor);

        mainPanel.add(BackButton);
        mainPanel.add(backgroundLabel);

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bankuser = false;
                showWelcomePage();
                }
        });
        

        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#region Image Control Functions
    
    private static BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //#endregion

    //#region Register guest page

    private void Registerationpage(){
        mainPanel.removeAll();
        frame.setSize(1050,700);
        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\Reg.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JLabel usernameLabel, phoneLabel, passwordLabel, confirmPasswordLabel;
        JTextField usernameField, phoneField;
        JPasswordField passwordField, confirmPasswordField;
        Font f=new Font("Arial",Font.BOLD,16);

        JLabel clientRegisterLabel = new JLabel("====== Please fill Guest Data form ======");
        clientRegisterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clientRegisterLabel.setHorizontalAlignment(JLabel.CENTER);
        clientRegisterLabel.setForeground(Color.decode("#A9A9A9"));
        clientRegisterLabel.setBounds(270, 50, 500, 50);
        mainPanel.add(clientRegisterLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(250, 160, 100, 25);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(f);
       

        usernameField = new JTextField();
        usernameField.setBounds(400, 160, 200, 25);
        usernameField.setFont(f);

        phoneLabel = new JLabel("Phone #:");
        phoneLabel.setBounds(250, 200, 150, 25);
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(f);

        phoneField = new JTextField();
        phoneField.setBounds(400, 200, 200, 25);
        phoneField.setFont(f);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(250, 280, 150, 25);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(f);

        passwordField = new JPasswordField();
        passwordField.setBounds(400, 280, 200, 25);
        passwordField.setFont(f);
        

        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(250, 320, 150, 25);
        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setFont(f);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(400, 320, 200, 25);
        confirmPasswordField.setFont(f);

        JButton signupButton = new JButton("Sign UP");
        signupButton.setBounds(445, 420, 90, 30);
        signupButton.setCursor(handCursor);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(330, 420, 90, 30);
        cancelButton.setCursor(handCursor);

        JLabel signupLabel = new JLabel("Already sigup?");
        signupLabel.setFont(new Font("Arial",Font.ITALIC, 18));
        signupLabel.setForeground(Color.WHITE);
        // signupLabel.setHorizontalAlignment(JLabel.CENTER);
        signupLabel.setBounds(310, 380, 150, 30);

        JButton signinButton = new JButton("Sign In");
        signinButton.setBounds(445, 380, 90, 30);
        signinButton.setCursor(handCursor);

        JLabel NOTE = new JLabel("Important NOTE:");
        NOTE.setFont(new Font("Arial",Font.BOLD, 18));
        NOTE.setForeground(Color.WHITE);
        NOTE.setBounds(125, 500, 700, 25);
        JLabel NOTE2 = new JLabel("This is just a Raw data Collection for log-in as a guest in our Digital-Bank-App.");
        NOTE2.setFont(new Font("Arial",Font.ITALIC, 12));
        NOTE2.setHorizontalAlignment(JLabel.CENTER);
        NOTE2.setForeground(Color.WHITE);
        NOTE2.setBounds(135, 520, 700, 25);
        JLabel NOTE3 = new JLabel("You have to Register Yourself in Bank too for making accounts and use services of our Bank.");
        NOTE3.setFont(new Font("Arial",Font.ITALIC, 12));
        NOTE3.setForeground(Color.WHITE);
        NOTE3.setHorizontalAlignment(JLabel.CENTER);
        NOTE3.setBounds(135, 535, 700, 25);
        JLabel NOTE4 = new JLabel("This is only for security purposes.");
        NOTE4.setFont(new Font("Arial",Font.ITALIC, 12));
        NOTE4.setForeground(Color.WHITE);
        NOTE4.setHorizontalAlignment(JLabel.CENTER);
        NOTE4.setBounds(135, 550, 700, 25);
                


        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(confirmPasswordLabel);
        mainPanel.add(confirmPasswordField);
        mainPanel.add(signupLabel);

        cancelButton.setFont(f);
        cancelButton.setBackground(Color.decode("#527A7A"));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBorder(lineBorder);

        signupButton.setFont(f);
        signupButton.setBackground(Color.decode("#1a5e79"));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBorder(lineBorder);

        signinButton.setFont(f);
        signinButton.setBackground(Color.decode("#1a5e79"));
        signinButton.setForeground(Color.WHITE);
        signinButton.setBorder(lineBorder);

        mainPanel.add(signupButton);
        mainPanel.add(cancelButton);
        mainPanel.add(signinButton);
        mainPanel.add(NOTE);
        mainPanel.add(NOTE2);
        mainPanel.add(NOTE3);
        mainPanel.add(NOTE4);
        mainPanel.add(backgroundLabel);
        
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((usernameField != null) && (passwordField != null) && (confirmPasswordField != null)
                && (phoneField != null)) 
                {
                    if (Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword()))
                    {
                        Bankuser = true;
                        JOptionPane.showMessageDialog(null, "Signup succesfully");
                        String name = new String(usernameField.getText());
                        String passw = new String(passwordField.getPassword());
                        myBank.add_guest_name(name);
                        myBank.add_guest_passw(passw);
                        isRegister = false;
                        showClientPage();
                    }
                    else{
                    Bankuser = false;
                    JOptionPane.showMessageDialog(null, "Signup not Succeed");
                    }
                }
                else{
                    Bankuser = false;
                    JOptionPane.showMessageDialog(null, "Signup not Succeed");
                }
                }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bankuser = false;
                showClientPage();
                }
        });

        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = JOptionPane.showInputDialog("Enter User name:");
                if (userName != null && myBank.IsGuest(userName)) {
                    String userPassword = showPasswordInputDialog();
                    if (userPassword != null && myBank.IsUserpassw(userPassword)) {
                        Bankuser = true;
                        showClientPage();  // Redirect to the Client page after successful login
                        JOptionPane.showMessageDialog(null, "You logged in Successfully");
                    } 
                    else {
                        if (userPassword!=null) {
                            JOptionPane.showMessageDialog(null, "Invalid User password");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Login canceled succesfully");
                        }
                    }
                } 
                else{
                    if (userName!=null) {
                        JOptionPane.showMessageDialog(null, "Invalid User name");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Login canceled succesfully");
                    }
                }
            }
        });

        frame.revalidate();
        frame.repaint();

    }
    //#endregion

    //#region Clients Page

    private void showClientPage() {
        JButton backButton,Signup,exitButton;
        frame.setSize(1050, 700);
        mainPanel.removeAll();
        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\Clientback.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JLabel clientWelcomeLabel = new JLabel("====== Welcome, Client! ======");
        clientWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clientWelcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        clientWelcomeLabel.setForeground(Color.decode("#12a68c"));
        clientWelcomeLabel.setBounds(325, 50, 400, 50);
        mainPanel.add(clientWelcomeLabel);

        //#region Buttons

        int buttonWidth = 250;
        int buttonHeight = 30;
        int buttonX = 85;
        // int buttonX = (frame.getWidth() - buttonWidth) / 3; For center buttons

        JButton registerButton = new JButton("Add Your Data For Accounts");
        JButton openAccountButton = new JButton("OPEN YOUR Account");
        JButton searchAccountButton = new JButton("Search YOUR Account");
        JButton requestToRemoveButton = new JButton("Request to Remove Client");
        JButton searchDetailsButton = new JButton("Search Your Details");
        JButton depositButton = new JButton("Deposit Money in your account");
        JButton withdrawButton = new JButton("Withdraw Money in your account");
        JButton switchToAdminButton = new JButton("Switch to Admin Account");

        exitButton = new JButton("Exit");

        if (Bankuser) {
            backButton = new JButton("LOG OUT");
            backButton.setBounds(buttonX+750, 610, buttonWidth-140, buttonHeight);
            exitButton.setBounds(buttonX+450, 610, buttonWidth-140, buttonHeight);
        }
        else{
            backButton = new JButton("Back");
            backButton.setBounds(buttonX+550, 610, buttonWidth-140, buttonHeight);
            exitButton.setBounds(buttonX+350, 610, buttonWidth-140, buttonHeight);
        }
        if (!Bankuser) {
            Signup = new JButton("Sign up");
            Signup.setBounds(buttonX+750, 610, buttonWidth-140, buttonHeight);
            mainPanel.add(Signup);
        }
        else{
            Signup = new JButton();
        }
        

        registerButton.setBounds(buttonX, 180, buttonWidth, buttonHeight);
        openAccountButton.setBounds(buttonX, 230, buttonWidth, buttonHeight);
        searchAccountButton.setBounds(buttonX, 280, buttonWidth, buttonHeight);
        requestToRemoveButton.setBounds(buttonX, 330, buttonWidth, buttonHeight);
        searchDetailsButton.setBounds(buttonX, 380, buttonWidth, buttonHeight);
        depositButton.setBounds(buttonX, 430, buttonWidth, buttonHeight);
        withdrawButton.setBounds(buttonX, 480, buttonWidth, buttonHeight);
        switchToAdminButton.setBounds(buttonX, 530, buttonWidth, buttonHeight);

        registerButton.setBackground(Color.decode("#1a5e79"));
        openAccountButton.setBackground(Color.decode("#1a5e79"));
        searchAccountButton.setBackground(Color.decode("#1a5e79"));
        requestToRemoveButton.setBackground(Color.decode("#1a5e79"));
        searchDetailsButton.setBackground(Color.decode("#1a5e79"));
        depositButton.setBackground(Color.decode("#1a5e79"));
        withdrawButton.setBackground(Color.decode("#1a5e79"));
        switchToAdminButton.setBackground(Color.decode("#1a5e79"));
        Signup.setBackground(Color.decode("#527A7A"));
        backButton.setBackground(Color.decode("#527A7A"));
        exitButton.setBackground(Color.decode("#527A7A"));

        registerButton.setForeground(Color.WHITE);
        openAccountButton.setForeground(Color.WHITE);
        searchAccountButton.setForeground(Color.WHITE);
        requestToRemoveButton.setForeground(Color.WHITE);
        searchDetailsButton.setForeground(Color.WHITE);
        depositButton.setForeground(Color.WHITE);
        withdrawButton.setForeground(Color.WHITE);
        switchToAdminButton.setForeground(Color.WHITE);
        Signup.setForeground(Color.BLACK);
        backButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        registerButton.setBorder(lineBorder);
        openAccountButton.setBorder(lineBorder);
        searchAccountButton.setBorder(lineBorder);
        requestToRemoveButton.setBorder(lineBorder);
        searchDetailsButton.setBorder(lineBorder);
        depositButton.setBorder(lineBorder);
        withdrawButton.setBorder(lineBorder);
        switchToAdminButton.setBorder(lineBorder);
        backButton.setBorder(lineBorder);
        exitButton.setBorder(lineBorder);
        Signup.setBorder(lineBorder);

        registerButton.setFont(f);
        openAccountButton.setFont(f);
        searchAccountButton.setFont(f);
        requestToRemoveButton.setFont(f);
        searchDetailsButton.setFont(f);
        depositButton.setFont(f);
        withdrawButton.setFont(f);
        switchToAdminButton.setFont(f);
        Signup.setFont(f);
        backButton.setFont(f);
        exitButton.setFont(f);

        registerButton.setCursor(handCursor);
        openAccountButton.setCursor(handCursor);
        searchAccountButton.setCursor(handCursor);
        requestToRemoveButton.setCursor(handCursor);
        searchDetailsButton.setCursor(handCursor);
        depositButton.setCursor(handCursor);
        withdrawButton.setCursor(handCursor);
        switchToAdminButton.setCursor(handCursor);
        Signup.setCursor(handCursor);
        backButton.setCursor(handCursor);
        exitButton.setCursor(handCursor);

        //#endregion 

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Registered Yourself functionality
                if (!isRegister) {

                    if (Bankuser) {
                        String clientName = "";
                        String clientCnic = "";
                        String clientPhoneNo = "";
                        
                        clientName = JOptionPane.showInputDialog("Enter Your full name:");
                        
                        // Check if the user pressed cancel or closed the dialog
                        if (clientName != null && !clientName.isEmpty()) {
                            clientCnic = JOptionPane.showInputDialog("Enter Your CNIC:");
                        
                            // Check if the user pressed cancel or closed the dialog
                            if (clientCnic != null && !clientCnic.isEmpty()) {
                                clientPhoneNo = JOptionPane.showInputDialog("Enter Your phone number:");
                        
                                // Check if the user pressed cancel or closed the dialog
                                if (clientPhoneNo != null && !clientPhoneNo.isEmpty()) {
                                    // All fields are non-empty, proceed to further actions
                                    Person person = new Person(clientName, clientCnic, clientPhoneNo);
                                    myBank.add_Person(person);
                                    Client newClient = myBank.add_client(person);
                                    JOptionPane.showMessageDialog(null, "Proceed to Account Opening Register Successfully\n\n" + newClient);
                                    isRegister = true ;
                                } 
                                else {
                                    // User canceled or closed the phone number input dialog
                                    JOptionPane.showMessageDialog(null, "Please enter a valid phone number.");
                                }
                            } 
                            else {
                                // User canceled or closed the CNIC input dialog
                                JOptionPane.showMessageDialog(null, "Please enter a valid CNIC.");
                            }
                        }
                        else {
                            // User canceled or closed the name input dialog
                            JOptionPane.showMessageDialog(null, "Please enter a valid name.");
                        }
                    } 
                    else {
                        int result = showCustomMessageDialog();
                        if (result == JOptionPane.YES_OPTION) {
                            // User clicked 'Signup'
                            Registerationpage();
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Already Having your data");
                }
            }
        });

        openAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // OPEN YOUR ACCOUNT
                if (Bankuser) {
                //     String clientName = JOptionPane.showInputDialog("Enter Your full name:");
                //     String clientCnic = null;
                //     String clientPhoneNo = null;
                //     if (clientName!= null) {
                //         clientCnic = JOptionPane.showInputDialog("Enter Your CNIC:");
                //         if (clientName != null && clientCnic != null) {
                //             clientPhoneNo = JOptionPane.showInputDialog("Enter Your phone number:");
                //         }
                //     }

                //     if (clientName != null && clientCnic != null && clientPhoneNo != null) {
                //         Person person = new Person(clientName, clientCnic, clientPhoneNo);
                //         myBank.add_Person(person);
                //         Client newClient = myBank.add_client(person);
                //         JOptionPane.showMessageDialog(null, "Proceed to Account Opening\n\n" + newClient);
                //     }

                    String clientIdInput = JOptionPane.showInputDialog("Enter Your client ID for the account:");
                    if (clientIdInput != null) {
                        try {
                            int clientIdForAccount = Integer.parseInt(clientIdInput);
                            // The rest of your code for adding an account...
                            // Uncomment and modify the code as needed
                            Client clientForAccount = myBank.findClientById(clientIdForAccount);
                            if (clientForAccount != null) {
                                float initialAmount = Float.parseFloat(JOptionPane.showInputDialog("Enter initial amount for the account:"));
                                Account newAccount = myBank.addAccount(initialAmount, clientForAccount);
                                JOptionPane.showMessageDialog(null, "Account added successfully:\n\n" + newAccount);
                            } else {
                                JOptionPane.showMessageDialog(null, "Client not found.");
                            }
                        } 
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid client ID. Please enter a valid number.");
                        }
                    }
                } 
                else {
                    int result = showCustomMessageDialog();
                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked 'Signup'
                        Registerationpage();
                    }
                }
            }
        });

        searchAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Search YOUR Account functionality
                if (Bankuser) {
                    String accNoToSearch = JOptionPane.showInputDialog("Enter account number to search:");
                    if (accNoToSearch != null && !accNoToSearch.isEmpty()) {
                        Account foundAccount = myBank.searchAccount(accNoToSearch);
                        if (foundAccount != null) {
                            JOptionPane.showMessageDialog(null, "Found Account:\n" + foundAccount);
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Account not found.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Account Number Not Entered");
                    }
                } 
                else {
                    int result = showCustomMessageDialog();
                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked 'Signup'
                        Registerationpage();
                    }
                }
            }
        });

        requestToRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Request to Remove Client functionality
                if (Bankuser) {
                    String clientIdToRemoveInput = JOptionPane.showInputDialog("Enter Your client ID to remove:");
                        if (clientIdToRemoveInput != null) {
                        try {
                            int clientIdToRemove = Integer.parseInt(clientIdToRemoveInput);
                            if (myBank.findClient(clientIdToRemove)) {
                                String Reason = JOptionPane.showInputDialog("Enter A reason to remove:");
                                JOptionPane.showMessageDialog(null, "Your request is under processing");
                                myBank.Requests.add("remove because "+ Reason +"|"+clientIdToRemoveInput);
                            }
                             else {
                                JOptionPane.showMessageDialog(null, "Client not found.");
                            }
                        } 
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid client ID. Please enter a valid number.");
                        }
                    }
                } 
                else {
                    int result = showCustomMessageDialog();
                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked 'Signup'
                        Registerationpage();
                    }
                }
            }
        });

        searchDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Search Your Details functionality

                if (Bankuser) {
                    String customerCnicToSearch = JOptionPane.showInputDialog("Enter customer CNIC to search:");
                    if (customerCnicToSearch != null) {
                        Client foundCustomer = myBank.searchCustomerDetail(customerCnicToSearch);
                        if (foundCustomer != null) {
                            JOptionPane.showMessageDialog(null, "Your details:\n" + foundCustomer);
                        } else {
                            JOptionPane.showMessageDialog(null, "Customer not found.");
                        }
                    }
                } 

                // Register First
                else {
                    int result = showCustomMessageDialog();
                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked 'Signup'
                        Registerationpage();
                    }
                }

            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement Deposit Money in your account functionality

                if (Bankuser) {
                    String accNoToSearch = JOptionPane.showInputDialog("Enter account number to deposit:");
                    if (accNoToSearch != null) {
                        Account foundAccount = myBank.searchAccount(accNoToSearch);

                        if (foundAccount != null) {
                            float amountToDeposit = Float.parseFloat(JOptionPane.showInputDialog("Enter amount to deposit:"));
                            foundAccount.deposit(amountToDeposit);
                            JOptionPane.showMessageDialog(null, "Current Account details:\n" + foundAccount);
                            myBank.updateAccount(foundAccount);
                            Client client = foundAccount.getAccountHolder();
                            myBank.updateClient(client);
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Account not found.");
                        }
                    }  
                } 

                else {
                    int result = showCustomMessageDialog();
                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked 'Signup'
                        Registerationpage();
                    }
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement Withdraw Money in your account functionality

                if (Bankuser) {
                    String accNoToSearch = JOptionPane.showInputDialog("Enter account number to withdraw:");
                    if (accNoToSearch != null) {
                        Account foundAccount = myBank.searchAccount(accNoToSearch);
                        if (foundAccount != null) {
                            float amountToWithdraw = Float.parseFloat(JOptionPane.showInputDialog("Enter amount to withdraw:"));
                            foundAccount.withdraw(amountToWithdraw);
                            JOptionPane.showMessageDialog(null, "Current Account details:\n" + foundAccount);
                            myBank.updateAccount(foundAccount);
                            Client client = foundAccount.getAccountHolder();
                            myBank.updateClient(client);
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Account not found.");
                        }
                    }
                } 

                else {
                    int result = showCustomMessageDialog();
                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked 'Signup'
                        Registerationpage();
                    }
                }
            }
        });

        switchToAdminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            // backing as button
               String adminName = JOptionPane.showInputDialog("Enter Admin name:");
                if (adminName != null && myBank.IsAdmin(adminName)) {
                    String adminPassword = showPasswordInputDialog();
                    if (adminPassword != null && myBank.IsAdpassw(adminPassword)) {
                        JOptionPane.showMessageDialog(null, "Yes, you are an Admin");
                        Bankuser = false;
                        showAdminPage();  // Redirect to the Admin page after successful login
                    } 
                    else {
                        if (adminPassword!=null) {
                            JOptionPane.showMessageDialog(null, "Invalid Admin password");
                        }
                        else{
                        JOptionPane.showMessageDialog(null, "Login canceled succesfully");
                        }
                    }
                } 
                else {
                    if (adminName!=null) {
                        JOptionPane.showMessageDialog(null, "Invalid Admin name");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Login canceled succesfully");
                    }
                }

            }
        });

        Signup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Registerationpage();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Bankuser) {
                    JOptionPane.showMessageDialog(null, "Logout succesfully");
                }
                Bankuser = false;
                showWelcomePage();
            }
        });


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Store();
                System.exit(0);
            }
        });

        //#region panel hangdling
        mainPanel.add(registerButton);
        mainPanel.add(openAccountButton);
        mainPanel.add(searchAccountButton);
        mainPanel.add(requestToRemoveButton);
        mainPanel.add(searchDetailsButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawButton);
        mainPanel.add(switchToAdminButton);
        mainPanel.add(backButton);
        mainPanel.add(exitButton);
        mainPanel.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
        //#endregion
    }

    //#endregion

    //#region Admins page
    private void showAdminPage() {
        frame.setSize(1050, 700);
        mainPanel.removeAll();
        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\Clientback.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JLabel adminWelcomeLabel = new JLabel("====== Welcome, Admin! ======");
        adminWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        adminWelcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        adminWelcomeLabel.setForeground(Color.decode("#12a68c"));
        adminWelcomeLabel.setBounds(320, 10, 400, 50);
        mainPanel.add(adminWelcomeLabel);

        //#region Buttons
        JButton clientsButton = new JButton("Deal Clients");
        JButton accountsButton = new JButton("Deal Accounts");
        JButton RecordsButton = new JButton("See Records");
        JButton totalAmountButton = new JButton("Find Total Amount in the Bank");
        JButton SeeComplainButton = new JButton("Complains & Requests");
        JButton addClientButton = new JButton("Add New Client");
        JButton openAccountButton = new JButton("OPEN An Account");
        JButton searchAccountButton = new JButton("Search An Account");
        JButton removeClientButton = new JButton("Remove THE Client With his accounts");
        JButton searchCustomerButton = new JButton("Search the Customer Detail");
        JButton showAllClientsButton = new JButton("Show ALL the Clients");
        JButton depositButton = new JButton("Deposit Money in An account");
        JButton withdrawButton = new JButton("Withdraw Money from An account");
        JButton showAllPersonsButton = new JButton("Show ALL the Persons");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        int buttonWidth = 250;
        int buttonHeight = 30;
        int buttonX = 80;

        clientsButton.setBounds(buttonX, 220, buttonWidth, buttonHeight);
        accountsButton.setBounds(buttonX, 270, buttonWidth, buttonHeight);
        RecordsButton.setBounds(buttonX, 320, buttonWidth, buttonHeight);
        totalAmountButton.setBounds(buttonX, 370, buttonWidth, buttonHeight);
        SeeComplainButton.setBounds(buttonX, 420, buttonWidth, buttonHeight);
        // addClientButton.setBounds(buttonX, 100, buttonWidth, buttonHeight);
        // openAccountButton.setBounds(buttonX, 150, buttonWidth, buttonHeight);
        // searchAccountButton.setBounds(buttonX, 200, buttonWidth, buttonHeight);
        // removeClientButton.setBounds(buttonX, 250, buttonWidth, buttonHeight);
        // searchCustomerButton.setBounds(buttonX, 350, buttonWidth, buttonHeight);
        // showAllClientsButton.setBounds(buttonX, 400, buttonWidth, buttonHeight);
        depositButton.setBounds(buttonX, 450, buttonWidth, buttonHeight);
        withdrawButton.setBounds(buttonX, 500, buttonWidth, buttonHeight);
        showAllPersonsButton.setBounds(buttonX, 550, buttonWidth, buttonHeight);
        backButton.setBounds(buttonX, 510, buttonWidth, buttonHeight);
        exitButton.setBounds(buttonX, 540, buttonWidth, buttonHeight);

        clientsButton.setBackground(Color.decode("#1a5e79"));
        accountsButton.setBackground(Color.decode("#1a5e79"));
        RecordsButton.setBackground(Color.decode("#1a5e79"));
        totalAmountButton.setBackground(Color.decode("#1a5e79"));
        SeeComplainButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        exitButton.setBackground(Color.decode("#527A7A"));

        clientsButton.setForeground(Color.WHITE);
        accountsButton.setForeground(Color.WHITE);
        RecordsButton.setForeground(Color.WHITE);
        totalAmountButton.setForeground(Color.WHITE);
        SeeComplainButton.setForeground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        clientsButton.setBorder(lineBorder);
        accountsButton.setBorder(lineBorder);
        RecordsButton.setBorder(lineBorder);
        totalAmountButton.setBorder(lineBorder);
        SeeComplainButton.setBorder(lineBorder);
        backButton.setBorder(lineBorder);
        exitButton.setBorder(lineBorder);

        clientsButton.setFont(f);
        accountsButton.setFont(f);
        RecordsButton.setFont(f);
        totalAmountButton.setFont(f);
        SeeComplainButton.setFont(f);
        backButton.setFont(f);
        exitButton.setFont(f);

        clientsButton.setCursor(handCursor);;
        accountsButton.setCursor(handCursor);
        RecordsButton.setCursor(handCursor);
        totalAmountButton.setCursor(handCursor);
        SeeComplainButton.setCursor(handCursor);
        backButton.setCursor(handCursor);
        exitButton.setCursor(handCursor);


        //#endregion

        clientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin_Clientpage();
            }
        });

        accountsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin_accountpage();
            }
        });

        RecordsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin_Recordpage();
            }
        });

        SeeComplainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Complain_Recordshow();
            }
        });

        //#region Functions
        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Add New Client functionality
                String clientName = "";
                String clientCnic = "";
                String clientPhoneNo = "";
                
                clientName = JOptionPane.showInputDialog("Enter Your full name:");
                
                // Check if the user pressed cancel or closed the dialog
                if (clientName != null && !clientName.isEmpty()) {
                    clientCnic = JOptionPane.showInputDialog("Enter Your CNIC:");
                
                    // Check if the user pressed cancel or closed the dialog
                    if (clientCnic != null && !clientCnic.isEmpty()) {
                        clientPhoneNo = JOptionPane.showInputDialog("Enter Your phone number:");
                
                        // Check if the user pressed cancel or closed the dialog
                        if (clientPhoneNo != null && !clientPhoneNo.isEmpty()) {
                            // All fields are non-empty, proceed to further actions
                            Person person = new Person(clientName, clientCnic, clientPhoneNo);
                            myBank.add_Person(person);
                            Client newClient = myBank.add_client(person);
                            JOptionPane.showMessageDialog(null, "Proceed to Account Opening Register Successfully\n\n" + newClient);
                        } 
                        else {
                            // User canceled or closed the phone number input dialog
                            JOptionPane.showMessageDialog(null, "Please enter a valid phone number.");
                        }
                    } 
                    else {
                        // User canceled or closed the CNIC input dialog
                        JOptionPane.showMessageDialog(null, "Please enter a valid CNIC.");
                    }
                }
                else {
                    // User canceled or closed the name input dialog
                    JOptionPane.showMessageDialog(null, "Please enter a valid name.");
                }
                
            }
        });

        openAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // OPEN An Account functionality
                String clientIdInput = JOptionPane.showInputDialog("Enter Your client ID for the account:");
                    if (clientIdInput != null) {
                        try {
                            int clientIdForAccount = Integer.parseInt(clientIdInput);
                            // The rest of your code for adding an account...
                            // Uncomment and modify the code as needed
                            Client clientForAccount = myBank.findClientById(clientIdForAccount);
                            if (clientForAccount != null) {
                                float initialAmount = Float.parseFloat(JOptionPane.showInputDialog("Enter initial amount for the account:"));
                                Account newAccount = myBank.addAccount(initialAmount, clientForAccount);
                                JOptionPane.showMessageDialog(null, "Account added successfully:\n\n" + newAccount);
                            } else {
                                JOptionPane.showMessageDialog(null, "Client not found.");
                            }
                        } 
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid client ID. Please enter a valid number.");
                        }
                    }
            }
        });

        searchAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Search An Account functionality

                String accNoToSearch = JOptionPane.showInputDialog("Enter account number to search:");
                    if (accNoToSearch != null && !accNoToSearch.isEmpty()) {
                        Account foundAccount = myBank.searchAccount(accNoToSearch);
                        if (foundAccount != null) {
                            JOptionPane.showMessageDialog(null, "Found Account:\n" + foundAccount);
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Account not found.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Account Number Not Entered");
                    }
            }
        });

        removeClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove THE Client With his accounts functionality
                String clientIdToRemoveInput = JOptionPane.showInputDialog("Enter Your client ID to remove:");
                        if (clientIdToRemoveInput != null) {
                        try {
                            int clientIdToRemove = Integer.parseInt(clientIdToRemoveInput);
                            if (myBank.removeClient(clientIdToRemove)) {

                                JOptionPane.showMessageDialog(null, "Client and associated accounts removed successfully.");
                            } 
                            else {
                                JOptionPane.showMessageDialog(null, "Client not found.");
                            }
                        } 
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid client ID. Please enter a valid number.");
                        }
                    }
            }
        });

        totalAmountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Find Total Amount in the Bank functionality
                JOptionPane.showMessageDialog(null, "Total Amount in the Bank: " + myBank.totalAmount(), "Total Amount", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        searchCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Search the Customer Detail functionality
            }
        });

        showAllClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement Show ALL the Clients functionality
                Client_Recordshow();
            }
        });
        //#endregion

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWelcomePage();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Store();
                System.exit(0);
            }
        });

        mainPanel.add(clientsButton);
        mainPanel.add(accountsButton);
        mainPanel.add(RecordsButton);
        mainPanel.add(SeeComplainButton);
        // mainPanel.add(addClientButton);
        // mainPanel.add(openAccountButton);
        // mainPanel.add(searchAccountButton);
        // mainPanel.add(removeClientButton);
        mainPanel.add(totalAmountButton);
        // mainPanel.add(searchCustomerButton);
        // mainPanel.add(showAllClientsButton);
        // mainPanel.add(depositButton);
        // mainPanel.add(withdrawButton);
        // mainPanel.add(showAllPersonsButton);
        mainPanel.add(backButton);
        mainPanel.add(exitButton);

        mainPanel.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#region  Admin pages

    //#region Admin_Clientpage
    private void Admin_Clientpage(){
        frame.setSize(1050, 700);
        mainPanel.removeAll();
        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\acbg.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JLabel adminWelcomeLabel = new JLabel("======= Welcome, Admin! =======");
        adminWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminWelcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        adminWelcomeLabel.setForeground(Color.decode("#12a68c"));
        adminWelcomeLabel.setBounds(325, 60, 400, 50);
        mainPanel.add(adminWelcomeLabel);

        //#region Buttons

        JButton addClientButton = new JButton("Add New Client");
        JButton removeClientButton = new JButton("Remove THE Client With his accounts");
        JButton searchCustomerButton = new JButton("Search the Customer Detail");
        JButton TotalCustomerButton = new JButton("Total Clients");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        int buttonWidth = 280;
        int buttonHeight = 30;
        int buttonX1 = 15;
        int buttonX2 = 750;

        addClientButton.setBounds(buttonX1, 310, buttonWidth, buttonHeight);
        removeClientButton.setBounds(buttonX1, 350, buttonWidth, buttonHeight);
        searchCustomerButton.setBounds(buttonX2, 310, buttonWidth, buttonHeight);
        TotalCustomerButton.setBounds(buttonX2, 350, buttonWidth, buttonHeight);
        backButton.setBounds(360, 610, buttonWidth-140, buttonHeight);
        exitButton.setBounds(540, 610, buttonWidth-140, buttonHeight);

        addClientButton.setBackground(Color.decode("#1a5e79"));
        removeClientButton.setBackground(Color.decode("#1a5e79"));
        searchCustomerButton.setBackground(Color.decode("#1a5e79"));
        TotalCustomerButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        exitButton.setBackground(Color.decode("#527A7A"));

        addClientButton.setForeground(Color.WHITE);
        removeClientButton.setForeground(Color.WHITE);
        searchCustomerButton.setForeground(Color.WHITE);
        TotalCustomerButton.setForeground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        addClientButton.setBorder(lineBorder);
        removeClientButton.setBorder(lineBorder);
        searchCustomerButton.setBorder(lineBorder);
        TotalCustomerButton.setBorder(lineBorder);
        backButton.setBorder(lineBorder);
        exitButton.setBorder(lineBorder);

        addClientButton.setFont(f);
        removeClientButton.setFont(f);
        searchCustomerButton.setFont(f);
        TotalCustomerButton.setFont(f);
        backButton.setFont(f);
        exitButton.setFont(f);

        addClientButton.setCursor(handCursor);
        removeClientButton.setCursor(handCursor);
        searchCustomerButton.setCursor(handCursor);
        TotalCustomerButton.setCursor(handCursor);
        backButton.setCursor(handCursor);
        exitButton.setCursor(handCursor);

        //#endregion

        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Add New Client functionality
                String clientName = "";
                String clientCnic = "";
                String clientPhoneNo = "";
                
                clientName = JOptionPane.showInputDialog("Enter Your full name:");
                
                // Check if the user pressed cancel or closed the dialog
                if (clientName != null && !clientName.isEmpty()) {
                    clientCnic = JOptionPane.showInputDialog("Enter Your CNIC:");
                
                    // Check if the user pressed cancel or closed the dialog
                    if (clientCnic != null && !clientCnic.isEmpty()) {
                        clientPhoneNo = JOptionPane.showInputDialog("Enter Your phone number:");
                
                        // Check if the user pressed cancel or closed the dialog
                        if (clientPhoneNo != null && !clientPhoneNo.isEmpty()) {
                            // All fields are non-empty, proceed to further actions
                            Person person = new Person(clientName, clientCnic, clientPhoneNo);
                            myBank.add_Person(person);
                            Client newClient = myBank.add_client(person);
                            JOptionPane.showMessageDialog(null, "Proceed to Account Opening Register Successfully\n\n" + newClient);
                        } 
                        else {
                            // User canceled or closed the phone number input dialog
                            JOptionPane.showMessageDialog(null, "Please enter a valid phone number.");
                        }
                    } 
                    else {
                        // User canceled or closed the CNIC input dialog
                        JOptionPane.showMessageDialog(null, "Please enter a valid CNIC.");
                    }
                }
                else {
                    // User canceled or closed the name input dialog
                    JOptionPane.showMessageDialog(null, "Please enter a valid name.");
                }
                
            }
        });

        removeClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove THE Client With his accounts functionality
                String clientIdToRemoveInput = JOptionPane.showInputDialog("Enter Your client ID to remove:");
                        if (clientIdToRemoveInput != null) {
                        try {
                            int clientIdToRemove = Integer.parseInt(clientIdToRemoveInput);
                            if (myBank.removeClient(clientIdToRemove)) {

                                JOptionPane.showMessageDialog(null, "Client and associated accounts removed successfully.");
                            } 
                            else {
                                JOptionPane.showMessageDialog(null, "Client not found.");
                            }
                        } 
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid client ID. Please enter a valid number.");
                        }
                    }
            }
        });
        
        searchCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Search the Customer Detail functionality
                String customerCnicToSearch = JOptionPane.showInputDialog("Enter customer CNIC to search:");
                    if (customerCnicToSearch != null) {
                        Client foundCustomer = myBank.searchCustomerDetail(customerCnicToSearch);
                        if (foundCustomer != null) {
                            JOptionPane.showMessageDialog(null, "Your details:\n" + foundCustomer);
                        } else {
                            JOptionPane.showMessageDialog(null, "Customer not found.");
                        }
                    }
            }
        });

        TotalCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Total Customer Detail functionality
                JOptionPane.showMessageDialog(null, "Total customerss in Bank are:\n" + myBank.TotalCustomers());
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAdminPage();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Store();
                System.exit(0);
            }
        });


        mainPanel.add(addClientButton);
        mainPanel.add(removeClientButton);
        mainPanel.add(searchCustomerButton);
        mainPanel.add(TotalCustomerButton);
        mainPanel.add(backButton);
        mainPanel.add(exitButton);
        mainPanel.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#region Admin_accountpage
    private  void Admin_accountpage(){
        frame.setSize(1050, 700);
        mainPanel.removeAll();
        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\acbg.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JLabel adminWelcomeLabel = new JLabel("======= Welcome, Admin! =======");
        adminWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminWelcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        adminWelcomeLabel.setForeground(Color.decode("#12a68c"));
        adminWelcomeLabel.setBounds(325, 60, 400, 50);
        mainPanel.add(adminWelcomeLabel);

        //#region buttons
        JButton openAccountButton = new JButton("OPEN An Account");
        JButton searchAccountButton = new JButton("Search An Account");
        JButton depositButton = new JButton("Deposit Money in An account");
        JButton withdrawButton = new JButton("Withdraw Money from An account");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        int buttonWidth = 280;
        int buttonHeight = 30;
        int buttonX1 = 15;
        int buttonX2 = 750;

        openAccountButton.setBounds(buttonX1, 310, buttonWidth, buttonHeight);
        searchAccountButton.setBounds(buttonX1, 350, buttonWidth, buttonHeight);
        depositButton.setBounds(buttonX2, 310, buttonWidth, buttonHeight);
        withdrawButton.setBounds(buttonX2, 350, buttonWidth, buttonHeight);
        backButton.setBounds(360, 610, buttonWidth-140, buttonHeight);
        exitButton.setBounds(540, 610, buttonWidth-140, buttonHeight);

        openAccountButton.setBackground(Color.decode("#1a5e79"));
        searchAccountButton.setBackground(Color.decode("#1a5e79"));
        depositButton.setBackground(Color.decode("#1a5e79"));
        withdrawButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        exitButton.setBackground(Color.decode("#527A7A"));

        openAccountButton.setForeground(Color.WHITE);
        searchAccountButton.setForeground(Color.WHITE);
        depositButton.setForeground(Color.WHITE);
        withdrawButton.setForeground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        openAccountButton.setBorder(lineBorder);
        searchAccountButton.setBorder(lineBorder);
        depositButton.setBorder(lineBorder);
        withdrawButton.setBorder(lineBorder);
        backButton.setBorder(lineBorder);
        exitButton.setBorder(lineBorder);

        openAccountButton.setFont(f);
        searchAccountButton.setFont(f);
        depositButton.setFont(f);
        withdrawButton.setFont(f);
        backButton.setFont(f);
        exitButton.setFont(f);

        openAccountButton.setCursor(handCursor);
        searchAccountButton.setCursor(handCursor);
        depositButton.setCursor(handCursor);
        withdrawButton.setCursor(handCursor);
        backButton.setCursor(handCursor);
        exitButton.setCursor(handCursor);

        //#endregion

        openAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // OPEN An Account functionality
                String clientIdInput = JOptionPane.showInputDialog("Enter Your client ID for the account:");
                    if (clientIdInput != null) {
                        try {
                            int clientIdForAccount = Integer.parseInt(clientIdInput);
                            // The rest of your code for adding an account...
                            // Uncomment and modify the code as needed
                            Client clientForAccount = myBank.findClientById(clientIdForAccount);
                            if (clientForAccount != null) {
                                float initialAmount = Float.parseFloat(JOptionPane.showInputDialog("Enter initial amount for the account:"));
                                Account newAccount = myBank.addAccount(initialAmount, clientForAccount);
                                JOptionPane.showMessageDialog(null, "Account added successfully:\n\n" + newAccount);
                            } else {
                                JOptionPane.showMessageDialog(null, "Client not found.");
                            }
                        } 
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid client ID. Please enter a valid number.");
                        }
                    }
            }
        });

        searchAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Search An Account functionality

                String accNoToSearch = JOptionPane.showInputDialog("Enter account number to search:");
                    if (accNoToSearch != null && !accNoToSearch.isEmpty()) {
                        Account foundAccount = myBank.searchAccount(accNoToSearch);
                        if (foundAccount != null) {
                            JOptionPane.showMessageDialog(null, "Found Account:\n" + foundAccount);
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Account not found.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Account Number Not Entered");
                    }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Deposit Money in An account functionality
                String accNoToSearch = JOptionPane.showInputDialog("Enter account number to deposit:");
                    if (accNoToSearch != null) {
                        Account foundAccount = myBank.searchAccount(accNoToSearch);

                        if (foundAccount != null) {
                            float amountToDeposit = Float.parseFloat(JOptionPane.showInputDialog("Enter amount to deposit:"));
                            foundAccount.deposit(amountToDeposit);
                            JOptionPane.showMessageDialog(null, "Current Account details:\n" + foundAccount);
                            myBank.updateAccount(foundAccount);
                            Client client = foundAccount.getAccountHolder();
                            myBank.updateClient(client);
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Account not found.");
                        }
                    }  
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Withdraw Money from An account functionality
                String accNoToSearch = JOptionPane.showInputDialog("Enter account number to withdraw:");
                    if (accNoToSearch != null) {
                        Account foundAccount = myBank.searchAccount(accNoToSearch);
                        if (foundAccount != null) {
                            float amountToWithdraw = Float.parseFloat(JOptionPane.showInputDialog("Enter amount to withdraw:"));
                            foundAccount.withdraw(amountToWithdraw);
                            JOptionPane.showMessageDialog(null, "Current Account details:\n" + foundAccount);
                            myBank.updateAccount(foundAccount);
                            Client client = foundAccount.getAccountHolder();
                            myBank.updateClient(client);
                        } 
                        else {
                            JOptionPane.showMessageDialog(null, "Account not found.");
                        }
                    }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAdminPage();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Store();
                System.exit(0);
            }
        });

        mainPanel.add(openAccountButton);
        mainPanel.add(searchAccountButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawButton);
        mainPanel.add(backButton);
        mainPanel.add(exitButton);
        mainPanel.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#region Admin_Recordpage
    private void Admin_Recordpage(){
        frame.setSize(1050, 700);
        mainPanel.removeAll();
        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\acbg.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JLabel adminWelcomeLabel = new JLabel("======= Welcome, Admin! =======");
        adminWelcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminWelcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        adminWelcomeLabel.setForeground(Color.decode("#12a68c"));
        adminWelcomeLabel.setBounds(325, 60, 400, 50);
        mainPanel.add(adminWelcomeLabel);

        //#region Buttons
        JButton showAllClientsButton = new JButton("Show ALL the Clients");
        JButton showAllPersonsButton = new JButton("Show ALL the Persons");
        JButton showAllAccountsButton = new JButton("Show ALL the Accounts");
        JButton showAllGuestsButton = new JButton("Show ALL the Guests");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        int buttonWidth = 280;
        int buttonHeight = 30;
        int buttonX1 = 15;
        int buttonX2 = 750;

        showAllClientsButton.setBounds(buttonX1, 310, buttonWidth, buttonHeight);
        showAllAccountsButton.setBounds(buttonX1, 350, buttonWidth, buttonHeight);
        showAllPersonsButton.setBounds(buttonX2, 310, buttonWidth, buttonHeight);
        showAllGuestsButton.setBounds(buttonX2, 350, buttonWidth, buttonHeight);
        backButton.setBounds(360, 610, buttonWidth-140, buttonHeight);
        exitButton.setBounds(540, 610, buttonWidth-140, buttonHeight);

        showAllClientsButton.setBackground(Color.decode("#1a5e79"));
        showAllAccountsButton.setBackground(Color.decode("#1a5e79"));
        showAllPersonsButton.setBackground(Color.decode("#1a5e79"));
        showAllGuestsButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        exitButton.setBackground(Color.decode("#527A7A"));

        showAllClientsButton.setForeground(Color.WHITE);
        showAllAccountsButton.setForeground(Color.WHITE);
        showAllPersonsButton.setForeground(Color.WHITE);
        showAllGuestsButton.setForeground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        showAllClientsButton.setBorder(lineBorder);
        showAllAccountsButton.setBorder(lineBorder);
        showAllPersonsButton.setBorder(lineBorder);
        showAllGuestsButton.setBorder(lineBorder);
        backButton.setBorder(lineBorder);
        exitButton.setBorder(lineBorder);

        showAllClientsButton.setFont(f);
        showAllAccountsButton.setFont(f);
        showAllPersonsButton.setFont(f);
        showAllGuestsButton.setFont(f);
        backButton.setFont(f);
        exitButton.setFont(f);

        showAllClientsButton.setCursor(handCursor);
        showAllAccountsButton.setCursor(handCursor);
        showAllPersonsButton.setCursor(handCursor);
        showAllGuestsButton.setCursor(handCursor);
        backButton.setCursor(handCursor);
        exitButton.setCursor(handCursor);


        //#endregion

        showAllClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client_Recordshow();
            }
        });

        showAllAccountsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Account_Recordshow();
            }
        });

        showAllPersonsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Person_Recordshow();
            }
        });

        showAllGuestsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Guest_Recordshow();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAdminPage();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Store();
                System.exit(0);
            }
        });

        mainPanel.add(showAllClientsButton);
        mainPanel.add(showAllAccountsButton);
        mainPanel.add(showAllPersonsButton);
        mainPanel.add(showAllGuestsButton);
        mainPanel.add(backButton);
        mainPanel.add(exitButton);
        mainPanel.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#endregion
    
    //#region Records
    private void Client_Recordshow() {
        frame.setSize(750, 500);
    
        mainPanel.removeAll();

        ImageIcon backgroundImage = new ImageIcon("E:\\BMS\\Record.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
    
        // String[] columnNames = {"ID", "Name", "CNIC", "Phone_number"};
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("CNIC");
        tableModel.addColumn("Contact");
    
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        // Bold and larger font for column headers
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20));
    
        // Set row height
        table.setRowHeight(25);
    
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                } 
                else {
                    c.setBackground(Color.decode("#1da6ab"));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        loadClients();
    
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 600, calculatePreferredHeight(table)); 
        mainPanel.add(scrollPane);
    
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 380, 100, 30); 
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin_Recordpage();
            }
        });
        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setBounds(200, 380, 100, 30); 
        RefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadClients();
            }
        });

        RefreshButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        backButton.setForeground(Color.BLACK);
        RefreshButton.setForeground(Color.BLACK);
        backButton.setBorder(lineBorder);
        RefreshButton.setBorder(lineBorder);
        RefreshButton.setFont(f);
        backButton.setFont(f);
        RefreshButton.setCursor(handCursor);
        backButton.setCursor(handCursor);

        mainPanel.add(RefreshButton);
        mainPanel.add(backButton);
        // mainPanel.add(table);
    
        mainPanel.add(backgroundLabel);
    
        // Set the new window to be visible
        frame.revalidate();
        frame.repaint();
    }
    
    //#region Records_Account
    private void Account_Recordshow() {
        frame.setSize(750, 500);
    
        mainPanel.removeAll();

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\PMYLS\\Desktop\\BMS\\Record.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
    
        // String[] columnNames = {"ID", "Name", "CNIC", "Phone_number"};
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Acc No.");
        tableModel.addColumn("Name");
        tableModel.addColumn("CNIC");
        tableModel.addColumn("Holder ID");
        tableModel.addColumn("Amount");
    
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        // Bold and larger font for column headers
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20));
    
        // Set row height
        table.setRowHeight(25);
    
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                } 
                else {
                    c.setBackground(Color.decode("#1da6ab"));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        loadAccounts();
    
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 600, calculatePreferredHeight(table));  // Adjust the position and size as needed
        mainPanel.add(scrollPane);
    
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 400, 100, 30); // Adjust the position and size as needed
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin_Recordpage();
            }
        });
        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setBounds(220, 400, 100, 30); // Adjust the position and size as needed
        RefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAccounts();
            }
        });

        RefreshButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        backButton.setForeground(Color.BLACK);
        RefreshButton.setForeground(Color.BLACK);
        backButton.setBorder(lineBorder);
        RefreshButton.setBorder(lineBorder);
        RefreshButton.setFont(f);
        backButton.setFont(f);
        RefreshButton.setCursor(handCursor);
        backButton.setCursor(handCursor);

        mainPanel.add(RefreshButton);
        mainPanel.add(backButton);
        // mainPanel.add(table);
    
        mainPanel.add(backgroundLabel);
    
        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#region Records_Person
    private void Person_Recordshow() {
        frame.setSize(750, 500);
    
        mainPanel.removeAll();

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\PMYLS\\Desktop\\BMS\\Record.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
    
        // String[] columnNames = {"ID", "Name", "CNIC", "Phone_number"};
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("CNIC");
        tableModel.addColumn("Contact");
    
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        // Bold and larger font for column headers
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20));
    
        // Set row height
        table.setRowHeight(25);
    
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                } 
                else {
                    c.setBackground(Color.decode("#1da6ab"));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        loadPersons();
    
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 600, calculatePreferredHeight(table));  // Adjust the position and size as needed
        mainPanel.add(scrollPane);
    
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 400, 100, 30); // Adjust the position and size as needed
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin_Recordpage();
            }
        });
        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setBounds(220, 400, 100, 30); // Adjust the position and size as needed
        RefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadPersons();
            }
        });

        RefreshButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        backButton.setForeground(Color.BLACK);
        RefreshButton.setForeground(Color.BLACK);
        backButton.setBorder(lineBorder);
        RefreshButton.setBorder(lineBorder);
        RefreshButton.setFont(f);
        backButton.setFont(f);
        RefreshButton.setCursor(handCursor);
        backButton.setCursor(handCursor);

        mainPanel.add(RefreshButton);
        mainPanel.add(backButton);
        // mainPanel.add(table);
    
        mainPanel.add(backgroundLabel);
    
        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#region Records_Guest
    private void Guest_Recordshow() {
        frame.setSize(750, 500);
    
        mainPanel.removeAll();

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\PMYLS\\Desktop\\BMS\\Record.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
    
        // String[] columnNames = {"ID", "Name", "CNIC", "Phone_number"};
        tableModel = new DefaultTableModel();
        tableModel.addColumn("SR Np.");
        tableModel.addColumn("UserName");
        tableModel.addColumn("Password");
    
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        // Bold and larger font for column headers
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20));
    
        // Set row height
        table.setRowHeight(25);
    
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                } 
                else {
                    c.setBackground(Color.decode("#1da6ab"));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        loadGuests();
    
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 600, calculatePreferredHeight(table));  // Adjust the position and size as needed
        mainPanel.add(scrollPane);
    
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 400, 100, 30); // Adjust the position and size as needed
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin_Recordpage();
            }
        });
        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setBounds(220, 400, 100, 30); // Adjust the position and size as needed
        RefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadGuests();
            }
        });

        RefreshButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        backButton.setForeground(Color.BLACK);
        RefreshButton.setForeground(Color.BLACK);
        backButton.setBorder(lineBorder);
        RefreshButton.setBorder(lineBorder);
        RefreshButton.setFont(f);
        backButton.setFont(f);
        RefreshButton.setCursor(handCursor);
        backButton.setCursor(handCursor);

        mainPanel.add(RefreshButton);
        mainPanel.add(backButton);
        // mainPanel.add(table);
    
        mainPanel.add(backgroundLabel);
    
        frame.revalidate();
        frame.repaint();
    }
    //#endregion
 
    //#region Complains_Account
    private void Complain_Recordshow() {
        frame.setSize(750, 500);
    
        mainPanel.removeAll();

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\PMYLS\\Desktop\\BMS\\Record.jpg");
        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
    
        // String[] columnNames = {"ID", "Name", "CNIC", "Phone_number"};
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Sr No.");
        tableModel.addColumn("Complain");
        tableModel.addColumn("Id");
        

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        // Bold and larger font for column headers
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20));
    
        // Set row height
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setMinWidth(70); // Set the width for columns
        table.getColumnModel().getColumn(1).setMinWidth(530);
        table.getColumnModel().getColumn(0).setMaxWidth(70); 
        table.getColumnModel().getColumn(1).setMaxWidth(530);
        table.getColumnModel().getColumn(2).setMinWidth(70);
        table.getColumnModel().getColumn(2).setMaxWidth(70); 
    
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                } 
                else {
                    c.setBackground(Color.decode("#1da6ab"));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        loadComplains();
    
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 670, calculatePreferredHeight(table));  // Adjust the position and size as needed
        mainPanel.add(scrollPane);
    
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 400, 100, 30); // Adjust the position and size as needed
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAdminPage();
            }
        });
        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setBounds(220, 400, 100, 30); // Adjust the position and size as needed
        RefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadComplains();
            }
        });

        RefreshButton.setBackground(Color.decode("#1a5e79"));
        backButton.setBackground(Color.decode("#527A7A"));
        backButton.setForeground(Color.BLACK);
        RefreshButton.setForeground(Color.BLACK);
        backButton.setBorder(lineBorder);
        RefreshButton.setBorder(lineBorder);
        RefreshButton.setFont(f);
        backButton.setFont(f);
        RefreshButton.setCursor(handCursor);
        backButton.setCursor(handCursor);

        mainPanel.add(RefreshButton);
        mainPanel.add(backButton);
        // mainPanel.add(table);
    
        mainPanel.add(backgroundLabel);
    
        frame.revalidate();
        frame.repaint();
    }
    //#endregion

    //#endregion

    //#region Loaders
    private void loadClients() {
        tableModel.setRowCount(0);
        if (myBank != null && myBank.getCllist() != null) {
            for (Client c : myBank.getCllist()) {
                Object[] rowData = {c.getId(), c.Person_Name(), c.Person_CNIC(), c.Person_Phone()};
    
                // Add the rowData to the tableModel
                tableModel.addRow(rowData);
            }
        }
    }

    private void loadAccounts() {
        tableModel.setRowCount(0);
        if (myBank != null && myBank.getAclist() != null) {
            for (Account a: myBank.getAclist()) {
                Object[] rowData = {a.getNumber(), a.GetNameofclient(), a.GetCnicofclient(), a.GetIdofclient(), a.getAmount()};
    
                // Add the rowData to the tableModel
                tableModel.addRow(rowData);
            }
        }
    }

    private void loadPersons() {
        tableModel.setRowCount(0);
        if (myBank != null && myBank.getpList() != null) {
            for (Person p: myBank.getpList()) {
                Object[] rowData = {p.getName(), p.getCNIC(), p.getPhone()};
    
                // Add the rowData to the tableModel
                tableModel.addRow(rowData);
            }
        }
    }

    private void loadGuests() {
        tableModel.setRowCount(0);
        int i = 1;
        if (myBank != null && myBank.getUsernames() != null && myBank.getUserpassw() != null) {
            for (int j = 0; j < myBank.getUsernames().size(); j++) {
                String password = myBank.getUserpassw().get(j);
                int passwordLength = password.length();

                // Create a string of '#' characters with the same length as the password
                StringBuilder resultString = new StringBuilder();
                for (int k = 0; k < passwordLength; k++) {
                    resultString.append("#");
                }
                Object[] rowData = {i++, myBank.getUsernames().get(j), resultString.charAt(1)+"#"+"#"+hashCode()};
                    // Add the rowData to the tableModel
                tableModel.addRow(rowData);
            }
        }
    }
    
    private void loadComplains() {
        tableModel.setRowCount(0);
        int i = 1;
        if (myBank != null && myBank.getRequests() != null) {
            for (String R: myBank.getRequests()) {
                String[] parts = R.split("\\|");
                Object[] rowData = {i++,parts[0],parts[1]};
    
                // Add the rowData to the tableModel
                tableModel.addRow(rowData);
            }
        }
    }

    private int calculatePreferredHeight(JTable table) {
        // Calculate preferred height based on the number of rows
        int rowCount = table.getRowCount();
        int rowHeight = table.getRowHeight();
        int headerHeight = table.getTableHeader().getPreferredSize().height;
        if ((rowCount * rowHeight + headerHeight) > 275) {
            return 275;
        } 
        else {
            if ((rowCount * rowHeight + headerHeight) < 50) {
                return 75;
            }
            else{
                return (rowCount * rowHeight) + headerHeight;
            }
        }

    }
    //#endregion

    //#region FileHandling
        private void Store(){
            try {
            myBank.Person_To_File();
            myBank.To_File();
            myBank.Acc_To_File();
            myBank.GUEST_To_File();
            myBank.GUESTP_To_File();
            myBank.Req_To_File();
            } 
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    //#endregion

    public static void main(String[] args)throws IOException, ClassNotFoundException{ 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String bankName = JOptionPane.showInputDialog("Enter Bank Name:");
                if (bankName!= null && !bankName.isEmpty()) {
                    Bank myBank = new Bank(bankName);
                        try {
                            myBank.Person_from_File();
                            myBank.from_File();
                            myBank.Acc_from_File();
                            myBank.GUEST_from_File();
                            myBank.GUESTP_from_File();
                            myBank.Req_from_File();
                        } 
                        catch (ClassNotFoundException | IOException e)
                        {
                            e.printStackTrace();
                        }

                    new Driver(myBank);
                }
            }
        });
        
    }
}

//#region Extra classes to round borders
class CircularImagePanel extends JPanel {
    private Image image;
    private int width;
    private int height;

    public CircularImagePanel(Image image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    private Image scaleImage(Image image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(new Ellipse2D.Float(0, 0, width, height));  // Set clipping to ellipse
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        Image scaledImage = scaleImage(image, diameter, diameter);

        Graphics2D g2d = (Graphics2D) g.create();
        Ellipse2D.Double circle = new Ellipse2D.Double(315, 20, 250, 250);
        g2d.setClip(circle);
        g2d.drawImage(scaledImage, x, y, diameter, diameter, this);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
        g2d.dispose();
    }
}

class RoundedBorder extends AbstractBorder {

        private int radius;
        private int borderWidth;

        public RoundedBorder(int radius, int borderWidth) {
            this.radius = radius;
            this.borderWidth = borderWidth;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            int totalWidth = 2 * (radius + borderWidth);
            return new Insets(totalWidth, totalWidth, totalWidth + 1, totalWidth);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getBackground());
            g.fillRoundRect(x, y, width, height, radius, radius);
            g.setColor(c.getForeground());
            ((Graphics2D) g).setStroke(new BasicStroke(borderWidth * 2));
            g.drawRoundRect(x + borderWidth, y + borderWidth, width - 2 * borderWidth, height - 2 * borderWidth, radius, radius);
        }
    }
//#endregion

// The End