package model;

import java.io.File;
//import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
//import org.farng.mp3.id3.ID3v2_2;

public class ExtractorModel
{
	public ExtractorModel()
	{
		
	}
	private List<File> listMp3Files(File sourcePath) throws Exception
	{
		if(!sourcePath.isDirectory())
			throw new Exception("The selected path is not a directory");
		else if(!sourcePath.canRead())
			throw new Exception("Premission denied to read the directory");
		ArrayList<File> musicList=new ArrayList<File>();
		listMp3Files(musicList, sourcePath);
		return musicList;
	}
	
	private ArrayList<File> listMp3Files(ArrayList<File> list, File path)
	{
		File files[] = path.listFiles();
		if(files!=null)
		{
			for (File file : files)
			{
				if (file.isDirectory() && file.canRead())
				{
					listMp3Files(list, file);
				}
				else if(file.getName().endsWith("mp3"))
					list.add(file);
			}
		}
		return list;
	}
	
	public String[][] getMusicInfo(File sourcePath) throws Exception
	{
		List<File> mp3Files= listMp3Files(sourcePath);
		String[][] data = new String[mp3Files.size()][5];
		for(int i=0;i<mp3Files.size();i++)
		{
			File mp3File=mp3Files.get(i);
			/*ID3v2_2 tags=new ID3v2_2(new RandomAccessFile(mp3File,"r"));
			data[i][0]=mp3File.getAbsolutePath();
			data[i][1]=tags.getSongTitle();
			data[i][2]=tags.getAlbumTitle();
			data[i][3]=tags.getLeadArtist();
			data[i][4]=tags.getSongGenre();*/
			data[i][0]=mp3File.getAbsolutePath();
			data[i][1]="aaaa";
			data[i][2]="aaaa";
			data[i][3]="aaaa";
			data[i][4]="aaaa";
		}
		return data;
	}
}
