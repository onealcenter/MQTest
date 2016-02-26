package ly;

import java.util.Properties;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class ReceiveTest {

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
	public void getMsg() {
		MQEnvironment.hostname = props.getProperty("hostname");
		MQEnvironment.port = Integer.parseInt(props.getProperty("port"));
		MQEnvironment.CCSID = Integer.parseInt(props.getProperty("ccsid"));
		MQEnvironment.channel = props.getProperty("channel");

		try {
			MQQueueManager qmgr = new MQQueueManager(props.getProperty("qmanager"));
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_FAIL_IF_QUIESCING;
			MQQueue q = qmgr.accessQueue(props.getProperty("localqueue"), openOptions);
			@SuppressWarnings("unused")
			MQMessage msg = new MQMessage();
			while (true) {
				while((msg = fetchOneMsg(q))!=null)
				{
					//deal msg
				}
			}
		} catch (MQException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	private static MQMessage fetchOneMsg(MQQueue q) {
		MQGetMessageOptions mgo = new MQGetMessageOptions();
		mgo.options |= MQC.MQGMO_WAIT;
		mgo.waitInterval |= -1;
		MQMessage msg = new MQMessage();
		try {
			q.get(msg, mgo);
			;
		} catch (MQException e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
