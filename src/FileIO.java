
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIO {

	public static void write(byte[] bs, String outPath) {
		try {
			RandomAccessFile f = new RandomAccessFile(outPath, "rw");
			f.write(bs);
			f.close();
		}

		catch (IOException ex) {
			System.out.println(ex);
		}
	}

	public static void main(String[] args) {
		// String filePath = "src/FileIO/test.txt";
		String filePath = args[1];
		byte[] buf = new byte[100];
		byte[] data = null;
		int dataIdx = 0;
		if (args.length != 3) {
			System.out.println("Please provide two arguments to main.");
		} else {
			try {
				RandomAccessFile f = new RandomAccessFile(filePath, "r");
				data = new byte[(int) f.length()];
				while (true) {
					int nBytes = f.read(buf);
					if (nBytes == -1) {
						if (args[0].equals("mv")) {
							System.out.println("here!");
							f.close();
							Files.delete(Paths.get(filePath));
						}
						break;
					}
					for (int i = 0; i < nBytes; i++) {
						data[dataIdx] = buf[i];
						dataIdx++;
					}
				}
				f.close();
			} catch (FileNotFoundException ex) {
				System.out.println("File not found.");
				return;
			} catch (IOException ex) {
				System.out.println(ex);
				return;
			}

			String[] splitup_path = args[2].split("/");
			int ele_count = 0;

			for (int i = 0; i < splitup_path.length; i++) {
				if (splitup_path[i] != null) {
					ele_count++;
				}
			}
			String path_dir = "";
			// if (splitup_path.length > 1) {path_dir += splitup_path[1];}
			String new_filename = splitup_path[ele_count - 1];
			for (int i = 0; i < ele_count - 1; i++) {
				path_dir += "/" + splitup_path[i];
			}

			Path w_path = Paths.get(System.getProperty("user.dir") + path_dir + "/");
			if (!(java.nio.file.Files.exists(w_path))) {
				try {
					Files.createDirectories(w_path);
				} catch (IOException ex) {
					System.out.println(ex);
					return;
				}
			}

			FileIO.write(data, w_path + "/" + new_filename);
			System.out.println("File action complete.");
		}
	}
}
