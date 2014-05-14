package nec.soft.java.fight;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RoomPane extends JPanel implements MouseListener {

	public int roomNumber = 0; // �����
	public boolean exist = false; // �����÷����Ƿ����
	public User[] users = new User[4]; // �û���Ϣ��ÿ���������4���û���
	public int userNum; // �÷��䵱ǰ���û�����
	public boolean fighting; // ��ʾ�÷������Ϸ�Ƿ��ѿ�ʼ
	
	private boolean isHighlight = false; // �����Ƿ��ڸ÷����ϴ���������¼���������������Ӧ������
	private Image image;
	private Image highlightImage;
	private Image sword; // ���÷������Ϸ�ѿ�ʼ������ʾ��ͼ��
	private User curUser; // ��ǰ�ͻ��˵�ʹ����
	GameHall gh;

	public RoomPane(User root,GameHall gh) {
		this.gh=gh;
		curUser = root;
		userNum = 0;
		fighting=false;
		/*try {
			image = ImageIO.read(new File("image/house.png"));
			highlightImage = ImageIO.read(new File("image/house1.png")); // ������ͼƬ
			sword = ImageIO.read(new File("image/sword.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}*/
		setOpaque(false);
		addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth();
		int heigth = this.getHeight();
		if (exist) {
			if (!isHighlight)
				try {
					g.drawImage(ImageIO.read(new File("image/house.png")), 0, 0, width, heigth, this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				try {
					g.drawImage(ImageIO.read(new File("image/house1.png")), 0, 0, width, heigth, this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			g.setFont(new Font("����", Font.BOLD, 15));
			g.setColor(new Color(255, 0, 0));
			g.drawString(roomNumber + "�ŷ���", 10, 20);
			
			Image headImage;
			for(int i=0;i<userNum;i++){
				try {
					headImage = ImageIO.read(new File(users[i].headURL));
					g.drawImage(headImage, (width/6-10)+i*(width/6+7), heigth/3+heigth/6, width/6, heigth/5, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			if(fighting){
				try {
					g.drawImage(ImageIO.read(new File("image/sword.png")), width/3*2, heigth/8, width/10, heigth/7, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (userNum < 4 && !fighting && curUser.roomNumber<0) {
			//curUser.state=1;
			users[userNum] = curUser;
			userNum++;
			gh.update();
			repaint();
		}else{
			//System.out.println("�����������ѿ�ս������ĳ��������");
			if(fighting){
				ErrorFrame.showError("�Բ��𣬸÷����ѿ�ʼ��Ϸ");
			}else{
				if(userNum >= 4){
					ErrorFrame.showError("�Բ��𣬸÷�������");
				}else{
					if(curUser.roomNumber>=0){
						ErrorFrame.showError("�Բ��������ڷ�������");
					}
				}
			}
		}
	}

	public String toString (){
		return (roomNumber+"  "+exist+"  "+userNum);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		isHighlight = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		isHighlight = false;
		repaint();
	}

}