package framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPUtility {

	
	/**
	 * @desc this method will make sftp connection to server
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @return ChannelSftp
	 */
	public static ChannelSftp makeSFTPConnection(String host, int port, String userName, String password) {
		JSch jSch = new JSch();
		Session session=null;
		ChannelSftp channel=null;
		
		//create an SSH channel
		try {
			session=jSch.getSession(userName,host,port);
		} catch (JSchException e) {
			throw new RuntimeException("Failed to connect to  SFTP server");
		}
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		try {
			session.connect();
		} catch (Exception e) {
			throw new RuntimeException("Failed to connect to  SFTP session");
		}
		// open an SFTP channel
		try {
			channel=(ChannelSftp) session.openChannel("sftp");
		} catch (JSchException e) {
			throw new RuntimeException("Failed to open SFTP channel");
		}
		try {
			channel.connect();;
		} catch (JSchException e) {
			throw new RuntimeException("Failed to connect SFTP channel");
		}
		return channel;
	}
	
	
	/**
	 * @desc this method will list all files from given remote repository
	 * @param remoteDirectory
	 * @param channel
	 * @return Vector<ChannelSftp.LsEntry>
	 */
	public static Vector<ChannelSftp.LsEntry> listAllFilesInSpecifiedRemoteDirectory(String remoteDirectory,ChannelSftp channel){
		try {
			channel.cd(remoteDirectory);
		} catch (SftpException e) {
			throw new RuntimeException("Failed to change directory on the SFTP server");
		}
		Vector<ChannelSftp.LsEntry> fileList;
		try {
			fileList=channel.ls(".");
		} catch (Exception e) {
			throw new RuntimeException("Failed to list all files in the given remoteDirectory "+remoteDirectory);
		}
		return fileList;
	}
	
	/**
	 * @desc this method will download file from specified remote repository with given file-prefix, file extension and file's last modification date
	 * @param prefix
	 * @param extension
	 * @param date
	 * @param localDirectory
	 * @param fileList
	 * @param channel
	 * @return String
	 */
	public static String downloadFileWithSpecificPrefixAndSpecificExtensionForSpecificDateInLocalDirectory(String prefix, String extension, String date, String localDirectory, Vector<ChannelSftp.LsEntry> fileList, ChannelSftp channel) {
		String file="";
		InputStream inputStream=null;
		Date latestDate=null;
		int i=0;
		String fileName=null;
		for (LsEntry lsEntry : fileList) {
			fileName=lsEntry.getFilename();
			SftpATTRS fileAttributes=lsEntry.getAttrs();
			SimpleDateFormat dateFormat=new SimpleDateFormat(SFTPproperties.SFTP_FILE_DATEFORMAT.toString());
			// get date&time of file last modification
			long modificationTime=fileAttributes.getMTime()*1000L;
			Date modificationDate=new Date(modificationTime);
			String modificationDateString=dateFormat.format(modificationDate);
			// extract date from date&time from sftp
			String dateParts=modificationDateString.split(" ")[0];
			// date is today
			if (fileName.startsWith(prefix) && fileName.endsWith(extension) && dateParts.equals(date)) {
				if(i==0) {
					latestDate=modificationDate;
					file=fileName;
					// get inputstream from file
				}else {
					if (modificationDate.after(latestDate)) {
						latestDate=modificationDate;
						file=fileName;
					}
				}
				try {
					inputStream=channel.get(fileName);
				} catch (SftpException e) {
					throw new RuntimeException("Not able to get file with prefix "+prefix+ " , file extension "+extension+ " for date "+date);
				}
				i++;
			}
		}
		
		try {
			// Downloading the specified file from sftp to the specified local directory
			FileUtils.copyInputStreamToFile(inputStream, new File(localDirectory+file));
			inputStream.close();
		}catch (Exception e) {
			throw new RuntimeException("Failed to copy file "+file+ " in the local directory "+localDirectory);
		}
		Session session;
		try {
			session=channel.getSession();
		} catch (JSchException e) {
			throw new RuntimeException("Failed to get the SFTP session");
		}
		
		// close  the channel and session
		if (channel != null) channel.disconnect();
		if(session != null) session.disconnect();
		return localDirectory+file;
	}
	
	
	
	/**
	 * @desc This method will deleted file with specific file-prefix and specific file-extension from local directory
	 * @param filePrefix
	 * @param fileExtension
	 * @param localDirectory
	 */
	public static void deleteFileWithSpecificPrefixAndSpecificFileExtensionFromLocalDirectory(String filePrefix, String fileExtension, String localDirectory) {
		File directory=new File(localDirectory);
		File[] files = directory.listFiles((dir,name)-> name.endsWith(fileExtension) && name.startsWith(filePrefix));
		for (File file : files) {
			if(file.exists()) file.delete();
		}
	}
	
	/**
	 * @desc this method closes channel and session
	 * @param ChannelSftp channel
	 */
	public static void closeChannelAndSession(ChannelSftp channel) {
		Session session;
		try {
			session=channel.getSession();
		} catch (Exception e) {
			throw new RuntimeException("Failed to get the SFTP session");
		}
		channel.exit();
		if(channel!=null) channel.disconnect();
		if(session!=null) session.disconnect();
	}
	
	
	
	/**
	 * @desc this method uploads the file at given filePath(absolute) in SFTP server in given remote directory
	 * @param channel
	 * @param localDirectory
	 * @param remoteDirectory
	 * @param filePath
	 */
	public static void uploadFileToRemoteDirectoryInSFTPServer(ChannelSftp channel, String localDirectory, String remoteDirectory,String filePath) {
		try {
			filePath=filePath.replace("\\", "/");
			File file = new File(filePath);
			FileInputStream fileInputStream = new FileInputStream(file);
			channel.put(fileInputStream, file.getName());		
		} catch (Exception e) {
			throw new RuntimeException("Failed to upload file "+filePath +" in SFTP server remote directory "+remoteDirectory);
		}
	}
	
	/**
	 * @desc this method unzips the zip file in given repository and returns unzipfiles path
	 * @param String zipFileName
	 * @param String localDirectory
	 * @return Set<String> unzipFilePathSet
	 */
	public static Set<String> unzipZipFileInGivenLocalDirectory(String zipFileName, String localDirectory){
		Set<String> unzipFilePathSet = new LinkedHashSet<>();
		Path currentRelativePath=Paths.get("");
		String basePath=currentRelativePath.toAbsolutePath().toString();
		String localZipFilePath=basePath+"\\"+localDirectory+zipFileName;
		try {
			ZipFile zipFile = new ZipFile(localZipFilePath);
			Enumeration<? extends ZipEntry> entries=zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				File entryFile=new File(basePath+"\\"+localDirectory,entry.getName());
				FileUtils.copyInputStreamToFile(zipFile.getInputStream(entry), entryFile);
				unzipFilePathSet.add(basePath+"\\"+localDirectory+"\\"+entry.getName());
			}
			zipFile.close();
		} catch (Exception e) {
			throw new RuntimeException("Unable to unzip file: "+zipFileName);
		}
		return unzipFilePathSet;
	}
	 
	
	
	
	
	
}
