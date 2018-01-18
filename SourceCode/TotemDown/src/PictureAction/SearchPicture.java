package PictureAction;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import model.Picture;

public class SearchPicture {

/*	public static Vector<String> SearchPName(String path) { // 获取文件名
		// get all the picture-files name from dir(path)
		Vector<String> pictures = new Vector<>();// 保存文件名称数组
		Path dir = Paths.get(path);

		assert Files.exists(dir) && Files.isDirectory(dir);

		try (DirectoryStream<Path> files = Files.newDirectoryStream(dir, new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) {
				return Files.isRegularFile(file)
						&& file.getFileName().toString().matches("^.*[.](?i:jpg|png|bmg|gif|img)$");
			}
		})) {
			for (Path file : files) {
				// paths.add(file.toString());
				pictures.add(file.getFileName().toString());
				// pictures.add(file.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return pictures;
	}*/

	/*public static Vector<String> SearchPPath(String path) { // 获取文件路径
		// get all the picture-files name from dir(path)
		Vector<String> paths = new Vector<>();// 保存文件绝对路径
		Path dir = Paths.get(path);
		assert Files.exists(dir) && Files.isDirectory(dir);

		try (DirectoryStream<Path> files = Files.newDirectoryStream(dir, new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) {
				return Files.isRegularFile(file)
						&& file.getFileName().toString().matches("^.*[.](?i:jpg|png|bmg|gif|img)$");
			}
		})) {
			for (Path file : files) {
				paths.add(file.toString());
				// pictures.add(file.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return paths;
	}
*/
	public static DirectoryStream<Path> GetPath(String path) throws IOException { // 获取文件路径
		// get all the picture-files name from dir(path)
		Vector<String> paths = new Vector<>();// 保存文件绝对路径
		Path dir = Paths.get(path);
		assert Files.exists(dir) && Files.isDirectory(dir);

		DirectoryStream<Path> files = Files.newDirectoryStream(dir, new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) {
				return Files.isRegularFile(file)
						&& file.getFileName().toString().matches("^.*[.](?i:jpg|png|bmg|gif|img)$");
			}
		});

		return files;
	}

	public static DirectoryStream<Path> getAllfile(String path) throws IOException {
		DirectoryStream<Path> paths = null;
		File f = null;
		f = new File(path);
		File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。

		for (File file : files) {
			if (file.isDirectory()) {
				// 如何当前路劲是文件夹，则循环读取这个文件夹下的所有文件
				getAllfile(file.getAbsolutePath());
			} else {
				Path dir = Paths.get(path);
				assert Files.exists(dir) && Files.isDirectory(dir);
				paths = Files.newDirectoryStream(dir, new DirectoryStream.Filter<Path>() {
					public boolean accept(Path file) {
						return Files.isRegularFile(file)
								&& file.getFileName().toString().matches("^.*[.](?i:jpg|png|bmg|gif|img)$");
					}
				});
			}
		}
		return paths;
	}

/*	public static DirectoryStream<Path> getAllfileFList(String path) throws IOException {
		DirectoryStream<Path> paths = null;
		File f = null;
		f = new File(path);
		File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。

		for (File file : files) {
			if (file.isDirectory()) {
				// 如何当前路劲是文件夹，则循环读取这个文件夹下的所有文件
				getAllfile(file.getAbsolutePath().toString());
			} else {
				Path dir = Paths.get(path);
				assert Files.exists(dir) && Files.isDirectory(dir);
				paths = Files.newDirectoryStream(dir, new DirectoryStream.Filter<Path>() {
					public boolean accept(Path file) {
						return Files.isRegularFile(file)
								&& file.getFileName().toString().matches("^.*[.](?i:jpg|png|bmg|gif|img)$");
					}
				});
			}
		}
		return paths;
	}
*/
	public static void save(String readpath, String writepath) throws Exception { // 保存图片到服务器
		FileInputStream in = new FileInputStream(readpath); // "C:/Users/Administrator/Desktop/1.jpg"
		FileOutputStream out = new FileOutputStream(writepath); // "e:/2.jpg"
		BufferedInputStream bufferedIn = new BufferedInputStream(in);
		BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
		byte[] by = new byte[1024];
		while (bufferedIn.read(by) != -1) {
			bufferedOut.write(by);
		}
		// 将缓冲区中的数据全部写出
		bufferedOut.flush();
		bufferedIn.close();
		bufferedOut.close();
	}
	
	

}
