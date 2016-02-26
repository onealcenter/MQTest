package ly;

import java.io.IOException;
import java.util.Properties;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class SendTest {

	private static Properties props;

	static {
		props = new Properties();
		props.put("hostname", "");
		props.put("port", "");
		props.put("ccsid", "");
		props.put("qmanager", "");
		props.put("channel", "");
		props.put("localqueue", "");
	}

	@SuppressWarnings("deprecation")
	public void sendMsg(String message) {
		MQEnvironment.hostname = props.getProperty("hostname");
		MQEnvironment.port = Integer.parseInt(props.getProperty("port"));
		MQEnvironment.CCSID = Integer.parseInt(props.getProperty("ccsid"));
		MQEnvironment.channel = props.getProperty("channel");

		try {
			MQQueueManager qmgr = new MQQueueManager(props.getProperty("qmanager"));
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
			MQQueue q = qmgr.accessQueue(props.getProperty("localqueue"), openOptions);
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			MQMessage msg = new MQMessage();
			msg.format = MQC.MQFMT_STRING;
			msg.write(message.getBytes("utf-8"));
			q.put(msg, pmo);
			qmgr.commit();
			while (true) {
			}
		} catch (MQException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
