package nec.soft.java.fight;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RoomPane extends JPanel implements MouseListener {

	public int roomNumber = 0; // 房间号
	public boolean exist = false; // 表明该房间是否存在
	public User[] users = new User[4]; // 用户信息（每个房间最多4个用户）
	public int userNum; // 该房间当前的用户数量
	public boolean fighting; // 表示该房间的游戏是否已开始
	
	private boolean isHighlight = false; // 表明是否在该房间上触发了鼠标事件（若触发，房间应变亮）
	private Image image;
	private Image highlightImage;
	private Image sword; // 若该房间的游戏已开始，则显示该图标
	private User curUser; // 当前客户端的使用者
	GameHall gh;

	public RoomPane(User root,GameHall gh) {
		this.gh=gh;
		curUser = root;
		userNum = 0;
		fighting=false;
		/*try {
			image = ImageIO.read(new File("image/house.png"));
			highlightImage = ImageIO.read(new File("image/house1.png")); // 高亮的图片
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

			g.setFont(new Font("宋体", Font.BOLD, 15));
			g.setColor(new Color(255, 0, 0));
			g.drawString(roomNumber + "号房间", 10, 20);
			
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
			//System.out.println("房间已满或已开战或已在某个房间中");
			if(fighting){
				ErrorFrame.showError("对不起，该房间已开始游戏");
			}else{
				if(userNum >= 4){
					ErrorFrame.showError("对不起，该房间已满");
				}else{
					if(curUser.roomNumber>=0){
						ErrorFrame.showError("对不起，您已在房间里了");
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