package nec.soft.java.client;
import java.util.*;
import java.lang.*;

//����һ��������
class LinkLine 
{
	//�������ӵ�
	private int[] points;
	//���յ�ͼ
	private int[][] blocks;

	public LinkLine(int[][] blocks)
	{
		this.blocks = blocks;
	}
	
	//�ж��������Ƿ������ӳ��߶�
	public boolean linkSegment(int x1 , int y1 , int x2 , int y2)
	{

		//�����ͬһ����ֱ����
		if(x1 == x2)	
		{
			if(y1 > y2)
			{
				int temp = y1;
				y1 = y2;
				y2 = temp;
			}
			for(int y = y1 ; y <= y2 ; y++)
			{
				//������ϰ���
				if(blocks[y][x1] > 0)
				{
					return false;
				}


			}
			return true;
		}
		//�����ͬһ��ˮƽ����
		else if(y1 == y2)
		{
			if(x1 > x2)
			{
				int temp = x1;
				x1 = x2;
				x2 = temp;
			}
			for(int x = x1 ; x <= x2 ; x++)
			{
				//������ϰ���
				if(blocks[y1][x] > 0)
				{
					return false;
				}
			}
			return true;
		}

		return false;
	}
	//�Ƿ�������ӳɷ�����Ϸ���������
	public boolean foldLineAble(int x1 , int y1 , int x2 , int y2)
	{
		//ÿ�ζ�����۵�
		points = null;
		int minDistance = 0;

		for(int x = x1 - 1; x >= 0; x--)	//��������
		{
			//�����һ���߶ο�������
			if(linkSegment(x1 , y1 , x , y1)) 
			{	
				//���ʣ������Ҳ��������
				if(linkSegment(x , y1 , x , y2) &&
					linkSegment(x , y2 , x2 , y2))
				{	
					//������С·��
					minDistance = Math.abs(x1 - x) + 
						Math.abs(y2 - y1) + Math.abs(x2 - x);
					
					//�����۵�
					points = new int[]{x1 , y1 , x , y1 , 
						x , y2 , x2 , y2};
					//�ҵ�����,�����������������
					break;
				}
			}
			else
			{	//�����ϰ�,�����������������
				break;
			}
		}

		for(int x = x1 + 1; x < blocks[0].length; x++)	//��������
		{
			//�����һ���߶ο�������
			if(linkSegment(x1 , y1 , x , y1)) 
			{	
				//���ʣ������Ҳ��������
				if(linkSegment(x , y1 , x , y2) &&
					linkSegment(x , y2 , x2 , y2))
				{	
					//������С·��
					int temp = Math.abs(x1 - x) + 
						Math.abs(y2 - y1) + Math.abs(x2 - x);
					
					//���С���ϴ�һ�����·�̻���һ������û������
					if(temp < minDistance || minDistance == 0)
					{
						minDistance = temp;
						//�����۵�
						points = new int[]{x1 , y1 , x , y1 , 
							x , y2 , x2 , y2};	
					}
					
					//�ҵ�����,�����������������
					break;
				}
			}
			else
			{
				break;
			}
		}

		for(int y = y1 + 1; y < blocks.length; y++)	//��������
		{
			//�����һ���߶ο�������
			if(linkSegment(x1 , y1 , x1 , y)) 
			{	
				//���ʣ������Ҳ��������
				if (linkSegment(x1 , y , x2 , y) && 
					linkSegment(x2 , y , x2 , y2))
				{
					//������С·��
					int temp = Math.abs(y - y1) + 
						Math.abs(x2 - x1) + Math.abs(y- y2);
					
					if(temp < minDistance || minDistance == 0)
					{
						minDistance = temp;
						//�����۵�
						points = new int[]{x1 , y1 , x1 , y , 
							x2 , y , x2 , y2};
					}
					break;
				}
			}
			else
			{
				break;
			}
		}

		for(int y = y1 - 1; y >= 0; y--)	//��������
		{
			//�����һ���߶ο�������
			if(linkSegment(x1 , y1 , x1 , y)) 
			{	
				//���ʣ������Ҳ��������
				if (linkSegment(x1 , y , x2 , y) && 
					linkSegment(x2 , y , x2 , y2))
				{
					//������С·��
					int temp = Math.abs(y - y1) + 
						Math.abs(x2 - x1) + Math.abs(y- y2);
					
					if(temp < minDistance || minDistance ==0)
					{
						minDistance = temp;
						//�����۵�
						points = new int[]{x1 , y1 , x1 , y ,
							x2 , y , x2 , y2};
					}
					break;
				}
			}
			else
			{
				break;
			}
		}

		if(points != null)
		{
			return true;
		}
		
		return false;
	}
	public boolean linkAble(int x1 , int y1 , int x2 , int y2)
	{
		boolean result = false;
		
		//ֻҪ��һ���ϰ���λ�ռ�����false
		if(blocks[y1][x1] == 0
			|| blocks[y2][x2] == 0)
		{
			return false;
		}
		
		//�����ͬһ����
		if(x1 == x2 && y1 == y2)
		{
			return false;
		}

		if(blocks[y1][x1] == blocks[y2][x2])
		{	
			/*
				�Ȱ�Ҫ�ж����ӵ��������ڵ�ͼ��ֵ��0,���������
				�������ǵ����ϰ���
			*/
			int temp1 = blocks[y1][x1];
			int temp2 = blocks[y2][x2];
			blocks[y1][x1] = 0;
			blocks[y2][x2] = 0;
			//���ж��Ƿ�������ӳ��߶�
			result = linkSegment(x1 , y1 , x2 ,y2);
			
			if(result)
			{	//�������ӵ�
				points = new int[]{x1 , y1 , x2 ,y2};
			}
			else
			{
				//�Ƿ������������
				result = foldLineAble(x1 , y1 , x2 ,y2);
			}
			blocks[y1][x1] = temp1;
			blocks[y2][x2] = temp2;
		}
			
		return result;
	}

	//��ȡ���ӵ�
	public int[] getPoints()
	{
		return points;
	}
}