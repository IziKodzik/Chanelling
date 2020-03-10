package zad1;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {


	public static void processDir(String dirName, String resultFileName) {

		File path = new File(dirName);

		try {
			Files.walkFileTree(path.toPath(),new SimpleFileVisitor<Path>(){

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
						throws IOException {
					System.out.println(file);
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e)
						throws IOException {
					if (e == null) {
						System.out.println(dir);
						return FileVisitResult.CONTINUE;
					} else {
						// directory iteration failed
						throw e;
					}
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private static void put(ByteBuffer b,int val){b.put((byte)val);}

}
