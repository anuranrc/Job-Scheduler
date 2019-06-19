package jobscheduler;

/*This is the jobscheduler.jobscheduler.Job object*/
public class Job {
	
	public int job_Id;
    public long arrivalTime;
	public long totalTime;
    public long rem_execTime;
	public long executedTime;


	public Job(int newId, long newArrivalTime, long newTotalTime){
        job_Id =newId;
        totalTime = newTotalTime;
        arrivalTime=newArrivalTime;
		executedTime =0;
		rem_execTime=totalTime;
	}
}

