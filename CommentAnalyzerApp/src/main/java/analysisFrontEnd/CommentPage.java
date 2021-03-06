package analysisFrontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.fasterxml.jackson.core.JsonParseException;

import api.CommentRetriever;
import api.Retriever;
import mainFrontEnd.SearchByPage;
import mainFrontEnd.TaskBar;

public class CommentPage extends SearchByPage{

	public CommentPage(JFrame frame, TaskBar bar, String videoID) {
		super(frame, bar);
		this.videoID = videoID;
	}

	protected void createJTextFields() {
		final JTextField field2 = new JTextField();
		field2.addActionListener(e ->{
			panel.parseComments(field2.getText());
		});
		JPanel fieldPanel = new JPanel();
		field2.setColumns(15);
		fieldPanel.add(field2);
		fieldPanel.setBorder(new EtchedBorder());
		top.add(fieldPanel);
		top.add(Box.createRigidArea(new Dimension(0,40)));
		
	}

	@Override
	protected void createPanels(ArrayList retrieverInput) {
		panel = new CommentListPanel(frame);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		for (String comment: (ArrayList<String>)retrieverInput) {
			panel.addPanel(new CommentPanel(comment));
		}
		panel.setPanel();
		this.add(panel);
	}

	@Override
	protected Retriever createRetriever() {
		// TODO Auto-generated method stub
		return new CommentRetriever();
	}

	@Override
	protected void setInitialContent() {
		try {
			retrieverInput = retriever.retrieve(videoID);
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		createPanels(retrieverInput);
		createJTextFields();
	}
	@Override
	protected JLabel getTitle() {
		JLabel label = new JLabel("Comment Page");
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
	private String videoID;
	private CommentListPanel panel;
}
