package mancala;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.GameModel;

public class ModelSaver {
	public static void save(GameModel m) {
		try {
			FileOutputStream fileOut = new FileOutputStream("saveGame.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(m);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static GameModel load() {
		GameModel m = null;
		try {
			FileInputStream fileIn = new FileInputStream("saveGame.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			m = (GameModel) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("GameModel class not found");
			c.printStackTrace();
			return null;
		}
		return m;
	}
}