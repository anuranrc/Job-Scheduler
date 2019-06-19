package jobscheduler;

import java.util.ArrayList;


/*Implementation of the Min Hp*/

public class MinHeap{
    private static ArrayList<Job> Hp;
    private static final int Frnt = 1;
 
    public MinHeap(){
    	
    	Hp =new ArrayList<>();
    	Hp.add(null);
    	
    }

    private int parent(int indx)
    {
        return indx / 2;
    }
 
    private int leftChild(int indx)
    {
        return (2 * indx);
    }


    private int rightChild(int indx)
    {
        return (2 * indx) + 1;
    }

    /*checks if a node is leaf or not*/
    private boolean checkLeaf(int indx)
    {
        if (indx >=  (Hp.size() / 2)  &&  indx <= Hp.size())
        { 
            return true;
        }
        return false;
    }
 
    private void swap(int fpos, int spos)
    {
        Job tmp;
        tmp = Hp.get(fpos);
        Hp.set (fpos, Hp.get(spos));
        Hp.set(spos, tmp);
    }

    /* Creates a minimum Heap of the given BST*/
    private void minHeapify(int pos)
    {
        if (!checkLeaf(pos))
        { 
            if ( (Hp.get(pos)).executedTime > Hp.get(leftChild(pos)).executedTime || (Hp.get(pos)).executedTime > Hp.get(rightChild(pos)).executedTime)
            {
                if (Hp.get(leftChild(pos)).executedTime < Hp.get(rightChild(pos)).executedTime)
                {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }
                else 
                {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }


    /*Insert operation in Heap*/
    public void insert(Job p)
    {
        Hp.add(p);
        int current = Hp.size()-1;
       
        while (current!=1&&(Hp.get(current).executedTime < Hp.get(parent(current)).executedTime))
        {
        	 
            swap(current,parent(current));
            current = parent(current);
        	
        }
    }

    /*Removes the min value node from the Heap*/
 
    public Job remove()
    {
    	Job pr=null;
    	if(Hp.size()>1)
    	{
    	pr = Hp.get(Frnt);
        Hp.set(Frnt, Hp.get(Hp.size()-1));
        Hp.remove(Hp.size()-1);
        minHeapify(Frnt);
    	}
        return pr;
    }
    
    public int getSize()
    {
    	return Hp.size()-1;
    }
 
   
}