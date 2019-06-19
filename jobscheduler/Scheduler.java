package jobscheduler;

public class Scheduler {


	private int execTimePeriod = 5;

	Job p;
	public static long Clk = 0;

	/*This is the method to schedule a job*/

	public void schedule(MinHeap hp, RBTree<Integer, Job> rbt) {

		while (Clk <= jobscheduler.nextarrivaltime) {
			if(hp.getSize()<1){
				Clk = jobscheduler.nextarrivaltime;
				jobscheduler.readInstruction();
				continue;
			}
			p = hp.remove();

			if (p.rem_execTime > execTimePeriod) {

				if(jobscheduler.nextarrivaltime <= Clk + execTimePeriod){
					long interval = jobscheduler.nextarrivaltime - Clk;
					long tempTimeInCPU = p.executedTime;
					long tempremTime = p.rem_execTime;
					p.executedTime = p.executedTime + interval;
					p.rem_execTime = p.rem_execTime-interval;

					//update jobscheduler.jobscheduler.RBTree
					Clk = Clk +interval;
					jobscheduler.readInstruction();
					Clk = Clk -interval;
					p.executedTime = tempTimeInCPU;
					p.rem_execTime = tempremTime;
				}

				p.executedTime = p.executedTime + execTimePeriod;
				p.rem_execTime = p.rem_execTime- execTimePeriod;
				Clk = Clk + execTimePeriod;

				if (p.rem_execTime > 0) {
					hp.insert(p);
					rbt.insert(p.job_Id,p);
				}
				else {
					rbt.del(p.job_Id);
				}

			} else {

				if(jobscheduler.nextarrivaltime <= Clk +p.rem_execTime){
					long interval = jobscheduler.nextarrivaltime - Clk;
					long tempTimeInCPU = p.executedTime;
					long tempremTime = p.rem_execTime;
					p.executedTime = p.executedTime + interval;
					p.rem_execTime = p.rem_execTime-interval;
					Clk = Clk +interval;
					jobscheduler.readInstruction();
					Clk = Clk -interval;
					p.executedTime = tempTimeInCPU;
					p.rem_execTime = tempremTime;
				}
				p.executedTime = p.executedTime + p.rem_execTime;
				Clk = Clk + p.rem_execTime;
				p.rem_execTime = 0;
				rbt.del(p.job_Id);
			}
		}
	}

}

	
		
	
