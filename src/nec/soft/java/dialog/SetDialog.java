package nec.soft.java.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import nec.soft.java.share.SharedVar;
import nec.soft.java.utils.Constants;
import nec.soft.java.utils.ShowHelper;

public class SetDialog extends JDialog implements ActionListener {

	private JButton confirm;
	private JButton cancle;
	private JRadioButton easy;
	private JRadioButton hard;
	private JRadioButton general;
	private JRadioButton bhard;
	private JCheckBox bgMusic;
	private JCheckBox effect;

	private static final long serialVersionUID = -9221834969234945571L;

	public SetDialog() {
		init();
	}

	private void init() {
		getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "音乐", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		panel.setBounds(10, 10, 356, 50);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel bglabel = new JLabel("背景音乐：");
		bglabel.setBounds(46, 21, 60, 15);
		panel.add(bglabel);

		bgMusic = new JCheckBox("开");
		bgMusic.setBounds(112, 17, 60, 23);
		if (SharedVar.backgroud_music)
			bgMusic.setSelected(true);
		panel.add(bgMusic);

		JLabel label = new JLabel("音效");
		label.setBounds(233, 21, 54, 15);
		panel.add(label);

		effect = new JCheckBox("开");
		effect.setBounds(265, 17, 54, 23);
		if (SharedVar.effct_music)
			effect.setSelected(true);
		panel.add(effect);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "困难等级", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel_1.setBounds(10, 85, 356, 50);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		easy = new JRadioButton("简单");
		easy.setBounds(20, 16, 65, 23);
		panel_1.add(easy);

		general = new JRadioButton("一般");
		general.setBounds(97, 16, 65, 23);
		panel_1.add(general);

		hard = new JRadioButton("困难");
		hard.setBounds(183, 16, 65, 23);
		panel_1.add(hard);

		bhard = new JRadioButton("超难");
		bhard.setBounds(262, 16, 65, 23);
		panel_1.add(bhard);

		ButtonGroup group = new ButtonGroup();
		group.add(easy);
		group.add(general);
		group.add(hard);
		group.add(bhard);
		switch (SharedVar.game_stage) {
		case Constants.GAME_EASY:
			easy.setSelected(true);
			break;
		case Constants.GAME_GENERAL:
			general.setSelected(true);
			break;
		case Constants.GAME_HARD:
			hard.setSelected(true);
			break;
		case Constants.GAME_CHARD:
			bhard.setSelected(true);
			break;
		default:
			break;
		}

		confirm = new JButton("确定");
		confirm.addActionListener(this);
		confirm.setBounds(118, 156, 93, 23);
		getContentPane().add(confirm);

		cancle = new JButton("取消");
		cancle.addActionListener(this);
		cancle.setBounds(259, 156, 93, 23);
		getContentPane().add(cancle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancle) {
			dispose();
			return;
		}
		if(e.getSource() == confirm) {
			if(bgMusic.isSelected())
				SharedVar.backgroud_music = true;
			else 
				SharedVar.backgroud_music = false;
			if(effect.isSelected()) 
				SharedVar.effct_music = true;
			else
				SharedVar.effct_music = false;
			if(easy.isSelected()) {
				SharedVar.game_stage = Constants.GAME_EASY;
			}
			if(general.isSelected()) {
				SharedVar.game_stage = Constants.GAME_GENERAL;
			}
			if(hard.isSelected()) {
				SharedVar.game_stage = Constants.GAME_HARD;
			}
			if(bhard.isSelected()) {
				SharedVar.game_stage = Constants.GAME_CHARD;
			}
			dispose();
		}
	}

	public static void open() {
		SetDialog dialog = new SetDialog();
		dialog.setTitle("设置");
		dialog.setModal(true);
		dialog.setResizable(false);
		dialog.setSize(400, 230);
		ShowHelper.showCenter(dialog);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}
