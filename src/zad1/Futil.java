package zad1;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
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
					File f = new File(file.toString());
					if(!f.isDirectory()) {
						FileInputStream in = new FileInputStream(f);
						ByteBuffer buffer = ByteBuffer.allocate(100);
						FileChannel channel = in.getChannel();
						channel.read(buffer);
						channel.close();
						System.out.println(buffer.get(0));
					}


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

	private void writeChannel(String fileName,byte[] data)
		throws IOException{

		ByteBuffer buffer = ByteBuffer.wrap(data);

		FileOutputStream out = new FileOutputStream(fileName);
		FileChannel channel = out.getChannel();

		channel.write(buffer);
		channel.close();

	}

	private byte[] readChannel(String fileName)
			throws IOException{

		File file = new File(fileName);
		FileInputStream in = new FileInputStream(file);
		FileChannel channel = in.getChannel();

		int size = (int)channel.size();
		ByteBuffer buffer = ByteBuffer.allocate(size);

		int nBytes = channel.read(buffer);
		channel.close();
		buffer.flip();

		byte[] result = new byte[nBytes];
		buffer.get(result);
		return result;

	}

}
