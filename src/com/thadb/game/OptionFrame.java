package com.thadb.game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	GameOptions options;
	JLabel lblCursorScale;
	JSlider sliderCursorScale;

	private JPanel contentPane;
	private float cursorScale;
	private JLabel lblBulletssec;
	private JSlider sliderBulletsPerSec;
	private JLabel lblBulletScale;
	private JSlider sliderBulletScale;

	public OptionFrame(final GameOptions options) {
		this.options = options;

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		lblCursorScale = new JLabel("Cursor Scale");
		GridBagConstraints gbc_lblCursorScale = new GridBagConstraints();
		gbc_lblCursorScale.insets = new Insets(0, 0, 5, 5);
		gbc_lblCursorScale.gridx = 0;
		gbc_lblCursorScale.gridy = 1;
		contentPane.add(lblCursorScale, gbc_lblCursorScale);
		this.cursorScale = options.getCursorScale();
		sliderCursorScale = new JSlider();
		sliderCursorScale.setMinimum(1);
		sliderCursorScale.setMaximum(30);
		sliderCursorScale.setValue((int) (options.getCursorScale() * 10));
		sliderCursorScale.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				System.out.println((float) sliderCursorScale.getValue() / 10);
				cursorScale = (float) sliderCursorScale.getValue() / 10;
				options.setCursorScale(cursorScale);
			}
		});

		GridBagConstraints gbc_sliderBulletsPerSec = new GridBagConstraints();
		gbc_sliderBulletsPerSec.gridwidth = 5;
		gbc_sliderBulletsPerSec.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderBulletsPerSec.insets = new Insets(0, 0, 5, 5);
		gbc_sliderBulletsPerSec.gridx = 1;
		gbc_sliderBulletsPerSec.gridy = 1;
		contentPane.add(sliderCursorScale, gbc_sliderBulletsPerSec);

		lblBulletssec = new JLabel("Bullets/Sec");
		GridBagConstraints gbc_lblBulletssec = new GridBagConstraints();
		gbc_lblBulletssec.insets = new Insets(0, 0, 5, 5);
		gbc_lblBulletssec.gridx = 0;
		gbc_lblBulletssec.gridy = 2;
		contentPane.add(lblBulletssec, gbc_lblBulletssec);

		sliderBulletsPerSec = new JSlider();
		sliderBulletsPerSec.setMaximum(500);
		sliderBulletsPerSec.setValue(options.getBulletsPerSecond());
		sliderBulletsPerSec.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				options.setBulletsPerSecond(sliderBulletsPerSec.getValue());
			}
		});
		sliderBulletsPerSec.setMinimum(1);
		GridBagConstraints gbc_sliderBulletsPerSec1 = new GridBagConstraints();
		gbc_sliderBulletsPerSec1.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderBulletsPerSec1.gridwidth = 5;
		gbc_sliderBulletsPerSec1.insets = new Insets(0, 0, 5, 5);
		gbc_sliderBulletsPerSec1.gridx = 1;
		gbc_sliderBulletsPerSec1.gridy = 2;
		contentPane.add(sliderBulletsPerSec, gbc_sliderBulletsPerSec1);

		lblBulletScale = new JLabel("Bullet Scale");
		GridBagConstraints gbc_lblBulletScale = new GridBagConstraints();
		gbc_lblBulletScale.insets = new Insets(0, 0, 5, 5);
		gbc_lblBulletScale.gridx = 0;
		gbc_lblBulletScale.gridy = 3;
		contentPane.add(lblBulletScale, gbc_lblBulletScale);

		sliderBulletScale = new JSlider();
		sliderBulletScale.setMinimum(1);
		sliderBulletScale.setValue((int) (options.getBombScale() * 10));
		sliderBulletScale.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				options.setBombScale((float) sliderBulletScale.getValue() / 10);
			}
		});
		GridBagConstraints gbc_sliderBulletScale = new GridBagConstraints();
		gbc_sliderBulletScale.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderBulletScale.gridwidth = 5;
		gbc_sliderBulletScale.insets = new Insets(0, 0, 5, 5);
		gbc_sliderBulletScale.gridx = 1;
		gbc_sliderBulletScale.gridy = 3;
		contentPane.add(sliderBulletScale, gbc_sliderBulletScale);

		JButton btnReset = new JButton("Reset");
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.insets = new Insets(0, 0, 0, 5);
		gbc_btnReset.gridx = 9;
		gbc_btnReset.gridy = 7;
		contentPane.add(btnReset, gbc_btnReset);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				options.setCursorScale(cursorScale);
			}

		});
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.gridx = 10;
		gbc_btnApply.gridy = 7;
		contentPane.add(btnApply, gbc_btnApply);
	}

}
