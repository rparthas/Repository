package entity;

import java.io.RandomAccessFile;

public class TemplateIOTask extends Task {

	public TemplateIOTask(String taskId, String clientName,
			String responseQueueName, String queueUrl, long fileSize,String filepath) {
		super(taskId, clientName, responseQueueName, queueUrl);
		this.fileSize = fileSize;
	}

	private long fileSize = 0;
	private String filePath=null;
	
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println(this.getTaskId()+" Creating File of  [" + fileSize + "] Bytes");
			RandomAccessFile f = new RandomAccessFile(filePath+getTaskId()+getClientName(), "rw");
            f.setLength(fileSize);
            f.close();
            return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String toString() {
		return getTaskId();
	}
}
