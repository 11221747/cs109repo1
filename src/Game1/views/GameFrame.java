package Game1.views;



import javax.swing.*;
import Game1.Controllers.GameController;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Game1.models.Block;
import Game1.models.Board;



public class GameFrame extends JFrame {
    private static final int CELL_SIZE = 80;
    private static final int BOARD_WIDTH = Board.COLS * CELL_SIZE;
    private static final int BOARD_HEIGHT = Board.ROWS * CELL_SIZE;

    private GameController controller;
    private BoardPanel boardPanel;


    private Block selectedBlock;

    public GameFrame(GameController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Klotski Puzzle");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        boardPanel = new BoardPanel();
        add(boardPanel);

        JPanel controlPanel = new JPanel();
        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");
        JButton resetButton = new JButton("Reset");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        upButton.addActionListener(e -> controller.moveBlock(Board.Direction.UP));
        downButton.addActionListener(e ->  controller.moveBlock(Board.Direction.DOWN));
        leftButton.addActionListener(e ->  controller.moveBlock(Board.Direction.LEFT));
        rightButton.addActionListener(e ->  controller.moveBlock(Board.Direction.RIGHT));

        resetButton.addActionListener(e -> {
            controller.resetGame();
            selectedBlock = null;
            boardPanel.repaint();
        });

        saveButton.addActionListener(e -> {
            if (controller.saveGame()) {
                JOptionPane.showMessageDialog(this, "Game saved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadButton.addActionListener(e -> {
            if (controller.loadGame()) {
                selectedBlock = null;
                boardPanel.repaint();
                JOptionPane.showMessageDialog(this, "Game loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to load game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        controlPanel.add(upButton);
        controlPanel.add(downButton);
        controlPanel.add(leftButton);
        controlPanel.add(rightButton);
        controlPanel.add(resetButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);

        add(controlPanel, BorderLayout.SOUTH);


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: controller.moveBlock(Board.Direction.UP); break;
                    case KeyEvent.VK_DOWN: controller.moveBlock(Board.Direction.DOWN); break;
                    case KeyEvent.VK_LEFT: controller.moveBlock(Board.Direction.LEFT); break;
                    case KeyEvent.VK_RIGHT: controller.moveBlock(Board.Direction.RIGHT); break;
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }




    private class BoardPanel extends JPanel {
        public BoardPanel() {
            setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
            setBackground(Color.LIGHT_GRAY);

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    Point p = new Point(evt.getX() / CELL_SIZE, evt.getY() / CELL_SIZE);
                    selectedBlock = controller.getBoard().getBlockAt(p);
                    repaint();
                }
            });
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw grid
            g.setColor(Color.DARK_GRAY);
            for (int i = 0; i <= Board.ROWS; i++) {
                g.drawLine(0, i * CELL_SIZE, BOARD_WIDTH, i * CELL_SIZE);
            }
            for (int j = 0; j <= Board.COLS; j++) {
                g.drawLine(j * CELL_SIZE, 0, j * CELL_SIZE, BOARD_HEIGHT);
            }

            // Draw exit
            g.setColor(Color.WHITE);
            g.fillRect(2 * CELL_SIZE, 4 * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.RED);
            g.drawString("EXIT", 2 * CELL_SIZE + 10, 4 * CELL_SIZE + 20);

            // Draw blocks
            for (Block block : controller.getBoard().getBlocks()) {
                int x = block.getX() * CELL_SIZE;
                int y = block.getY() * CELL_SIZE;
                int width = block.getWidth() * CELL_SIZE;
                int height = block.getHeight() * CELL_SIZE;

                g.setColor(block.getColor());
                g.fillRect(x, y, width, height);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, width, height);

                // Draw block type
                String type = "";
                switch (block.getType()) {
                    case CAO_CAO: type = "曹操"; break;
                    case GUAN_YU: type = "关羽"; break;
                    case GENERAL: type = "将军"; break;
                    case SOLDIER: type = "兵"; break;
                }

                g.drawString(type, x + width/2 - 10, y + height/2 + 5);
            }

            // Highlight selected block
            if (selectedBlock != null) {
                int x = selectedBlock.getX() * CELL_SIZE;
                int y = selectedBlock.getY() * CELL_SIZE;
                int width = selectedBlock.getWidth() * CELL_SIZE;
                int height = selectedBlock.getHeight() * CELL_SIZE;

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.YELLOW);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(x, y, width, height);
            }

            // Draw move count
            g.setColor(Color.BLACK);
            g.drawString("Moves: " + controller.getBoard().getMoves(), 10, 20);
        }
    }


    //javabean

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public void setSelectedBlock(Block selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }


}
