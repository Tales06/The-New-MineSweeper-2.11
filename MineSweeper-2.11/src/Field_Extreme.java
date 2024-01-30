import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * 
 *@author Jake & Tales
 */
public class Field_Extreme extends JFrame implements ActionListener {

    Field_Easy field_Easy; Field_Intermediate field_Intermediate;
    
    private int rightClickCount = 0;
    private int seconds = 0;
    private int number_flag = 0;
    private boolean debug = false;
    
    MouseListener ms = new MouseAdapter() {
        
        /**
         * MousePressed event handler.
         *
         * @param  e  the MouseEvent object representing the event
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if(!gameover) {
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < cols; y++) {
                        if (e.getSource() == buttons_extreme[x][y]) {
                            if (color.isSelected()) {
                                faceButton.setIcon(shocked);
                            } else {
                                faceButton.setIcon(shockedOld);
                            }
                            if (SwingUtilities.isRightMouseButton(e)) {
                                if (marks.isSelected()) {
                                    if(compareImageIcons((ImageIcon)buttons_extreme[x][y].getIcon(), flag) ||
                                        compareImageIcons((ImageIcon)buttons_extreme[x][y].getIcon(), flagOld)) {
                                        rightClickCount = 1;
                                    } else if (compareImageIcons((ImageIcon)buttons_extreme[x][y].getIcon(), question)) {
                                        rightClickCount = 2;
                                    } else {
                                        rightClickCount = 0;
                                    }
                                    if (buttons_extreme[x][y].getBorder() == BorderFactory.createRaisedBevelBorder()) {

                                        switch (rightClickCount) {
                                            case 0:
                                            if(number_flag < flags_num) {
                                                if(color.isSelected()) {
                                                    buttons_extreme[x][y].setIcon(flag);
                                                } else {
                                                    buttons_extreme[x][y].setIcon(flagOld);
                                                }
                                            }
                                            break;
                                            case 1:
                                            buttons_extreme[x][y].setIcon(question);
                                            break;
                                            case 2:
                                            buttons_extreme[x][y].setIcon(switchBomb(0));
                                            break;
                                        }  
                                    }
                                } else {
                                    if(compareImageIcons((ImageIcon)buttons_extreme[x][y].getIcon(), flag) ||
                                        compareImageIcons((ImageIcon)buttons_extreme[x][y].getIcon(), flagOld)) {
                                        rightClickCount = 1;
                                    } else {
                                        rightClickCount = 0;
                                    }
                                    if (color.isSelected()) {
                                        faceButton.setIcon(shocked);
                                    } else {
                                        faceButton.setIcon(shockedOld);
                                    }
                                    if (buttons_extreme[x][y].getBorder() == BorderFactory.createRaisedBevelBorder()) {
                                        
                                        switch (rightClickCount) {
                                            case 0:
                                                if(color.isSelected()){
                                                    buttons_extreme[x][y].setIcon(flag);
                                                } else {
                                                    buttons_extreme[x][y].setIcon(flagOld);
                                                }
                                            break;
                                            case 1:
                                            buttons_extreme[x][y].setIcon(switchBomb(0));
                                            break;
                                        }  
                                    }
                                }
                                buttons_extreme[x][y].setFocusable(false);
                            }
                            number_flag = 0;
                            for(int i=0; i<rows; i++) {
                                for(int j=0; j<cols; j++) {
                                    if (compareImageIcons((ImageIcon)buttons_extreme[i][j].getIcon(), flag)  || 
                                        compareImageIcons((ImageIcon)buttons_extreme[i][j].getIcon(), flagOld)) {
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
                            flags_num = 99;
                        }
                    }
                }
            }
        }


        /**
         * Handles the mouseReleased event.
         *
         * @param  e     the MouseEvent object representing the event
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!gameover) {
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < cols; y++) {
                        if (e.getSource() == buttons_extreme[x][y]) {
                            if (compareImageIcons((ImageIcon)label_extreme[x][y].getIcon(), bomb) && compareImageIcons((ImageIcon) buttons_extreme[x][y].getIcon(), bomb) ||
                                compareImageIcons((ImageIcon)label_extreme[x][y].getIcon(), bomb) && compareImageIcons((ImageIcon) buttons_extreme[x][y].getIcon(), bombOld)) {
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
         * A description of the entire Java function.
         *
         * @param  e	an ItemEvent object that represents the event
         * @return   	void
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Quando un radio button viene selezionato, deseleziona gli altri
                if (e.getSource() == beginner) {
                    intermediate.setSelected(false);
                    expert.setSelected(false);
                    custom.setSelected(false);
                    field_Easy = new Field_Easy();
                    setVisible(false);
                    frame.setVisible(false);
                    field_Easy.setVisible(true);
                    field_Easy.frame.setVisible(false);
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
                if (e.getSource() == expert) {
                    expert.setSelected(true);
                }
            }
        }
    };
    
    private ImageIcon iconGame = new ImageIcon("src/resources/mine.png");
    private ImageIcon smiley = new ImageIcon("src/resources/smiley.png");
    private ImageIcon shocked = new ImageIcon("src/resources/shocked.png");
    private ImageIcon death = new ImageIcon("src/resources/dead.png");
    private ImageIcon cool = new ImageIcon("src/resources/cool.png");
    private ImageIcon smileyOld = new ImageIcon("src/resources/smiley_old.png");
    private ImageIcon shockedOld = new ImageIcon("src/resources/shocked_old.png");
    private ImageIcon deathOld = new ImageIcon("src/resources/dead_old.png");
    private ImageIcon flagsCounterIcon = new ImageIcon("src/resources/flag_counter.png");
    private ImageIcon flagsCounterIconOld = new ImageIcon("src/resources/flag_counter_old.png");
    private ImageIcon timerIcon = new ImageIcon("src/resources/timer.png");
    private ImageIcon timerIconOld = new ImageIcon("src/resources/timer_old.png");
    private ImageIcon bomb = new ImageIcon("src/resources/bomb.png");
    private ImageIcon bombOld = new ImageIcon("src/resources/bomb_old.png");
    private ImageIcon flag = new ImageIcon("src/resources/flag.png");
    private ImageIcon flagOld = new ImageIcon("src/resources/flag_old.png");
    private ImageIcon question = new ImageIcon("src/resources/mark.png");
    
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
    private int flags_num = 99;
    private JFrame fastestTime = new JFrame("Fastest Mine Sweeper");
    private JLabel timeLabel = new JLabel();
    private JFrame winTime = new JFrame();
    protected JFrame frame;
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
    private int filled = 16;
    private int limits = 30;
    private JButton[][] buttons_extreme = new JButton[filled][limits];
    private JLabel[][] label_extreme = new JLabel[filled][limits];
    private boolean gameover = false;
    private ArrayList <Point> RandBomb = new ArrayList<>();
    private Timer _timer_;

    private boolean[][] revealed;
    private int rows = 16;
    private int cols = 30;
    private int bombNum = 99;
    private String sec = "999";
    private String name = "Anonymous";
    
    private final int WIDTH = 623; private final int HEIGHT = 454;

    public Field_Extreme() {

        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(iconGame.getImage());
        this.setLocation(500, 200);
        this.setResizable(false);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            /**
             * Dispatches key events and performs specific actions based on the event.
             *
             * @param  e  the key event to dispatch
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
        winName.setText("<html><div style='text-align: center;'>You have the fastest time<br>for extreme level.<br>Please enter your name.</div></html>");
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
        
        expert.setSelected(true);
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

        panelDown.setPreferredSize(new Dimension(40, 340));
        panelDown.setBackground(Color.LIGHT_GRAY);
        panelDown.setLayout(new GridLayout(rows, cols));
        panelDown.setBorder(BorderFactory.createLoweredBevelBorder());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons_extreme[i][j] = new JButton();
                buttons_extreme[i][j].setBackground(Color.LIGHT_GRAY);
                buttons_extreme[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                buttons_extreme[i][j].setIcon(switchBomb(0));
                buttons_extreme[i][j].addActionListener(this);
                buttons_extreme[i][j].addMouseListener(ms);
                buttons_extreme[i][j].setFocusPainted(true);
                panelDown.add(buttons_extreme[i][j]);
                label_extreme[i][j] = new JLabel();
            }
        }

        Random random = new Random();
        Point point = new Point(0, 0);

        for (int r = 1; r <= bombNum; r++) {
            int row, col;
            do {
                row = random.nextInt(0, rows);
                col = random.nextInt(0, cols);
                point = new Point(row, col);
            } while (RandBomb.contains(point));

            RandBomb.add(point);
            label_extreme[row][col].setIcon(bomb);
        }

        label_extreme = calculateNumericValues(label_extreme);

        frame  = new JFrame();
        frame.setVisible(debug);
        frame.setLayout(new GridLayout(rows, cols));
        frame.setSize(WIDTH, HEIGHT);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frame.add(label_extreme[i][j]);
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
        faceButton.setBounds(285, 5, 30, 30);

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
        timer.setBounds(540, 5, 57, 28);

        panelTop.add(timer);

        faceButton.addMouseListener(ms);
        faceButton.addActionListener(this);

        _timer_ = new Timer(1000, new ActionListener() {

            /**
             * A description of the entire Java function.
             *
             * @param  e	Event object representing the action event
             * @return  	void
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
                    playSound("src/resources/beep-07a.wav");
                }
            }
        });

        this.add(mainPanel);
        this.setVisible(false);
    }

    /**
     * Updates the label of the timer based on the current value of the 'seconds' variable.
     *
     * @param  None N/A
     * @return None N/A
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
     * Calculates the numeric values for the given label matrix.
     *
     * @param  labelMatrix  the matrix of JLabels to calculate the numeric values for
     * @return              the updated label matrix with the numeric values calculated
     */
    private JLabel[][] calculateNumericValues(JLabel[][] labelMatrix) {
        int rows = 16;
        int cols = 30;

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
     * Counts the number of bombs surrounding a given position in a label matrix.
     *
     * @param  labelMatrix  the matrix of labels representing the game board
     * @param  row          the row index of the position
     * @param  col          the column index of the position
     * @return              the ImageIcon representing the number of bombs
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
     * Switches the bomb image based on the given count.
     *
     * @param  cont  the count used to determine which bomb image to switch to
     * @return       the ImageIcon object representing the new bomb image
     */
    private ImageIcon switchBomb(int cont) {
        String path;
        ImageIcon n = new ImageIcon();
            switch (cont) {
                case 1 -> path = (color.isSelected())? "src/resources/1.png" : "src/resources/1_old.png";
                case 2 -> path = (color.isSelected())? "src/resources/2.png" : "src/resources/2_old.png";
                case 3 -> path = (color.isSelected())? "src/resources/3.png" : "src/resources/3_old.png";
                case 4 -> path = (color.isSelected())? "src/resources/4.png" : "src/resources/4_old.png";
                case 5 -> path = (color.isSelected())? "src/resources/5.png" : "src/resources/5_old.png";
                case 6 -> path = (color.isSelected())? "src/resources/6.png" : "src/resources/6_old.png";
                case 7 -> path = (color.isSelected())? "src/resources/7.png" : "src/resources/7_old.png";
                case 8 -> path = (color.isSelected())? "src/resources/8.png" : "src/resources/8_old.png";
                case 0 -> path = (color.isSelected())? "src/resources/null.png" : "src/resources/null_old.png";
                default -> path = "";
            }
            n = new ImageIcon(path);
            return n;
        } 


    
     /**
      * Compares two ImageIcons and returns a boolean value indicating whether they are equal.
      *
      * @param  icon1  the first ImageIcon to compare
      * @param  icon2  the second ImageIcon to compare
      * @return        true if the ImageIcons are equal, false otherwise
      */
     boolean compareImageIcons(ImageIcon icon1, ImageIcon icon2) {
        if(icon1.getDescription().equals(icon2.getDescription())) {
            return true;
        } 
        return false;
    }
    

    /**
     * Reveals a cell on the game board.
     *
     * @param  row  the row index of the cell
     * @param  col  the column index of the cell
     */
    private void revealCell(int row, int col) {

        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            // Skip if the indices are out of bounds
            return;
        }

        revealed[row][col] = true;
        ImageIcon B = new ImageIcon("src/resources/bomb.png");

        if (compareImageIcons(B, (ImageIcon)label_extreme[row][col].getIcon())) {
            buttons_extreme[row][col].setIcon(label_extreme[row][col].getIcon());
            buttons_extreme[row][col].setBorder(null); // Bomba
            gameOver();
        } else {
            int bombCount = countAdjacentBombs(row, col);
            buttons_extreme[row][col].setIcon(switchBomb(bombCount));
            buttons_extreme[row][col].setBorder(BorderFactory.createLineBorder(new Color(93, 93, 93)));

            if (bombCount == 0) {
                revealAdjacentButtons(row, col);
            }
        }
    }

    /**
     * Updates the information of a category in the file.
     *
     * @param  category   the category to be updated
     * @param  newNumber  the new number value
     * @param  newName    the new name value
     */
    private void changeCategoryInfo(String category, String newNumber, String newName) {
        try {
            // Leggi il contenuto attuale del file
            File file = new File("src/resources/file.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }

            reader.close();

            // Modifica il contenuto del file in base alla categoria
            String currentContent = content.toString();
            String newContent = currentContent.replaceAll("\\b" + category + ": \\d+ seconds \\w+\\b", category + ": " + newNumber + " seconds " + newName);

            // Scrivi il nuovo contenuto nel file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reveals the adjacent buttons to the specified row and column.
     *
     * @param  row  the row of the button
     * @param  col  the column of the button
     */
    private void revealAdjacentButtons(int row, int col) {
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols) {
                    if (color.isSelected()) {
                        if (!compareImageIcons((ImageIcon) buttons_extreme[i][j].getIcon(), flag)) {
                            if (!revealed[i][j]) {
                                revealCell(i, j);
                            }
                        }
                    } else {
                        if (!compareImageIcons((ImageIcon) buttons_extreme[i][j].getIcon(), flagOld)) {
                            if (!revealed[i][j]) {
                                revealCell(i, j);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates the number of adjacent bombs to a given cell.
     *
     * @param  row  the row index of the cell
     * @param  col  the column index of the cell
     * @return      the number of adjacent bombs
     */
    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                if (compareImageIcons(bomb, (ImageIcon)label_extreme[i][j].getIcon())) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Plays a sound from the given file path.
     *
     * @param  filePath  the path of the sound file to be played
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
     * This function is called when the game is over.
     *
     * @param  None   	This function does not take any parameters.
     * @return None    	This function does not return any value.
     */
    private void gameOver() {
        
        gameover = true;
        
        // Puoi aggiungere ulteriori azioni da eseguire quando il gioco termina.
    }

    /**
     * Checks if the player has won the game.
     *
     * @param  None
     * @return None
     */
    private void checkWin() {
        int num_but = 0;
        for(int w=0; w < rows; w++) {
            for(int volkswagen = 0; volkswagen < cols; volkswagen++) {
                if(buttons_extreme[w][volkswagen].getBorder() == BorderFactory.createRaisedBevelBorder()) {
                    num_but++;
                }
                if(buttons_extreme[w][volkswagen].getBackground() == Color.RED) { 
                    return ;
                }
            }
        }
        if(num_but == bombNum) {
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
     * Resets the game by setting gameover to false, initializing the 'revealed' array with the specified number of rows and columns, clearing the RandBomb list,
     * resetting the buttons and labels, regenerating the bombs, calculating numeric values for the labels, and stopping the timer.
     *
     * @param  None   No parameters are required.
     * @return        No return value.
     */
    private void resetGame() {
        gameover = false;
        revealed = new boolean[rows][cols];
        RandBomb.clear();

        // Reset buttons and labels
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons_extreme[i][j].setBackground(null);
                buttons_extreme[i][j].setIcon(switchBomb(0));
                buttons_extreme[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
            }
        }
        // Reset face icon and other components
        if (color.isSelected()) {
            faceButton.setIcon(smiley);
        } else {
            faceButton.setIcon(smileyOld);
        }
        flags.setText("099");
        timer.setText("000");

        // Regenerate bombs
        Random random = new Random();
        for (int i=0; i < rows; i++) {
            for(int j=0; j < cols; j++) {
                label_extreme[i][j].setIcon(switchBomb(0));
            }
        }
        for (int r = 1; r <= bombNum; r++) {
            int row, col;
            Point point;
            do {
                row = random.nextInt(0, rows);
                col = random.nextInt(0, cols);
                point = new Point(row, col);
            } while (RandBomb.contains(point));

            RandBomb.add(new Point(row, col));
            label_extreme[row][col].setIcon(bomb);
        }

        label_extreme = calculateNumericValues(label_extreme);
        seconds = 0;
        _timer_.stop();
    }

    /**
     * Reads a file and sets the text of a JLabel with the contents of the file.
     *
     * @param  paramName    None
     * @return              None
     */
    private void readFile() {
        try {
            File myObj = new File("src/resources/file.txt");
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
     * Opens the specified URL in the default web browser.
     *
     * @param  url   the URL to be opened
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
     * Handles the action events triggered by different components in the UI.
     *
     * @param  a  the ActionEvent object that represents the action event
     */
    @Override
    public void actionPerformed(ActionEvent a) {
        if(a.getSource() == content) {
            openWebPage("https://minesweeper.online/help/patterns");
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
            changeCategoryInfo("Intermediate", "999", "Anonymous");
            changeCategoryInfo("Expert", "999", "Anonymous");
            sec = "999";
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
            changeCategoryInfo("Expert", sec, name);
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
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    if (a.getSource() == buttons_extreme[x][y]) {
                        if (color.isSelected()) {
                            if (!compareImageIcons((ImageIcon)buttons_extreme[x][y].getIcon(), flag)) {
                                if (cont == 0) {
                                    _timer_.start();
                                    cont++;
                                }
                                if (!revealed[x][y]) {
                                    buttons_extreme[x][y].setBorder(null);
                                    buttons_extreme[x][y].setIcon(label_extreme[x][y].getIcon());
                                    revealCell(x, y);  // Add the reveal logic
                                }
                                if (compareImageIcons((ImageIcon)label_extreme[x][y].getIcon(), bomb) || 
                                    compareImageIcons((ImageIcon)label_extreme[x][y].getIcon(), bombOld)) {
                                    if(sound.isSelected()) {
                                        playSound("src/resources/explosion.wav");
                                    }
                                    if (color.isSelected()) {
                                        buttons_extreme[x][y].setBackground(Color.RED);
                                    } else {
                                        buttons_extreme[x][y].setBackground(Color.BLACK);
                                    }
                                    for(int i=0; i < rows; i++) {
                                        for(int j=0; j < cols; j++) {
                                            if (compareImageIcons((ImageIcon)label_extreme[i][j].getIcon(), bomb)) {
                                                buttons_extreme[i][j].setIcon(bomb);
                                                buttons_extreme[i][j].setBorder(null);
                                            }
                                        }
                                    }
                                    if (color.isSelected()) {
                                        buttons_extreme[x][y].setIcon(bomb);
                                    } else {
                                        buttons_extreme[x][y].setIcon(bombOld);
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
                            if (!compareImageIcons((ImageIcon)buttons_extreme[x][y].getIcon(), flagOld)) {
                                if (cont == 0) {
                                    _timer_.start();
                                    cont++;
                                }
                                if (!revealed[x][y]) {
                                    buttons_extreme[x][y].setBorder(null);
                                    buttons_extreme[x][y].setIcon(label_extreme[x][y].getIcon());
                                    revealCell(x, y);  // Add the reveal logic
                                }
                                if (compareImageIcons((ImageIcon)label_extreme[x][y].getIcon(), bomb) || 
                                    compareImageIcons((ImageIcon)label_extreme[x][y].getIcon(), bombOld)) {
                                    if (color.isSelected()) {
                                        buttons_extreme[x][y].setBackground(Color.RED);
                                    } else {
                                        buttons_extreme[x][y].setBackground(Color.BLACK);
                                    }
                                    for(int i=0; i < filled; i++) {
                                        for(int j=0; j < limits; j++) {
                                            if (compareImageIcons((ImageIcon)label_extreme[i][j].getIcon(), bomb)) {
                                                buttons_extreme[i][j].setIcon(bomb);
                                                buttons_extreme[i][j].setBorder(null);
                                            }
                                        }
                                    }
                                    if (color.isSelected()) {
                                        buttons_extreme[x][y].setIcon(bomb);
                                    } else {
                                        buttons_extreme[x][y].setIcon(bombOld);
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