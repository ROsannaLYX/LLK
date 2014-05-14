package nec.soft.java.utils;

import java.util.LinkedList;
import java.util.Queue;
/**
 * ��������Ϸ�������<br/>
 * 
 * ������������¾�̬�������Ÿ��ⲿ���ã�<br/>
 * newGame:�������̵�ʵ�ʴ�С��ͼ��������������һ�����ߵ�����;
 * move:����ĳЩģʽ�У��Զ��ƶ�����ķ����������ƶ����򣬷����ƶ�������̣�ִ�д˷����󲻱�֤��·����; canMove:�ж�����Ƿ���·����;
 * canRemove:�жϴ�����������ܷ���ȥ; getPath:���ڷ���·������ִ����canRemove���䷵��ֵΪtrueʱ;
 * help:������֣���һ������Ϊ4��һά���鷵�ؿ��Ա���ȥ��x1,y1,x2,y2;
 * reset��������֣���֤ԭ��ÿ��ͼ���������䡣����һ����ά���飬����һ���µĶ�ά����; ���⻹��һ����̬����WAY��ʾͼ�пյ�λ�ã�Ĭ��Ϊ0.<br/>
 * ���⻹���ĸ�˽�з�����<br/>
 * hasWay:�ж�һ��ֱ���ϵ��������Ƿ���ͨ���������˵㣩; turnZero:�ж��������ܷ��ڹ�0�ε�����±���ȥ����Ҫ��hasWay&���˵�ֵ��ͬ;
 * turnOne(Two):���������ƣ���Ҫ��hasWay&���˵�ֵ��ͬ&�յ�ΪWAY.
 * 
 * @version 0.5.1 Finished on 5/10/2013<br/>
 *          �޸����ж�ͨ·�ķ�������Ϊԭ���ķ����붯��Ч��ͬ�û���bug �������ƶ�����ʱ��bug �޸��˴�ɢ������㷨��ʹ����ɢ������
 *          �޸���·����ʾ�����bug since 0.4 �޸�����������Ϊ������ʱ�����bug���޸��˴������̵ķ�ʽ since 0.3
 *          �����������̺��ƶ����̵ķ������޸��˲�����ȥ�߽�ķ����bug
 * @author andong
 */
public class Checker {

	private static final int WAY = 0; // ͨ·
	private static int[] path = new int[9]; // ��¼��ȥʱ������·��
	private static Mode mode; // �趨���̵��ƶ���ʽ��ȡֵΪ(0,1,2,3,4)������ֵΪ���ƶ�

	// ������ʾ������һ������Ϊ4������={x1,y1,x2,y2}���������ڷ���ȫ0������
	public static int[] help(int[][] arr) {
		for (int i = 0; i < arr.length * arr[0].length; i++) {
			for (int j = 0; j < arr.length * arr[0].length; j++) {
				int x1 = i / arr[0].length;
				int y1 = i % arr[0].length;
				int x2 = j / arr[0].length;
				int y2 = j % arr[0].length;
				if (canRemove(arr, x1, y1, x2, y2)) {
					return new int[] { x1, y1, x2, y2 };
				}
			}
		}
		return null;
	}

	/**
	 * �������һ����·���ߵ����̣�ÿ��ͼ���ĸ�����Ϊż������
	 * ���̵Ĵ�СΪʵ�ʴ�С����Ҫ����10*10����Ч���̣���������12*12�����飬������ͼ���Ĳ�����10*10��
	 * width��height����ȫΪ�����������޽⡣
	 * 
	 * @param width
	 *            ���̵Ŀ�ȣ�������������0
	 * @param height
	 *            ���̵ĸ߶ȣ�ͬ��������������0
	 * @param numOfShape
	 *            ָ���ж�����ͼ������ʹ�ã��Ա���������
	 * @param md
	 *            ����ģʽ���������ɵ����̵�Ч�������Mode.java
	 * @return ����һ������㱻0����������
	 */
	public static int[][] newGame(int width, int height, int numOfShape, Mode md) {
		mode = md;
		int[][] arr = new int[height + 2][width + 2];
		do {
			int[] t = new int[height * width];
			int len = md == Mode.blank ? width * height * 3 / 4 : width
					* height;
			for (int i = 0; i < len; i += 2) {
				t[i] = t[i + 1] = mode == Mode.blank ? (int) (Math.random() * (numOfShape + 1))
						: (int) (Math.random() * numOfShape) + 1;
			}
			int times = t.length;
			for (int k = 0; k < times; k++) {
				for (int i = t.length - 1; i >= 1; i--) {
					int ran = (int) (Math.random() * i);
					int temp = t[ran];
					t[ran] = t[i];
					t[i] = temp;
				}
			}
			for (int i = 0; i < t.length; i++) {
				int y = i % width + 1;
				int x = i / width + 1;
				arr[x][y] = t[i];
			}
		} while (!canMove(arr));
		return arr;
	}

	// ��������������˳�򣬲�����һ����·���ߵ����
	public static int[][] reset(int[][] arr) {
		Queue<Integer> q = new LinkedList<Integer>();
		do {
			for (int i = 1; i < arr.length - 1; i++) {
				for (int j = 1; j < arr[0].length - 1; j++) {
					if (arr[i][j] != WAY) {
						q.add(arr[i][j]);
						arr[i][j] = WAY;
					}
				}
			}
			while (!q.isEmpty()) {
				int x, y;
				do {
					x = (int) ((arr.length - 2) * Math.random() + 1);
					y = (int) ((arr[0].length - 2) * Math.random() + 1);
				} while (arr[x][y] != WAY);
				arr[x][y] = q.remove();
			}
		} while (!canMove(arr));
		return arr;
	}

	// �жϵ�ǰ����Ƿ���·����
	public static boolean canMove(int[][] arr) {
		for (int i = 0; i < arr.length * arr[0].length; i++) {
			for (int j = 0; j < arr.length * arr[0].length; j++) {
				int x1 = i / arr[0].length;
				int y1 = i % arr[0].length;
				int x2 = j / arr[0].length;
				int y2 = j % arr[0].length;
				if(canRemove(arr,x1,y1,x2,y2))
					return true;
			}
		}
		return false;
	}

	// �ж�ѡ�е��������ܷ���ȥ
	public static boolean canRemove(int[][] arr, int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2) {
			return false;
		}
		int m1 = arr[x1][y1];
		int m2 = arr[x2][y2];
		if (m1 != m2 || m1 == WAY || m2 == WAY) {
			return false;
		}
		return turnZero(arr, x1, y1, x2, y2) || turnOne(arr, x1, y1, x2, y2)
				|| turnTwo(arr, x1, y1, x2, y2);
	}

	/**
	 * ���Ҫ�ﵽÿ����ȥ����֮�����෽���Զ��ƶ���Ч������ע�⣺ ��Ϊִ����˷�������ܻ���·���ߣ�������ִ�б����������ж��Ƿ�canMove.
	 * 
	 * @param arr
	 *            �ƶ�ǰ������
	 * @param direct
	 *            �ƶ��ķ���1 2 3 4�ֱ���� �� �� �� ��
	 * @return �ƶ��������
	 */
	public static int[][] move(int[][] arr) {
		if (mode == Mode.center) {
			for(int x=1;x<=arr.length/2-2;x++)
				for(int y=1;y<=arr[0].length-2;y++)
					if(arr[x][y]<=0)
						for(int t=x;t>=1;t--)
							arr[t][y] = arr[t-1][y];
			for(int x=arr.length-2;x>=arr.length/2-1;x--)
				for(int y=1;y<=arr[0].length-2;y++)
					if(arr[x][y]<=0)
						for(int t=x;t<=arr.length-2;t++)
							arr[t][y] = arr[t+1][y];
		} else if (mode == Mode.up) {
			for(int x=arr.length-2;x>=1;x--)
				for(int y=1;y<=arr[0].length-2;y++)
					if(arr[x][y]<=0)
						for(int t=x;t<=arr.length-2;t++)
							arr[t][y] = arr[t+1][y];
		} else if (mode == Mode.down) {
			for(int x=1;x<=arr.length-2;x++)
				for(int y=1;y<=arr[0].length-2;y++)
					if(arr[x][y]<=0)
						for(int t = x;t>=1;t--)
							arr[t][y] = arr[t-1][y];
		} else if (mode == Mode.left) {
			for(int x=1;x<=arr.length-2;x++)
				for(int y=arr[0].length-2;y>=1;y--)
					if(arr[x][y]<=0)
						for(int t=y;t<=arr[0].length-2;t++)
							arr[x][t] = arr[x][t+1];
		} else if (mode == Mode.right) {
			for(int x=1;x<=arr.length-2;x++)
				for(int y=1;y<=arr[0].length-2;y++)
					if(arr[x][y]<=0)
						for(int t=y;t>=1;t--)
							arr[x][t] = arr[x][t-1];
		}
		return arr;
	}

	/**
	 * ��Ϊjavaֻ�ܷ���һ��ֵ������Ϊ�˻�ȡ·������Ҫ���û�ѡ������ͼ��ʱ����ִ��canRemove�����棬˵��������ȥ��
	 * ��ʱ���ô˷������Ի�ȡһ��·�� �����������еĶ˵�͹յ㣬��Ϊͨ����Щ��Ϳ��Ի���·��
	 * 
	 * @return ����һ��һά���飬�������ĵ�һ��λ�ñ�ʾ�м��������ĵ㡣����ÿ�����������һ�����x��y���ꡣ
	 *         ���3��0��2��2��2��2��0����ʾ��������(0,2),(2,2),(2,0)��ɵ�����
	 */
	public static int[] getPath() {
		return path;
	}

	// �ж�λ��һ��ֱ�ߣ��������������б�ߣ����������Ƿ���ͨ.�����ͨ������true������ͨ����ֱ�ߣ�����false��
	private static boolean hasWay(int[][] arr, int x1, int y1, int x2, int y2) {
		if (x1 == x2) {
			if (y1 > y2) {
				int t = y1;
				y1 = y2;
				y2 = t;
			}
			for (int y = y1 + 1; y < y2; y++) {
				if (arr[x1][y] > WAY) {
					return false;
				}
			}
			return true;
		} else if (y1 == y2) {
			if (x1 > x2) {
				int t = x1;
				x1 = x2;
				x2 = t;
			}
			for (int x = x1 + 1; x < x2; x++) {
				if (arr[x][y1] > WAY) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// �ж��ܷ���ת0�ε��������ͨ
	private static boolean turnZero(int[][] arr, int x1, int y1, int x2, int y2) {
		if (arr[x1][y1] == arr[x2][y2] && hasWay(arr, x1, y1, x2, y2)) {
			path[0] = 2;
			path[1] = x1;
			path[2] = y1;
			path[3] = x2;
			path[4] = y2;
			return true;
		}
		return false;
	}

	// �ж��ܷ���תһ�� ���������ͨ
	private static boolean turnOne(int[][] arr, int x1, int y1, int x2, int y2) {
		if ((hasWay(arr, x1, y1, x1, y2) && hasWay(arr, x1, y2, x2, y2) && arr[x1][y2] == WAY)) {
			path[0] = 3;
			path[1] = x1;
			path[2] = y1;
			path[3] = x1;
			path[4] = y2;
			path[5] = x2;
			path[6] = y2;
			return true;
		}
		if ((hasWay(arr, x1, y1, x2, y1) && hasWay(arr, x2, y1, x2, y2) && arr[x2][y1] == WAY)) {
			path[0] = 3;
			path[1] = x1;
			path[2] = y1;
			path[3] = x2;
			path[4] = y1;
			path[5] = x2;
			path[6] = y2;
			return true;
		}
		return false;
	}

	// �ж��ܷ���ת���ε��������ͨ
	private static boolean turnTwo(int[][] arr, int x1, int y1, int x2, int y2) {
		for (int x = 0; x < arr.length; x++) {
			if (hasWay(arr, x1, y1, x, y1) && hasWay(arr, x, y1, x, y2)
					&& hasWay(arr, x, y2, x2, y2) && arr[x][y1] == WAY
					&& arr[x][y2] == WAY) {
				path[0] = 4;
				path[1] = x1;
				path[2] = y1;
				path[3] = x;
				path[4] = y1;
				path[5] = x;
				path[6] = y2;
				path[7] = x2;
				path[8] = y2;
				return true;
			}
		}
		for (int y = 0; y < arr[0].length; y++) {
			if (hasWay(arr, x1, y1, x1, y) && hasWay(arr, x1, y, x2, y)
					&& hasWay(arr, x2, y, x2, y2) && arr[x1][y] == WAY
					&& arr[x2][y] == WAY) {
				path[0] = 4;
				path[1] = x1;
				path[2] = y1;
				path[3] = x1;
				path[4] = y;
				path[5] = x2;
				path[6] = y;
				path[7] = x2;
				path[8] = y2;
				return true;
			}
		}
		return false;
	}

}
