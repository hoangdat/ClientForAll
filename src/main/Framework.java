package main;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import rmiscreensaver.CenterController;
import rmiscreensaver.CenterControllerImpl;
import rmiscreensaver.ContentMessage;
import rmiscreensaver.RegisterMessage;

/**
 * Framework that controls the game (Game.java) that created it, update it and draw it on the screen.
 * 
 * @author www.gametutorial.net
 */

public class Framework extends Canvas {
    
    private String id;
    private CenterControllerImpl client;
    private CenterController server;
    
    private boolean isAdmin;
    public static boolean isDoneConfig = false; 
    public static boolean isStarted = false;
    public static String nameBackground = "";
    public static String nameCharacter = "";
    public static int countCharacter;
    private int countClickInMenu;
    private int optionItemWidth;
    private int optionItemHeight;
    private int x_Item;
    private boolean isGoClicked = false;
    
    JTextField txtIP;
    JButton btnConnect;
    BufferedImage btnClose;
    

    
    private static final int countTest = 5;
    
    
    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    /**
     * Width of the frame.
     */
    public static int frameWidth;
    /**
     * Height of the frame.
     */
    public static int frameHeight;

    /**
     * Time of one second in nanoseconds.
     * 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;
    
    /**
     * Time of one millisecond in nanoseconds.
     * 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;
    
    /**
     * FPS - Frames per second
     * How many times per second the game should update?
     */
    private final int GAME_FPS = 60;
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    
    /**
     * Possible states of the game
     */
    public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, 
                                                    OPTIONS, PLAYING, GAMEOVER, DESTROYED}
    /**
     * Current state of the game
     */
    public static GameState gameState;
    
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    private long lastTime;
    
    // The actual game
    private Game game;
    
    
    public Framework ()
    {
        super();
        System.out.println("Vao ham Framework khoi tao");
        
        isAdmin          = false;
        isDoneConfig     = false;
        isStarted        = false;
        countClickInMenu = 0;
        
        //cho thuc hien dang ki tu toan nay luon
        
//        JTextField txtIP = new JTextField();
//        txtIP.setPreferredSize(new Dimension(this.getWidth() / 4, 30));
//        this.add(txtIP, BorderLayout.WEST);
//        GridLayout grid = new GridLayout(2, 1);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
//        JButton btnDisconnect = new JButton("Disconnect");
//        this.add(btnDisconnect);
        txtIP = new JTextField();
        Dimension txtSize = new Dimension(200, 20);
        txtIP.setMaximumSize(txtSize);
        txtIP.setMinimumSize(txtSize);
        txtIP.setPreferredSize(txtSize);
        //txtIP.setBounds(this.getWidth() / 2 - 35 , this.getHeight() / 2  - 25 , 20, 20);
        this.add(txtIP);
        
        btnConnect = new JButton("Connect");
        this.add(btnConnect);
        btnConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String strIP = txtIP.getText();
                if (strIP.isEmpty()) {
                    JOptionPane.showMessageDialog(btnConnect, "IP is empty! Please fill IP address.");
                } else {
                    try {
                        Registry myReg = LocateRegistry.getRegistry(strIP, 1099);
                        server = (CenterController) Naming.lookup("ScreenSaver_Service");
                        client = new CenterControllerImpl();
                    } catch (RemoteException ex) {
                        Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(btnConnect, "Your IP address is incorrected."
                                + "\nPlease try again!"
                                + "\nError: " + ex.getMessage());
                        
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(btnConnect, "Error:"
                                + "\n" + ex.getMessage());
                    }
                }
                startGamePH();
            }
        });
        
//        gameState = GameState.VISUALIZING;
//        
//        //We start game in new thread.
//        Thread gameThread = new Thread() {
//            @Override
//            public void run(){
//                GameLoop();
//            }
//        };
//        gameThread.start();
    }
    
    public void startGamePH(){
        this.removeAll();
        gameState = GameState.VISUALIZING;
        
        //We start game in new thread.
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();       
    }
    
    
   /**
     * Set variables and objects.
     * This method is intended to set the variables and objects for this class, 
     * variables and objects for the actual game can be set in Game.java.
     */
    private void Initialize()
    {

    }
    
    /**
     * Load files - images, sounds, ...
     * This method is intended to load files for this class, files for the actual 
     * game can be loaded in Game.java.
     */
    private void LoadContent()
    {
        try {
            URL btnCloseURL = getClass().getClassLoader()
                    .getResource("main/characterimg/close.png");
            btnClose = ImageIO.read(btnCloseURL);
        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated and then the game is drawn on the screen.
     */
    private void GameLoop()
    {
        // This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame();
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                    //...
                break;
                case MAIN_MENU:
                    if (!isAdmin) {
                        if (isDoneConfig) {
                            URL bgURL = getClass().getClassLoader().getResource(nameBackground);
                            URL chURL = getClass().getClassLoader().getResource(nameCharacter);
                            if (isStarted) {
                                newGame(bgURL, chURL, countCharacter);
                            }
                        }
                    }
                break;
                case OPTIONS:
                    //...
                break;
                case GAME_CONTENT_LOADING:
                    //...
                break;
                case STARTING:
                    // Sets variables and objects.
                    Initialize();
                    // Load files - images, sounds, ...
                    LoadContent();

                    // When all things that are called above finished, we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case VISUALIZING:
                    // On Ubuntu OS (when I tested on my old computer) this.getWidth() method doesn't return the correct value immediately (eg. for frame that should be 800px width, returns 0 than 790 and at last 798px). 
                    // So we wait one second for the window/frame to be set to its correct size. Just in case we
                    // also insert 'this.getWidth() > 1' condition in case when the window/frame size wasn't set in time,
                    // so that we although get approximately size.
                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
                    {
                        frameWidth = this.getWidth();
                        frameHeight = this.getHeight();
                        
                        try {
//                            client = new CenterControllerImpl();
//                            Registry myReg = LocateRegistry.getRegistry("127.0.0.1", 1099);
//                            server = (CenterController) Naming.lookup("ScreenSaver_Service");
                            
                            RegisterMessage regMes = new 
                                    RegisterMessage(frameWidth, frameHeight, client);
                            
                            RegisterMessage resultMes = new RegisterMessage();
                            int i = 0;
                            for (i = 0; i < 3; i++) {
                                resultMes = server.register(regMes);
                                if (resultMes != null) {
                                    break;
                                }
                            }
                            
                            System.out.println("=-===-=-=-=-=-=-==-=");
                            System.out.println(resultMes.getId());
                            
                            if (i == 2 && resultMes == null) {
                                throw new RuntimeException("Registed failed");
                            }
                            
                            if (resultMes.isIsAdmin()) {
                                
                                setIsAdmin(true);
                                setId(resultMes.getId());
                                gameState = GameState.STARTING;
                                
                            } else {
                                
                                setIsAdmin(false);
                                if (resultMes.getServerState() == 
                                        CenterControllerImpl.SESSION_CONFIGURING) {
                                    
                                    setId(resultMes.getId());
                                    gameState = GameState.STARTING;
                                    
                                } else {
                                    //System.out.println("Vao Truong hop giua chung");
                                    setId(resultMes.getId());
                                    //test ki truong hop nay
                                    ContentMessage content =  server.loadContent();
                                    //lap tuc tham gia vong lap game ngay
                                    URL bgURL = getClass().getClassLoader()
                                            .getResource(content.getBackgroundName());
                                    
                                    URL chURL = getClass().getClassLoader()
                                            .getResource(content.getCharacterName());
                                    
                                    int chCount = content.getCharacterCount();
                                    
                                    System.out.println(content.getBackgroundName());
                                    
                                    newGame(bgURL, chURL, chCount);
                                }
                            }
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // When we get size of frame we change status.
                        //gameState = GameState.STARTING;
                    }
                    else
                    {
                        visualizingTime += System.nanoTime() - lastVisualizingTime;
                        lastVisualizingTime = System.nanoTime();
                    }
                break;
            }
            
            // Repaint the screen.
            repaint();
            
            // Here we calculate the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    /**
     * Draw the game to the screen. It is called through repaint() method in GameLoop() method.
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        if (gameState == null) {
            return;
        }
        switch (gameState)
        {
            case PLAYING:
                game.Draw(g2d, mousePosition());
                //close button
                g2d.drawImage(btnClose, frameWidth - 40, 10, 30, 30, null);
            break;
            case GAMEOVER:
                //...
            break;
            case MAIN_MENU:
                try {
                    //draw a menu for background
                    //calculate for option item
                    optionItemWidth = (frameWidth / 10) * 4;
                    optionItemHeight = frameHeight / 8;
                    x_Item = frameWidth / 2 - 2 - optionItemWidth;
                    
                    URL bgMenuURL = getClass().getClassLoader()
                            .getResource("main/characterimg/backgroundSpace2.png");
                    BufferedImage bgMenuBF = ImageIO.read(bgMenuURL);
                    g2d.drawImage(bgMenuBF, 0, 0, frameWidth, frameHeight, null);
                    
                    
                    if (isAdmin) {
                        URL playURL = getClass().getClassLoader()
                            .getResource("main/characterimg/play.png");
                        BufferedImage playBF = ImageIO.read(playURL);
                        g2d.drawImage(playBF, frameWidth / 12 * 10, frameHeight / 10 * 8, 
                                optionItemHeight, optionItemHeight, null);

                        
                        URL op1URL = getClass().getClassLoader().getResource("main/characterimg/footballBg.png");
                        BufferedImage bfOp1 = ImageIO.read(op1URL);
                        g2d.drawImage(bfOp1, x_Item , frameHeight/10 * 4, optionItemWidth, optionItemHeight, null);
                        
                        URL op2URL = getClass().getClassLoader().getResource("main/characterimg/SpaceshipBg.png");
                        BufferedImage bfOp2 = ImageIO.read(op2URL);
                        g2d.drawImage(bfOp2, x_Item, frameHeight/10 * 4 + optionItemHeight + 2, optionItemWidth, optionItemHeight, null);
                        
                        URL op3URL = getClass().getClassLoader().getResource("main/characterimg/supperBg.png");
                        BufferedImage bfOp3 = ImageIO.read(op3URL);
                        g2d.drawImage(bfOp3, x_Item, frameHeight/10 * 4 + 2 * (optionItemHeight + 2), optionItemWidth, optionItemHeight, null);
                        
                        if (countClickInMenu >= 1) {
                            URL viewUrl = getClass().getClassLoader().getResource(nameBackground);
                            BufferedImage bfView = ImageIO.read(viewUrl);
                            g2d.drawImage(bfView, frameWidth/2 + 2, frameHeight/10 * 4, 
                                       optionItemWidth, optionItemHeight * 3 + 4, null);
                        }
                        
                        
                    } else {
                        g2d.drawString("Waiting for config", 
                                    frameWidth/2 - 83, (int)(frameHeight * 0.65));
                    }
                    
                } catch (Exception e) {
                    Logger.getLogger(Framework.class.getName())
                                .log(Level.SEVERE, null, e);
                }
            break;
            case OPTIONS:
                //...
            break;
            case GAME_CONTENT_LOADING:
                //...
            break;
        }
    }
    
    
    /**
     * Starts new game.
     */
    private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game();
    }
    
    private void newGame(URL bgURL, URL chURL, int numChar) {
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game(server, client, isIsAdmin(), getId(), bgURL, chURL, numChar);
    }
    
    /**
     *  Restart game - reset game time and call RestartGame() method of game object so that reset some variables.
     */
    private void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game.RestartGame();
        
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }
    
    
    /**
     * Returns the position of the mouse pointer in game frame/window.
     * If mouse position is null than this method return 0,0 coordinate.
     * 
     * @return Point of mouse coordinates.
     */
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    
    
    /**
     * This method is called when keyboard key is released.
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {
        
    }
    
    /**
     * This method is called when mouse button is clicked.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (isAdmin) {
            switch (gameState) {
                
                
                case MAIN_MENU:
                    if (e.getButton() == MouseEvent.BUTTON1){
                        
                        if (new Rectangle(x_Item , frameHeight/10 * 4, optionItemWidth, optionItemHeight)
                                .contains(e.getPoint())) {
                            nameCharacter = "main/characterimg/ball.png";
                            nameBackground = "main/characterimg/sanBongV22.png";
                            countClickInMenu += 1;
                        } else if (new Rectangle(x_Item, frameHeight/10 * 4 + optionItemHeight + 2, optionItemWidth, optionItemHeight)
                                .contains(e.getPoint())) {
                            nameCharacter = "main/characterimg/spaceshipPH.png";
                            nameBackground = "main/characterimg/backgroundSpace2.png";
                            countClickInMenu += 1;
                        } else if (new Rectangle(x_Item, frameHeight/10 * 4 + 2 * (optionItemHeight + 2), optionItemWidth, optionItemHeight)
                                .contains(e.getPoint())) {
                            nameCharacter = "main/characterimg/super.png";
                            nameBackground = "main/characterimg/backgroundAction3.png";
                            countClickInMenu += 1;
                        } // go button
                        else if (new Rectangle(frameWidth / 12 * 10, frameHeight/10 * 8, optionItemHeight, optionItemHeight)
                                .contains(e.getPoint())) {
                            isGoClicked = true;
                            countClickInMenu += 1;
                        }
                        
                        
                        if (countClickInMenu >= 2 && !nameBackground.isEmpty()
                          && !nameCharacter.isEmpty() && isGoClicked == true) {
                            try {
                                //so ngoi sao hien gio dang fix, chua duoc lua chon
                                System.out.println("Vao cho goi newGame");
                                server.configureContent(nameBackground, nameCharacter, countTest);//block hay non block
                                URL bgURLchoosed = getClass().getClassLoader().getResource(nameBackground);
                                URL chURLchoosed = getClass().getClassLoader().getResource(nameCharacter);
                                newGame(bgURLchoosed, chURLchoosed, countTest);//test o day
                                
                            } catch (RemoteException re) {
                                Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, re);
                            }

                        }
                        
                    }    
                    break;
            }
        } else {
            switch (gameState) {
                case PLAYING:
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        
                        if (new Rectangle(frameWidth - 40, 10, 30, 30)
                                .contains(e.getPoint())) {
                            try {
                                server.signOut(id);
                                System.exit(0);
                            } catch (RemoteException ex) {
                                Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                  
                    break;
            }
        }
    }
}
