package com.space.tools;

import javax.swing.*;

public class Logger extends JFrame
{
    private static Logger logger;
    private int width, height;

    private JTextArea textArea = null;

    private Logger(String title, int width, int height)
    {
        super(title);
        setSize(width, height);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane pane = new JScrollPane(textArea);
        getContentPane().add(pane);
        setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pane.setAutoscrolls(true);
    }

    public void showInfo(String data)
    {
        textArea.insert("\n", 0);
        textArea.insert(data, 0);
        this.getContentPane().validate();
    }

    public static synchronized Logger getInstance()
    {
        if (logger == null)
            logger = new Logger("Log", 500, 200);
        return logger;
    }
}
