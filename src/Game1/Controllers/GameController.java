package Game1.Controllers;




import java.io.*;
import Game1.models.Board;
import Game1.models.GameState;
import Game1.models.User;

public class GameController {
    private static final String SAVE_DIR = "saves/";
    private static final String SAVE_EXT = ".klotski";

    private Board board;
    private User currentUser;

    //构造方法和设置用户
    public GameController() {
        this.board = new Board();
    }
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Board getBoard() {
        return board;
    }
    public void resetGame() {
        board.reset();
    }


    public boolean moveBlock(Board.Direction direction) {
        // 之后写
        return false;
    }

    //写入游戏
    public boolean saveGame() {
        if (currentUser == null) return false;

        //路径
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdir();         //先写上
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(
                //路径--一个用户一个文件--后缀
                new FileOutputStream(SAVE_DIR + currentUser.getUsername() + SAVE_EXT))) {
            //保存棋盘，用户名
            oos.writeObject(new GameState(board, currentUser.getUsername()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //读取存档
    public boolean loadGame() {
        if (currentUser == null) return false;

        //载入文件
        File file = new File(SAVE_DIR + currentUser.getUsername() + SAVE_EXT);
        if (!file.exists()) return false;

        //input尝试
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            GameState state = (GameState) ois.readObject();     //state包括username和board
            this.board = state.getBoard();
            return true;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isWin() {
        return board.isWin();
    }
}