package jobscheduler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class jobscheduler {
	
	public static RBTree<Integer, Job> rbt = new RBTree<Integer, Job>();
	public static MinHeap hp = new MinHeap();

	public static String currentline = "";
    public static long nextarrivaltime;
    public static Scanner filescanner;

    public static List<String> op = new ArrayList<String>();
	
	public static void main(String[] args) throws FileNotFoundException {
        /*This is for checking the command line argument */
		/*
		if (args.length < 1) {
			System.out.println("Error, usage: java ClassName inputfile");
			System.exit(1);
		}
		*/

        //filescanner = new Scanner(new FileInputStream(args[0]));
        Scanner file_Input = new Scanner(new File("D:/Projects/Academic Projects/ADS Project/Job Scheduler/Input and Output files/sample_input1.txt"));
        filescanner = file_Input;
       // String line = "";
        readInstruction();
        //**store arrival time of next instruction***
        Scheduler sc_job= new Scheduler();
        sc_job.schedule(hp,rbt);

        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter("D:/Projects/Academic Projects/ADS Project/Job Scheduler/Input and Output files/sample_ouput1.txt"));
            for(String s : op){
                out.println(s);
            }
            out.close();
        }
        catch (IOException e){System.out.println(e);
        }

	}

	public static int readInstruction(){

	    Scanner file_Input = filescanner;
        //Read the first line

        String line = "";
        if(currentline.equals("")){
            line = file_Input.nextLine();
        }
        else{
            line = currentline;
        }
        String[] colon = line.split(": ");
        int start_time = Integer.parseInt(colon[0]);
        String[] fbrac = colon[1].split("[(]");
        String command = fbrac[0];
        int jobid=0;
        int total_time=0;
        try {
            String[] comma = fbrac[1].split(",");
            jobid = Integer.parseInt(comma[0]);
            String[] rbrac = comma[1].split("[)]");
            total_time = Integer.parseInt(rbrac[0]);
        }
        catch(NumberFormatException e){
            String[] rbrac = fbrac[1].split("[)]");
            jobid = Integer.parseInt(rbrac[0]);
        }

        /*Insert job*/

        if (command.equals("Insert")){
            Job x = new Job(jobid,start_time,total_time );
            hp.insert(x);
            rbt.insert(jobid,x);
        }


        /* Print job */

        if (command.equals("PrintJob")){
            int jobid2 =total_time;
            if (jobid2==0) {
                Job j = rbt.search(jobid);
                if(j!=null) {

                    //System.out.println("(" + j.job_Id + "," + j.executedTime + "," + j.totalTime + ")");
                    String x= "(" + j.job_Id + "," + j.executedTime + "," + j.totalTime + ")";
                    op.add(x);
                }

                else {
                    //System.out.println("(0,0,0)");
                    op.add("(0,0,0)");
                }

            } else if (jobid2>jobid){
                String p = "";
                for(int jid : rbt.keys(jobid,jobid2)){
                    Job j = rbt.search(jid);
                    p=p+("("+j.job_Id+ ","+ j.executedTime +"," + j.totalTime+"),");
                }

                if(p.length()==0){
                   // System.out.println("(0,0,0)");
                    op.add("(0,0,0)");
                } else {
                   // System.out.println(p.substring(0,p.length()-1));
                    op.add(p.substring(0,p.length()-1));
                }

            }
        }

        /*Next job*/
        if (command.equals("NextJob")){
            Integer jid = rbt.nxtSmlNd(jobid);
            if(jid!=null){
                Job j = rbt.search(jid);
                //System.out.println("("+j.job_Id+ ","+ j.executedTime +"," + j.totalTime+")");
                op.add("("+j.job_Id+ ","+ j.executedTime +"," + j.totalTime+")");
            }
            else{
                //System.out.println("(0,0,0)");
                op.add("(0,0,0)");
            }
        }

        /*Previous job*/
        if (command.equals("PreviousJob")){
            Integer jid = rbt.prevLarNd(jobid);
            if(jid!=null){
                Job j = rbt.search(jid);
               // System.out.println("("+j.job_Id+ ","+ j.executedTime +"," + j.totalTime+")");
                op.add("("+j.job_Id+ ","+ j.executedTime +"," + j.totalTime+")");
                }
            else{
                //System.out.println("(0,0,0)");
                op.add("(0,0,0)");
                }
        }

        /*Process NEXT Line*/

        if(!file_Input.hasNext()){
            nextarrivaltime = 0;
            return 0;
        }
        line = file_Input.nextLine();
        currentline = line;
        colon = line.split(": ");
        start_time = Integer.parseInt(colon[0]);
        nextarrivaltime = start_time;
        return start_time;
    }

}
