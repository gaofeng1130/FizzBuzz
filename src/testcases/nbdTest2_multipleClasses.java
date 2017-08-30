package testcases;

import java.util.ArrayList;

public class nbdTest2_multipleClasses 
{
	//constructor 1 NBD = 2
	public nbdTest2_multipleClasses()
	{
		if(1 < 2)
		{
			System.out.println("hello");
		}
	}


class test1
{
	//constructor 2 NBD = 3
			public test1(int b)
			{
				do
				{
					for(int a = 0; a < 2; a ++)
					{
						System.out.println("hello");
					}
				}while(b < 2);
			}
			
			//method 1 NBD = 3
			public void method1()
			{
				int a = 4;
				while(a < 5)
				{
					if(a == 9)
					{
						
					}
					else if (a != 1)
					{
						
					}
				}
			}
}
}

class test2
{
	//metohod 2 NBD = 4
	public void method2()
	{
		int a = 1;
		int b = 2;
		try
		{
			switch(a)
			{
			case 1: System.out.println("hh");
			case 2: 
				for(; b < 3; b++)
				{
				
				};
			default: System.out.println("gg");
			}
		}
		catch(Exception e)
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(int g : list)
			{
				
			}
		}
	}
	
	//metohod 3 NBD = 3
	public void method3()
	{
		
			synchronized(this)
			{
		          int a  = 0;
		          
		     }
		
		
		b: 
        {
        System.out.println();
        
        
        break b;
        }
	}
	
	//metohod 4 NBD = 2
	public void method4(double f)
	{
		{
			b:
			{
				System.out.println();
        
        
				break b;
			}
		}
		
	}
}