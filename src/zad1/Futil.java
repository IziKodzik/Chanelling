package zad1;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {


	public static void processDir(String dirName, String resultFileName) {

		File file = new File(resultFileName);
		if(file.exists()) {
			boolean deleted = file.delete();
		}
		File path = new File(dirName);

		try {
			Files.walkFileTree(path.toPath(),new SimpleFileVisitor<Path>(){

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
						throws IOException {

					CharBuffer buffer = readChannel(file.toString());
					writeChannel(resultFileName,buffer);

					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e)
						throws IOException {
					if (e == null) {
						System.out.println(dir);
						return FileVisitResult.CONTINUE;
					} else {
						throw e;
					}
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}


	}


	private static void writeChannel(String fileName,CharBuffer data)
		throws IOException{

		Charset charSet = StandardCharsets.UTF_8;
		ByteBuffer result = charSet.encode(data);

		FileChannel channel = new FileOutputStream(fileName,true).getChannel();
		channel.position(channel.size());

		channel.write(result);
		channel.close();

	}

	private static CharBuffer readChannel(String fileName)
			throws IOException{

		File file = new File(fileName);
		FileInputStream in = new FileInputStream(file);
		FileChannel channel = in.getChannel();

		int size = (int)channel.size();
		ByteBuffer buffer = ByteBuffer.allocate(size);

		channel.read(buffer);
		channel.close();
		buffer.flip();

		Charset inCSet = Charset.forName("cp1250");

		return inCSet.decode(buffer);

	}


}
