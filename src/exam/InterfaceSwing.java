package exam;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Timer;
import java.util.TimerTask;

public class InterfaceSwing extends JFrame {
    private final GestionnaireColis gestionnaire = new GestionnaireColis();
    private DefaultListModel<String> listModel;
    private JTextField idField;
    private JTextField destinationField;
    private JPanel mainPanel;

    // Dark mode color palette
    private static final Color DARK_BACKGROUND = new Color(30, 30, 40);
    private static final Color DARK_PANEL_BACKGROUND = new Color(40, 42, 54);
    private static final Color ACCENT_COLOR = new Color(98, 114, 164);
    private static final Color TEXT_COLOR = Color.WHITE; 
    private static final Color SECONDARY_TEXT_COLOR = new Color(170, 170, 180);
    private static final Color SUCCESS_COLOR = new Color(80, 250, 123);
    private static final Color ERROR_COLOR = new Color(255, 85, 85);

    private void startPeriodicListUpdate() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> updateList());
            }
        }, 0, 500);
    }

    public InterfaceSwing() {
        // Configure frame
        setTitle("Système de Livraison de Colis");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(DARK_BACKGROUND);

        // Create main panel with dark styling
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add title
        JLabel titleLabel = createTitleLabel();
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Input panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel);
        //adds a 20 pixel vertical space to the mainPanel component.
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Action buttons panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Colis list panel
        JPanel listPanel = createListPanel();
        mainPanel.add(listPanel);

        // Add main panel to frame
        add(mainPanel);

        // Start periodic list updates
        startPeriodicListUpdate();

        // Center the frame on screen
        setLocationRelativeTo(null);
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Gestion des Colis");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return titleLabel;
    }

    private JPanel createInputPanel() {
    	
    	/*JPanel : Un conteneur Swing pour organiser les composants. 
        GridBagLayout : Un gestionnaire de mise en page flexible pour organiser les composants dans une grille avec des contraintes.
        setOpaque(false) : Rend le panneau transparent pour un design plus moderne.
        insets : Définit les marges autour des composants (5 px sur chaque côté).
        fill = GridBagConstraints.HORIZONTAL : Permet au composant de s'étendre horizontalement pour remplir la largeur disponible.
   	    gbc.weightx = 0.3 : Réserve une proportion de l'espace disponible pour l'étiquette.
   	    gbc.weightx = 0.7 : Alloue 70 % de l'espace horizontal au champ texte.
   	    LineBorder : Une bordure grise arrondie (1 px).
        EmptyBorder : Une marge intérieure de 5 px pour aérer le texte.
   	 * */
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel idLabel = new JLabel("ID du Colis:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        idLabel.setForeground(SECONDARY_TEXT_COLOR);
        inputPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 16));
        idField.setBackground(DARK_PANEL_BACKGROUND);
        idField.setForeground(Color.WHITE);
        idField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        inputPanel.add(idField, gbc);

        // Destination Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        destinationLabel.setForeground(SECONDARY_TEXT_COLOR);
        inputPanel.add(destinationLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        destinationField = new JTextField();
        destinationField.setFont(new Font("Arial", Font.PLAIN, 16));
        destinationField.setBackground(DARK_PANEL_BACKGROUND);
        destinationField.setForeground(Color.WHITE);
        destinationField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        inputPanel.add(destinationField, gbc);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        // Enregistrer Colis Button
        JButton ajouterColis = createStyledButton("Enregistrer Colis", ACCENT_COLOR);
        ajouterColis.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String destination = destinationField.getText();

                // Validate input
                if (id <= 0 || destination.isEmpty()) {
                    showErrorDialog("L'ID et la destination doivent être valides.");
                    return;
                }

                // Create the thread for registering the colis and start it
                ThreadEnregistrement thread = new ThreadEnregistrement(gestionnaire, id, destination);
                thread.start();

                // Wait for the thread to complete (using Thread.join) before checking result
                thread.join();

                // Check the result after the thread has finished execution
                if (thread.isTest()) {
                    showSuccessDialog("Colis enregistré avec succès!");

                    // Start the delivery process automatically after registration
                    new ThreadLivraison(gestionnaire, id).start();

                    // Clear the input fields after registration
                    idField.setText("");
                    destinationField.setText("");

                } else {
                    showErrorDialog("L'ID existe déjà.");
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("L'ID et la destination doivent être valides.");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        buttonPanel.add(ajouterColis);

        return buttonPanel;
    }

    private JPanel createListPanel() {
        JPanel listPanel = new JPanel(new BorderLayout(15, 15));
        listPanel.setOpaque(false);

        // Liste pour afficher les statuts des colis
        listModel = new DefaultListModel<>();
        JList<String> colisList = new JList<>(listModel);
        colisList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // Customized list colors
        colisList.setBackground(new Color(50, 52, 64)); 
        colisList.setForeground(new Color(240, 240, 240)); 
        colisList.setSelectionBackground(new Color(98, 114, 164)); 
        colisList.setSelectionForeground(Color.WHITE);
        
        colisList.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(98, 114, 164), 2, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JScrollPane scrollPane = new JScrollPane(colisList);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(98, 114, 164), 2, true),
                "Liste des Colis",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        );
        titledBorder.setTitleColor(new Color(98, 114, 164));
        scrollPane.setBorder(titledBorder);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        //setCellRenderer: Allows you to define how each element of the list (parcelList) will be rendered graphically.
        //  A new DefaultListCellRenderer is created and customized. This renderer controls the appearance of the cells displayed in the list.
        colisList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, 
                boolean isSelected, boolean cellHasFocus) {
                
                JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
                
                
                // Color coding based on status
                if (value.toString().contains("Statut: En attente")) {
                    label.setForeground(new Color(255, 184, 108)); // Orange for waiting
                } else if (value.toString().contains("Statut: Livré")) {
                    label.setForeground(new Color(80, 250, 123)); // Green for delivered
                } else if (value.toString().contains("Statut: En transit")) {
                    label.setForeground(new Color(189, 147, 249)); // Purple for in transit
                }
                
                return label;
            }
        });

        listPanel.add(scrollPane, BorderLayout.CENTER);

        return listPanel;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 10, 20),
                BorderFactory.createLineBorder(ACCENT_COLOR, 2, true)
        ));
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        return button;
    }

    private void updateList() {
        listModel.clear();
        for (Colis colis : gestionnaire.obtenirTousLesColis()) {
            listModel.addElement("Colis ID: " + colis.getId() + " - Statut: " + colis.getStatut() + " - Destination: " + colis.getDestination());
        }
    }

    private void showErrorDialog(String message) {
        UIManager.put("OptionPane.background", DARK_BACKGROUND);
        UIManager.put("Panel.background", DARK_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", ERROR_COLOR);
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Erreur");
        dialog.getContentPane().setBackground(DARK_BACKGROUND);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void showSuccessDialog(String message) {
        UIManager.put("OptionPane.background", DARK_BACKGROUND);
        UIManager.put("Panel.background", DARK_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", SUCCESS_COLOR);
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Succès");
        dialog.getContentPane().setBackground(DARK_BACKGROUND);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceSwing frame = new InterfaceSwing();
            frame.setVisible(true);
        });
    }
}