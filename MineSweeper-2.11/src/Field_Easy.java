import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * 
 *@author Jake & Tales
 */
public class Field_Easy extends JFrame implements ActionListener {
    
    Field_Intermediate field_Intermediate;
    Field_Extreme field_Extreme;

    
    MouseListener ms = new MouseAdapter() {
        
        /**
         * Overrides the mousePressed method of the MouseEvent class.
         *
         * @param  e The MouseEvent object representing the mouse press event.
         * @return   void.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if(!gameover) {
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < cols; y++) {
                        if (e.getSource() == buttons_easy[x][y]) {
                            if (color.isSelected()) {
                                faceButton.setIcon(shocked);
                            } else {
                                faceButton.setIcon(shockedOld);
                            }
                            if (SwingUtilities.isRightMouseButton(e)) {
                                if (marks.isSelected()) {
                                    if(compareImageIcons((ImageIcon)buttons_easy[x][y].getIcon(), flag) ||
                                        compareImageIcons((ImageIcon)buttons_easy[x][y].getIcon(), flagOld)) {
                                        rightClickCount = 1;
                                    } else if (compareImageIcons((ImageIcon)buttons_easy[x][y].getIcon(), question)) {
                                        rightClickCount = 2;
                                    } else {
                                        rightClickCount = 0;
                                    }
                                    if (buttons_easy[x][y].getBorder() == BorderFactory.createRaisedBevelBorder()) {

                                        switch (rightClickCount) {
                                            case 0:
                                                if(number_flag < flags_num) {
                                                    if(color.isSelected()) {
                                                        buttons_easy[x][y].setIcon(flag);
                                                    } else {
                                                        buttons_easy[x][y].setIcon(flagOld);
                                                    }
                                                }
                                            break;
                                            case 1:
                                            buttons_easy[x][y].setIcon(question);
                                            break;
                                            case 2:
                                            buttons_easy[x][y].setIcon(switchBomb(0));
                                            break;
                                        }  
                                    }
                                } else {
                                    if(compareImageIcons((ImageIcon)buttons_easy[x][y].getIcon(), flag) ||
                                        compareImageIcons((ImageIcon)buttons_easy[x][y].getIcon(), flagOld)) {
                                        rightClickCount = 1;
                                    } else {
                                        rightClickCount = 0;
                                    }
                                    if (color.isSelected()) {
                                        faceButton.setIcon(shocked);
                                    } else {
                                        faceButton.setIcon(shockedOld);
                                    }
                                    if (buttons_easy[x][y].getBorder() == BorderFactory.createRaisedBevelBorder()) {
                                        
                                        switch (rightClickCount) {
                                            case 0:
                                                if(color.isSelected()){
                                                    buttons_easy[x][y].setIcon(flag);
                                                } else {
                                                    buttons_easy[x][y].setIcon(flagOld);
                                                }
                                            break;
                                            case 1:
                                            buttons_easy[x][y].setIcon(switchBomb(0));
                                            break;
                                        }  
                                    }
                                }
                                buttons_easy[x][y].setFocusable(false);
                            }
                            number_flag = 0;
                                for(int i=0; i<9; i++) {
                                    for(int j=0; j<9; j++) {
                                        if (compareImageIcons((ImageIcon)buttons_easy[i][j].getIcon(), flag)  || 
                                            compareImageIcons((ImageIcon)buttons_easy[i][j].getIcon(), flagOld)) {
                                            number_flag++;
                                        }
                                    }
                                }
                            flags_num -= number_flag;
                            if(flags_num >= 0) {
                                if (flags_num < 10) {
                                    flags.setText("00" + flags_num);
                                } else if (flags_num < 100) {
                                    flags.setText("0" + flags_num);
                                } else {
                                    flags.setText("" + flags_num);
                                }
                            }
                            flags_num = 10;
                        }
                    }
                }
            }
        }


        /**
         * Handles the mouse released event.
         *
         * @param  e The MouseEvent object representing the event.
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!gameover) {
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        if (e.getSource() == buttons_easy[x][y]) {
                            if (compareImageIcons((ImageIcon)label_easy[x][y].getIcon(), bomb) && compareImageIcons((ImageIcon) buttons_easy[x][y].getIcon(), bomb) ||
                                compareImageIcons((ImageIcon)label_easy[x][y].getIcon(), bomb) && compareImageIcons((ImageIcon) buttons_easy[x][y].getIcon(), bombOld)) {
                                    if (color.isSelected()) {
                                        faceButton.setIcon(death);
                                    } else {
                                        faceButton.setIcon(deathOld);
                                    }
                                } else {
                                if (color.isSelected()) {
                                    faceButton.setIcon(smiley);
                                    } else {
                                        faceButton.setIcon(smileyOld);
                                    }
                            }
                        }
                    }
                }
            }
        }
    };
    
    ItemListener radioListener = new ItemListener() {
        /**
         * Handles the item state change event.
         *
         * @param  e  the ItemEvent object representing the state change
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Quando un radio button viene selezionato, deseleziona gli altri
                if (e.getSource() == beginner) {
                    intermediate.setSelected(false);
                    expert.setSelected(false);
                    custom.setSelected(false);
                } else if (e.getSource() == intermediate) {
                    beginner.setSelected(false);
                    expert.setSelected(false);
                    custom.setSelected(false);
                    field_Intermediate = new Field_Intermediate();
                    setVisible(false);
                    frame.setVisible(false);
                    field_Intermediate.setVisible(true);
                    field_Intermediate.frame.setVisible(false);
                } else if (e.getSource() == expert) {
                    beginner.setSelected(false);
                    intermediate.setSelected(false);
                    custom.setSelected(false);
                    field_Extreme = new Field_Extreme();
                    setVisible(false);
                    frame.setVisible(false);
                    field_Extreme.setVisible(true);
                    field_Extreme.frame.setVisible(false);
                } else if (e.getSource() == custom) {
                    JOptionPane.showMessageDialog(null, "Coming Soon in the next version!!", "Coming Soon!", JOptionPane.INFORMATION_MESSAGE);
                    custom.setSelected(false);
                }
                if (e.getSource() == color) {
                    flags.setForeground(Color.RED);
                    flags.setIcon(flagsCounterIcon);
                    
                    timer.setForeground(Color.RED);
                    timer.setIcon(timerIcon);
                    resetGame();
                }
            }
            if (e.getStateChange() == ItemEvent.DESELECTED)  {
                if (e.getSource() == color) {
                    flags.setForeground(Color.WHITE);
                    flags.setIcon(flagsCounterIconOld);
                    timer.setForeground(Color.WHITE);
                    timer.setIcon(timerIconOld);
                    faceButton.setIcon(smileyOld);
                    resetGame();
                }
                if (e.getSource() == beginner) {
                    beginner.setSelected(true);
                }
            }
        }
    };
    
    private int rightClickCount = 0;
    private int seconds = 0;
    private int number_flag;
    private boolean debug = false;

    private boolean[][] revealed;
    private int rows = 9;
    private int cols = 9;
    private int bombNum = 10;
    private String sec = "999";
    private String name = "Anonymous";

    private ImageIcon iconGame = new ImageIcon("MineSweeper-2.11/src/resources/mine.png");
    private ImageIcon smiley = new ImageIcon("MineSweeper-2.11/src/resources/smiley.png");
    private ImageIcon shocked = new ImageIcon("MineSweeper-2.11/src/resources/shocked.png");
    private ImageIcon death = new ImageIcon("MineSweeper-2.11/src/resources/dead.png");
    private ImageIcon cool = new ImageIcon("MineSweeper-2.11/src/resources/cool.png");
    private ImageIcon smileyOld = new ImageIcon("MineSweeper-2.11/src/resources/smiley_old.png");
    private ImageIcon shockedOld = new ImageIcon("MineSweeper-2.11/src/resources/shocked_old.png");
    private ImageIcon deathOld = new ImageIcon("MineSweeper-2.11/src/resources/dead_old.png");
    private ImageIcon flagsCounterIcon = new ImageIcon("MineSweeper-2.11/src/resources/flag_counter.png");
    private ImageIcon flagsCounterIconOld = new ImageIcon("MineSweeper-2.11/src/resources/flag_counter_old.png");
    private ImageIcon timerIcon = new ImageIcon("MineSweeper-2.11/src/resources/timer.png");
    private ImageIcon timerIconOld = new ImageIcon("MineSweeper-2.11/src/resources/timer_old.png");
    private ImageIcon bomb = new ImageIcon("MineSweeper-2.11/src/resources/bomb.png");
    private ImageIcon bombOld = new ImageIcon("MineSweeper-2.11/src/resources/bomb_old.png");
    private ImageIcon flag = new ImageIcon("MineSweeper-2.11/src/resources/flag.png");
    private ImageIcon flagOld = new ImageIcon("MineSweeper-2.11/src/resources/flag_old.png");
    private ImageIcon question = new ImageIcon("MineSweeper-2.11/src/resources/mark.png");
    
    private JMenuBar menubar = new JMenuBar();
    private JMenu game = new JMenu("Game");
    private JMenu help = new JMenu("Help");
    
    private JSeparator[] separators = new JSeparator[5];
    
    private JMenuItem newGame = new JMenuItem("New                 F2");
    private JRadioButtonMenuItem beginner = new JRadioButtonMenuItem("Beginner");
    private JRadioButtonMenuItem intermediate = new JRadioButtonMenuItem("Intermediate");
    private JRadioButtonMenuItem expert = new JRadioButtonMenuItem("Expert");
    private JRadioButtonMenuItem custom = new JRadioButtonMenuItem("Custom...");
    private JCheckBoxMenuItem marks = new JCheckBoxMenuItem("Marks (?)");
    private JCheckBoxMenuItem color = new JCheckBoxMenuItem("Color");
    private JCheckBoxMenuItem sound = new JCheckBoxMenuItem("Sound");
    private JMenuItem times = new JMenuItem("Best Times...");
    private JMenuItem exit = new JMenuItem("Exit");
    
    private JMenuItem content = new JMenuItem("Contents                 F1");
    private JMenuItem ourPage = new JMenuItem("Our Page");
    private JMenuItem about = new JMenuItem("About Minesweeper");
    
    private JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
    private JLabel flags = new JLabel();
    private int flags_num = 10;
    protected JFrame frame;
    private JFrame fastestTime = new JFrame("Fastest Mine Sweeper");
    private JLabel timeLabel = new JLabel();
    private JFrame winTime = new JFrame();
    private JLabel winName = new JLabel();
    private JTextField nameField = new JTextField();
    private JButton confirmButton = new JButton("OK");
    private JButton resetButton;
    private JButton okButton;
    private JPanel buttonPanel;
    private JLabel timer = new JLabel();
    private JPanel panelTop = new JPanel();
    private JPanel panelDown = new JPanel();
    private JButton faceButton = new JButton();
    private JButton[][] buttons_easy = new JButton[rows][rows];
    private JLabel[][] label_easy = new JLabel[rows][rows];
    private boolean gameover = false;
    private ArrayList <Point> RandBomb = new ArrayList<>();
    private Timer _timer_;
    
    private final int WIDTH = 205; private final int HEIGHT = 315;

    public Field_Easy() {

        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(iconGame.getImage());
        this.setLocation(500, 200);
        this.setResizable(false);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            /**
             * Dispatches a key event.
             *
             * @param  e  the key event to be dispatched
             * @return    true if the event is consumed, false otherwise
             */
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_F2) {
                        resetGame();
                        return true; // Consuma l'evento
                    }
                    if (e.getKeyCode() == KeyEvent.VK_F1) {
                        openWebPage("https://minesweeper.online/help/patterns");
                        return true; // Consuma l'evento
                    }
                }
                return false; // Non consumare l'evento
            }
        });

        for(int i=0; i<separators.length; i++) {
            separators[i] = new JSeparator();
        }

        revealed = new boolean[rows][cols];

        fastestTime.setSize(315, 205);
        fastestTime.setResizable(false);
        fastestTime.setLayout(new BorderLayout());
        timeLabel.setFont(new Font("Arial", Font.BOLD, 13));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        resetButton = new JButton("Reset Scores");
        resetButton.addActionListener(this);
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(okButton);
        fastestTime.add(timeLabel, BorderLayout.CENTER);
        fastestTime.add(buttonPanel,  BorderLayout.SOUTH);
        fastestTime.setLocation(this.getX(), this.getY()+80);

        winTime.setLocation(this.getX(), this.getY()+80);
        winTime.setUndecorated(true);
        winTime.setLayout(new BorderLayout());
        winTime.setSize(420, 420);
        winName.setText("<html><div style='text-align: center;'>You have the fastest time<br>for beginner level.<br>Please enter your name.</div></html>");
        winName.setHorizontalAlignment(JLabel.CENTER);
        nameField.setPreferredSize(new Dimension(200, 40));
        winTime.add(winName, BorderLayout.NORTH);
        winTime.add(nameField, BorderLayout.CENTER);
        winTime.add(confirmButton, BorderLayout.SOUTH);
        confirmButton.addActionListener(this);
        winTime.pack();
        winTime.setVisible(false);
        
        beginner.addItemListener(radioListener);
        intermediate.addItemListener(radioListener);
        expert.addItemListener(radioListener);
        custom.addItemListener(radioListener);
        
        beginner.setSelected(true);
        marks.setSelected(true);
        color.setSelected(true);

        color.addItemListener(radioListener);
        sound.addItemListener(radioListener);
        exit.addActionListener(this);
        times.addActionListener(this);

        menubar.add(game);
        menubar.add(help);

        newGame.addActionListener(this);
        ourPage.addActionListener(this);
        about.addActionListener(this);
        content.addActionListener(this);

        game.add(newGame);
        game.add(separators[0]);
        game.add(beginner);
        game.add(intermediate);
        game.add(expert);
        game.add(custom);
        game.add(separators[1]);
        game.add(marks);
        game.add(color);
        game.add(sound);
        game.add(separators[2]);
        game.add(times);
        game.add(separators[3]);
        game.add(exit);

        help.add(content);
        help.add(ourPage);
        help.add(separators[4]);
        help.add(about);

        game.setMnemonic(KeyEvent.VK_G);
        help.setMnemonic(KeyEvent.VK_H);

        this.setJMenuBar(menubar);

        mainPanel.setBounds(0, 0, 205, 235);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        panelTop.setBorder(BorderFactory.createLoweredBevelBorder());
        panelTop.setBackground(Color.LIGHT_GRAY);
        panelTop.setPreferredSize(new Dimension(40, 40));
        mainPanel.add(panelTop, BorderLayout.NORTH);

        panelDown.setPreferredSize(new Dimension(40, 200));
        panelDown.setBackground(Color.LIGHT_GRAY);
        panelDown.setLayout(new GridLayout(rows, cols));
        panelDown.setBorder(BorderFactory.createLoweredBevelBorder());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                buttons_easy[i][j] = new JButton();
                buttons_easy[i][j].setBackground(Color.LIGHT_GRAY);
                buttons_easy[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                buttons_easy[i][j].setIcon(switchBomb(0));
                buttons_easy[i][j].addActionListener(this);
                buttons_easy[i][j].addMouseListener(ms);
                buttons_easy[i][j].setFocusPainted(true);
                panelDown.add(buttons_easy[i][j]);
                label_easy[i][j] = new JLabel();
            }
        }

        Random random = new Random();
        Point point = new Point(0, 0);

        for (int r = 1; r <= bombNum; r++) {
            int row, col;
            do {
                row = random.nextInt(0, 9);
                col = random.nextInt(0, 9);
                point = new Point(row, col);
            } while (RandBomb.contains(point));

            RandBomb.add(point);
            label_easy[row][col].setIcon(bomb);
        }

        label_easy = calculateNumericValues(label_easy);

        frame = new JFrame();
        frame.setVisible(debug);
        frame.setLayout(new GridLayout(9, 9));
        frame.setSize(420, 420);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                frame.add(label_easy[i][j]);
            }
        }

        mainPanel.add(panelDown, BorderLayout.SOUTH);

        panelTop.setLayout(null);

        faceButton.setBorder(BorderFactory.createRaisedBevelBorder());
        if (color.isSelected()) {
            faceButton.setIcon(smiley);
        } else {
            faceButton.setIcon(smileyOld);
        }
        faceButton.setBackground(Color.LIGHT_GRAY);
        faceButton.setBounds(78, 5, 30, 30);

        panelTop.add(faceButton);

        flags.setText("0"+flags_num);
        flags.setIcon(flagsCounterIcon);
        flags.setIconTextGap(-53);
        flags.setHorizontalAlignment(JLabel.CENTER);
        flags.setVerticalAlignment(JLabel.CENTER);
        flags.setForeground(Color.RED);
        repaint();
        flags.setFont(new Font("Digital-7 Mono", Font.PLAIN, 32));
        flags.setBounds(8, 5, 57, 28);

        panelTop.add(flags);

        timer.setText("000");
        timer.setIcon(timerIcon);
        timer.setIconTextGap(-53);
        timer.setHorizontalAlignment(JLabel.CENTER);
        timer.setVerticalAlignment(JLabel.CENTER);
        timer.setForeground(Color.RED);
        timer.setFont(new Font("Digital-7 Mono", Font.PLAIN, 32));
        timer.setBounds(121, 5, 57, 28);

        panelTop.add(timer);

        faceButton.addMouseListener(ms);
        faceButton.addActionListener(this);

        _timer_ = new Timer(1000, new ActionListener() {

            /**
             * A description of the entire Java function.
             *
             * @param e The ActionEvent object that triggered the actionPerformed event.
             * @return  void
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Codice da eseguire ogni secondo
                if(seconds < 999) {
                    seconds++;
                    updateTimerLabel();
                } else {
                    if(!gameover) {
                        JOptionPane.showMessageDialog(rootPane, "Timer Limit Exceeded", "Limit Exceeded", JOptionPane.WARNING_MESSAGE);
                    }
                    gameOver();
                    if(color.isSelected()) {
                        faceButton.setIcon(death);
                    } else {
                        faceButton.setIcon(deathOld);
                    }
                }
                if(sound.isSelected()){
                    playSound("MineSweeper-2.11/src/resources/beep-07a.wav");
                }
            }
        });

        this.add(mainPanel);
        this.setVisible(true);
    }

    /**
     * Updates the timer label based on the value of the seconds variable.
     *
     */
    private void updateTimerLabel() {
        if (seconds < 10) {
            timer.setText("00" + seconds);
        } else if (seconds < 100) {
            timer.setText("0" + seconds);
        } else {
            timer.setText("" + seconds);
        }
    }

    /**
     * Calculates the numeric values for each cell in the labelMatrix.
     *
     * @param  labelMatrix   A 2D array of JLabels representing the game board.
     * @return               The updated labelMatrix with the numeric values.
     */
    private JLabel[][] calculateNumericValues(JLabel[][] labelMatrix) {
        int rows = 9;
        int cols = 9;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (labelMatrix[i][j].getIcon() == bomb) {
                    continue;
                } else {
                    ImageIcon bombCount = countBombs(labelMatrix, i, j);
                    labelMatrix[i][j].setIcon(bombCount);
                }
            }
        }

        return labelMatrix;
    }

    /**
     * Calculates the number of bombs surrounding a given cell in the label matrix.
     *
     * @param  labelMatrix  the 2D array of JLabel representing the game board
     * @param  row          the row index of the cell
     * @param  col          the column index of the cell
     * @return              the ImageIcon representing the number of bombs surrounding the cell
     */
    private ImageIcon countBombs(JLabel[][] labelMatrix, int row, int col) {
        int bombCount = 0;
        int rows = labelMatrix.length;
        int cols = labelMatrix[0].length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (labelMatrix[newRow][newCol].getIcon() == bomb) {
                        bombCount++;
                    }
                }
            }
        }

        ImageIcon N_Bomb;

        N_Bomb = switchBomb(bombCount);

        return N_Bomb;
    }

    /**
     * Switches the bomb image based on the given cont value.
     *
     * @param  cont    the value used to determine which bomb image to switch to
     * @return         the new ImageIcon representing the switched bomb image
     */
    private ImageIcon switchBomb(int cont) {
        String path;
        ImageIcon n = new ImageIcon();
            switch (cont) {
                case 1 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/1.png" : "MineSweeper-2.11/src/resources/1_old.png";
                case 2 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/2.png" : "MineSweeper-2.11/src/resources/2_old.png";
                case 3 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/3.png" : "MineSweeper-2.11/src/resources/3_old.png";
                case 4 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/4.png" : "MineSweeper-2.11/src/resources/4_old.png";
                case 5 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/5.png" : "MineSweeper-2.11/src/resources/5_old.png";
                case 6 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/6.png" : "MineSweeper-2.11/src/resources/6_old.png";
                case 7 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/7.png" : "MineSweeper-2.11/src/resources/7_old.png";
                case 8 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/8.png" : "MineSweeper-2.11/src/resources/8_old.png";
                case 0 -> path = (color.isSelected())? "MineSweeper-2.11/src/resources/null.png" : "MineSweeper-2.11/src/resources/null.png";
                default -> path = "";
            }
            n = new ImageIcon(path);
            return n;
        } 


    
     /**
      * Compares two ImageIcons based on their descriptions.
      *
      * @param  icon1  the first ImageIcon to compare
      * @param  icon2  the second ImageIcon to compare
      * @return        true if the descriptions of the ImageIcons are equal, false otherwise
      */
     boolean compareImageIcons(ImageIcon icon1, ImageIcon icon2) {
        if(icon1.getDescription().equals(icon2.getDescription())) {
            return true;
        } 
        return false;
    }
    

    /**
     * Reveals a cell in the game grid.
     *
     * @param  row  the row index of the cell
     * @param  col  the column index of the cell
     */
    private void revealCell(int row, int col) {
        revealed[row][col] = true;
        ImageIcon B = new ImageIcon("MineSweeper-2.11/src/resources/bomb.png");

        if (compareImageIcons(B, (ImageIcon)label_easy[row][col].getIcon())) {
            buttons_easy[row][col].setIcon(label_easy[row][col].getIcon());
            buttons_easy[row][col].setBorder(null); // Bomba
            gameOver();
        } else {
            int bombCount = countAdjacentBombs(row, col);
            buttons_easy[row][col].setIcon(switchBomb(bombCount));
            buttons_easy[row][col].setBorder(BorderFactory.createLineBorder(new Color(93, 93, 93)));

            if (bombCount == 0) {
                revealAdjacentButtons(row, col);
            }
        }
    }

    /**
     * Changes the category information in the given file.
     *
     * @param  category   the category to modify
     * @param  newNumber  the new number for the category
     * @param  newName    the new name for the category
     */
    private void changeCategoryInfo(String category, String newNumber, String newName) {
        try {
            // Leggi il contenuto attuale del file
            File file = new File("MineSweeper-2.11/src/resources/file.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
    
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
    
            reader.close();
    
            // Modifica il contenuto del file in base alla categoria
            String currentContent = content.toString();
            String regex = "\\b" + Pattern.quote(category) + ": \\d+ seconds .+\\b";
            String newContent = currentContent.replaceAll(regex, category + ": " + newNumber + " seconds " + newName);
    
            // Scrivi il nuovo contenuto nel file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    /**
     * Reveals the adjacent buttons of a given button at the specified row and column.
     *
     * @param  row  the row index of the button
     * @param  col  the column index of the button
     */
    private void revealAdjacentButtons(int row, int col) {
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if (color.isSelected()) {
                    if(!compareImageIcons((ImageIcon)buttons_easy[i][j].getIcon(), flag)) {
                        if (!revealed[i][j]) {
                            revealCell(i, j);
                        }
                    }
                } else {
                    if(!compareImageIcons((ImageIcon)buttons_easy[i][j].getIcon(), flagOld)) {
                        if (!revealed[i][j]) {
                            revealCell(i, j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Counts the number of adjacent bombs to a given cell.
     *
     * @param  row  the row index of the cell
     * @param  col  the column index of the cell
     * @return      the number of adjacent bombs
     */
    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if (compareImageIcons(bomb, (ImageIcon)label_easy[i][j].getIcon())) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Plays a sound file located at the specified file path.
     *
     * @param  filePath  the path of the sound file to be played
     * @return           void
     */
    public static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * A description of the entire Java function.
     *
     */
    private void gameOver() {
        
        gameover = true;        
        // Puoi aggiungere ulteriori azioni da eseguire quando il gioco termina.
    }

    /**
     * Checks whether the player has won the game by checking the state of the buttons.
     *
     * @return  This function does not return any value.
     */
    private void checkWin() {
        int num_but = 0;
            for(int w=0; w < 9; w++) {
                for(int volkswagen = 0; volkswagen < 9; volkswagen++) {
                    if(buttons_easy[w][volkswagen].getBorder() == BorderFactory.createRaisedBevelBorder() ) {
                        num_but++;
                    }
                    if(buttons_easy[w][volkswagen].getBackground() == Color.RED) { 
                        return ;
                    }
                }
            }
            if(num_but == 10) {
                gameover = true;
                faceButton.setIcon(cool);
                _timer_.stop();
                if(Integer.valueOf(sec) > seconds) {
                    sec = seconds+"";
                    winTime.setVisible(true);
                }
            }

    }

    /**
     * Resets the game by initializing the necessary variables and components.
     *
     * @param  None  This function does not take any parameters.
     * @return       This function does not return any value.
     */
    private void resetGame() {
        gameover = false;
        revealed = new boolean[rows][cols];
        RandBomb.clear();

        // Reset buttons and labels
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                buttons_easy[i][j].setBackground(null);
                buttons_easy[i][j].setIcon(switchBomb(0));
                buttons_easy[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
            }
        }
        // Reset face icon and other components
        if (color.isSelected()) {
            faceButton.setIcon(smiley);
        } else {
            faceButton.setIcon(smileyOld);
        }
        flags.setText("010");
        timer.setText("000");

        // Regenerate bombs
        Random random = new Random();
        for (int i=0; i < 9; i++) {
            for(int j=0; j < 9; j++) {
                label_easy[i][j].setIcon(switchBomb(0));
            }
        }
        for (int r = 1; r <= 10; r++) {
            int row, col;
            Point point;
            do {
                row = random.nextInt(0, 9);
                col = random.nextInt(0, 9);
                point = new Point(row, col);
            } while (RandBomb.contains(point));

            RandBomb.add(new Point(row, col));
            label_easy[row][col].setIcon(bomb);
        }

        label_easy = calculateNumericValues(label_easy);
        seconds = 0;
        _timer_.stop();
    }

    /**
     * Reads a file and sets the text of a JLabel with the contents of the file.
     *
     * @param  None
     * @return None
     */
    private void readFile() {
        try {
            File myObj = new File("MineSweeper-2.11/src/resources/file.txt");
            Scanner myReader = new Scanner(myObj);
            StringBuilder timeString = new StringBuilder();
            timeString.append("<html>");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                timeString.append(data).append("<br><br>");
            }
    
            myReader.close();
    
            // Imposta il testo del JLabel
            timeLabel.setText(timeString.toString()+"</html>");
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a web page with the given URL.
     *
     * @param  url	the URL of the web page to open
     */
    public static void openWebPage(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = new URI(url);
                desktop.browse(uri);
            } else {
                System.out.println("Il desktop non supporta l'apertura di pagine web.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action performed when an event is triggered.
     *
     * @param  a  the event that was triggered
     */
    @Override
    public void actionPerformed(ActionEvent a) {
        if(a.getSource() == content) {
            openWebPage("https://minesweeper.online/help/patterns");
        }
        if(a.getSource() == ourPage) {
            JOptionPane.showMessageDialog(null, "Coming Soon in the next version!!", "Coming Soon!", JOptionPane.INFORMATION_MESSAGE);
        }
        if(a.getSource() == about) {
            openWebPage("https://it.wikipedia.org/wiki/Campo_minato_(videogioco)");
        }
        if (a.getSource() == exit) {
            System.exit(0);
        }
        if (a.getSource() == times) {
            readFile();
            boolean state = true;
            fastestTime.setLocation(this.getX(), this.getY()+80);
            fastestTime.setVisible(state);
            state = !state;
        }
        if (a.getSource() == resetButton) {
            changeCategoryInfo("Beginner", "999", "Anonymous");
            sec = "999";
            changeCategoryInfo("Intermediate", "999", "Anonymous");
            changeCategoryInfo("Expert", "999", "Anonymous");
            readFile();
            boolean state = true;
            fastestTime.setLocation(this.getX(), this.getY()+80);
            fastestTime.setVisible(state);
            state = !state;
            fastestTime.setVisible(true);
        }
        if (a.getSource() == okButton) {
            fastestTime.setVisible(false);
        }
        if(a.getSource() == confirmButton) {
            if(nameField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter your name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(nameField.getText().length() > 10) {
                JOptionPane.showMessageDialog(null, "Name too long!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(nameField.getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "Name too short!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if(!nameField.getText().matches("[a-zA-Z1-9]+")) {
                JOptionPane.showMessageDialog(null, "Name can only contain letters!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            name = nameField.getText();
            winTime.setVisible(false);
            changeCategoryInfo("Beginner", sec, name);
            readFile();
            nameField.setText("");
            boolean state = true;
            fastestTime.setLocation(this.getX(), this.getY()+80);
            fastestTime.setVisible(state);
            state = !state;
            fastestTime.setVisible(true);
        }
        if(!gameover) {
            int cont = 0;
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (a.getSource() == buttons_easy[x][y]) {
                        if (color.isSelected()) {
                            if (!compareImageIcons((ImageIcon)buttons_easy[x][y].getIcon(), flag)) {
                                if (cont == 0) {
                                    _timer_.start();
                                    cont++;
                                }
                                if (!revealed[x][y]) {
                                    buttons_easy[x][y].setBorder(null);
                                    buttons_easy[x][y].setIcon(label_easy[x][y].getIcon());
                                    revealCell(x, y);  // Add the reveal logic
                                }
                                if (compareImageIcons((ImageIcon)label_easy[x][y].getIcon(), bomb) || 
                                    compareImageIcons((ImageIcon)label_easy[x][y].getIcon(), bombOld)) {
                                    if(sound.isSelected()) {
                                        playSound("MineSweeper-2.11/src/resources/explosion.wav");
                                    }
                                    if (color.isSelected()) {
                                        buttons_easy[x][y].setBackground(Color.RED);
                                    } else {
                                        buttons_easy[x][y].setBackground(Color.BLACK);
                                    }
                                    for(int i=0; i < 9; i++) {
                                        for(int j=0; j < 9; j++) {
                                            if (compareImageIcons((ImageIcon)label_easy[i][j].getIcon(), bomb)) {
                                                buttons_easy[i][j].setIcon(bomb);
                                                buttons_easy[i][j].setBorder(null);
                                            }
                                        }
                                    }
                                    if (color.isSelected()) {
                                        buttons_easy[x][y].setIcon(bomb);
                                    } else {
                                        buttons_easy[x][y].setIcon(bombOld);
                                    }
                                    _timer_.stop();
                                    if (color.isSelected()) {
                                        faceButton.setIcon(death);
                                    } else {
                                        faceButton.setIcon(deathOld);
                                    }
                                }
                            }
                        } else {
                            if (!compareImageIcons((ImageIcon)buttons_easy[x][y].getIcon(), flagOld)) {
                                if (cont == 0) {
                                    _timer_.start();
                                    cont++;
                                }
                                if (!revealed[x][y]) {
                                    buttons_easy[x][y].setBorder(null);
                                    buttons_easy[x][y].setIcon(label_easy[x][y].getIcon());
                                    revealCell(x, y);  // Add the reveal logic
                                }
                                if (compareImageIcons((ImageIcon)label_easy[x][y].getIcon(), bomb) || 
                                    compareImageIcons((ImageIcon)label_easy[x][y].getIcon(), bombOld)) {
                                    if (color.isSelected()) {
                                        buttons_easy[x][y].setBackground(Color.RED);
                                    } else {
                                        buttons_easy[x][y].setBackground(Color.BLACK);
                                    }
                                    for(int i=0; i < 9; i++) {
                                        for(int j=0; j < 9; j++) {
                                            if (compareImageIcons((ImageIcon)label_easy[i][j].getIcon(), bomb)) {
                                                buttons_easy[i][j].setIcon(bomb);
                                                buttons_easy[i][j].setBorder(null);
                                            }
                                        }
                                    }
                                    if (color.isSelected()) {
                                        buttons_easy[x][y].setIcon(bomb);
                                    } else {
                                        buttons_easy[x][y].setIcon(bombOld);
                                    }
                                    _timer_.stop();
                                    if (color.isSelected()) {
                                        faceButton.setIcon(death);
                                    } else {
                                        faceButton.setIcon(deathOld);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            checkWin();
        }
        if (a.getSource() == faceButton || a.getSource() == newGame) {
            resetGame();
        }
    }
}