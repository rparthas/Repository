package utility;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import monitor.WorkerMonitor;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

public class RestartAll {

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	static HashSet<Long> setNums = new HashSet<Long>();

	public static void main(String[] args) throws UnknownHostException {
		AmazonEC2 ec2 = new AmazonEC2Client(
				new ClasspathPropertiesFileCredentialsProvider());
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		ec2.setRegion(usWest2);
		List<String> instanceIds = new ArrayList<>();
		String instanceid=null;
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		
        List<String> valuesT1 = new ArrayList<String>();
        valuesT1.add("worker");
        Filter filter = new Filter("tag-value", valuesT1);

        DescribeInstancesResult result = ec2.describeInstances(request.withFilters(filter));

        List<Reservation> reservations = result.getReservations();

        for (Reservation reservation : reservations) {
            List<Instance> instances = reservation.getInstances();
            for (Instance instance : instances) {
            	instanceid =instance.getInstanceId();
                PrintManager.PrintMessage(instanceid);
                instanceIds.add(instanceid);
               List<Tag> abc = instance.getTags();
               for (Tag aaa:abc){
            	   PrintManager.PrintMessage(aaa.getKey()+" : "+aaa.getValue());
               }

            }
        } 
      /* instanceIds.remove("i-cb45adfc");
        StopInstancesRequest stopReq = new StopInstancesRequest(instanceIds);
        ec2.stopInstances(stopReq);
        
        List<String> instanceIdTerms = new ArrayList<>();
        instanceIdTerms.add(WorkerMonitor.retrieveInstanceId());
        TerminateInstancesRequest term = new TerminateInstancesRequest(instanceIdTerms);
        ec2.terminateInstances(term);*/
        
		RebootInstancesRequest requestRe = new RebootInstancesRequest(instanceIds);
		ec2.rebootInstances(requestRe);
		
		
		
	}

}
