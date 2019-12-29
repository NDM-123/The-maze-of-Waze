package gui;
/*
1)print graph using stdDraw
2)save printed graph
3)run algorithms operations - print the actions
4)if graph changes outside of the class -> class updates itself

 */


import algorithms.Graph_Algo;
import dataStructure.*;
import utils.Point3D;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.*;

public class graphGui extends JFrame implements Serializable, MouseListener, MouseMotionListener {
    graph gra =null;
    DGraph dg = new DGraph();
    Graph_Algo ga = new Graph_Algo();
    JMenuBar menuFrame;
        JMenu fileMenu, AlgorithmsMenu;
        JMenuItem openItem,createItem, saveItem,ShortestPathLengthItem,ShortestPathRouteItem,TravelSalesmanProblemItem;
        JTextField setx,sety;
        private JPanel _panel;
        LinkedList<Point3D> points ;
        Point3D mPivot_point = null;
        boolean mDraw_pivot = false;
        boolean mMoving_point = false;
        boolean mCreate = false;
        private int kRADIUS = 5;


        public graphGui() {
            points = new LinkedList<Point3D>();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new FlowLayout());
            this.setBounds(500, 500, 600, 600);
            this.setTitle("The maze of Waze");
            initComponents();
            createGui();
            this.setVisible(true);
        }
    public graphGui(graph g) {
            this.gra =g;
            this.ga.init(g);
        points = new LinkedList<Point3D>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(500, 500, 600, 600);
        this.setTitle("The maze of Waze");
        initComponents();
        createGui();
        this.setVisible(true);
    }

        public void initComponents() {

            //set menu bar
            menuFrame = new JMenuBar();
            setJMenuBar(menuFrame);
            menuFrame.setVisible(true);

            //set file menu	with open, save
            fileMenu = new JMenu("File");
            openItem = new JMenuItem("Open");

            //keyboard shortcuts
            openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  ActionEvent.CTRL_MASK));
            fileMenu.add(openItem);

            saveItem = new JMenuItem("Save");
            saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
            fileMenu.add(saveItem);

            createItem = new JMenuItem("Create");
            createItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
            fileMenu.add(createItem);

            menuFrame.add(fileMenu);

            // set edit menu with copy paste
            AlgorithmsMenu = new JMenu("Algorithms");
            ShortestPathLengthItem = new JMenuItem("Shortest Path Length");
            ShortestPathLengthItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
            AlgorithmsMenu.add(ShortestPathLengthItem);

            ShortestPathRouteItem = new JMenuItem("Shortest Path Route");
            ShortestPathRouteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
            AlgorithmsMenu.add(ShortestPathRouteItem);

            TravelSalesmanProblemItem = new JMenuItem("Travel Salesman Problem");
            TravelSalesmanProblemItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
            AlgorithmsMenu.add(TravelSalesmanProblemItem);
            menuFrame.add(AlgorithmsMenu);
            if(mCreate){


                this.addMouseListener(this);
                this.addMouseMotionListener(this);
                setx = new JTextField("set x");
                sety = new JTextField("set y");
                setx.setFont(new Font("arial", Font.PLAIN, 14));
                sety.setFont(new Font("arial", Font.PLAIN, 14));

                this.add(setx);
                this.add(sety);

            }
        }

        public void createGui() {
            openItem.addActionListener(new ActionListener() {
                //File menu
                @Override
                public void actionPerformed(ActionEvent e) {
                    readFileDialog();
                }
            });
            saveItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    writeFileDialog();
                }
            });
            createItem.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    mCreate=true;

                   initComponents();

                }

            });
            // Algorithms menu
            ShortestPathLengthItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //                textField.setText("copy choozen");				to do
                }
            });
            ShortestPathRouteItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //               textField.setText("paste choozen");				to do
                }
            });
            TravelSalesmanProblemItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //   textField.setText("paste choozen");							to do
                }
            });
        }
    public void actionPerformed(ActionEvent e) {
    String x = setx.getText();
        String y = sety.getText();
        double xnum = Double.parseDouble(x);
        double ynum = Double.parseDouble(y);
        Point3D p = new Point3D(xnum,ynum,0);
        points.add(p);
        repaint();

    }
        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (mDraw_pivot) {
                mPivot_point.setX(x);
                mPivot_point.setY(y);

                repaint();
            }

        }
        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (mDraw_pivot) {
                mPivot_point.setX(x);
                mPivot_point.setY(y);

                repaint();
            }

        }
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            Point3D tmp = new Point3D(x, y);

            int min_dist = (int) (kRADIUS * 1.5);
            double best_dist = 1000000;
            for (Point3D p : points) {
                double dist = tmp.distance3D(p);
                if (dist < min_dist && dist < best_dist) {
                    mPivot_point = p;
                    best_dist = dist;
                    mMoving_point = true;
                }
            }

            if (mPivot_point == null) {
                mPivot_point = new Point3D(x, y);
            }else{
                int x1 = e.getX();
                int y1 = e.getY();
                Point3D tmp1 = new Point3D(x1,y1);
                Point3D hey =null;
                for(Point3D p : points) {
                    double dist = tmp1.distance3D(p);
                    if (dist <= min_dist && dist <= best_dist) {
                        mPivot_point = p;

                    hey = p;
                         break;
                }


                }
                if(hey!=null)points.add(hey);
            }

            mDraw_pivot = true;
            repaint();
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!mMoving_point) {
                points.add(new Point3D(mPivot_point));
                repaint();
            }
            mMoving_point = false;
            mPivot_point = null;
            mDraw_pivot = false;
        }


        public void readFileDialog() {
            //		try read from the file
            FileDialog fd = new FileDialog(this, "Open text file", FileDialog.LOAD);
            fd.setFile("*.txt");
            fd.setDirectory("C:\\");
            fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
            fd.setVisible(true);
            String folder = fd.getDirectory();
            String fileName = fd.getFile();
           DGraph dg;
            try {
                FileInputStream file = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(file);

                dg = (DGraph)in.readObject();
               for(node_data nd : dg.vertices.values()){
                   points.add(nd.getLocation());
                }
                repaint();
                in.close();
                file.close();

                System.out.println("Object has been deserialized");

                file.close();
                in.close();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.print("Error reading file " + ex);
                System.exit(2);
            }
        }
        public void writeFileDialog() {
            //		 try write to the file
            FileDialog fd = new FileDialog(this, "Save the text file", FileDialog.SAVE);
            fd.setFile("*.txt");
//            fd.setFilenameFilter(new FilenameFilter() {
//                @Override
//                public boolean accept(File dir, String name) {
//                    return name.endsWith(".txt");
//                }
//            });
            fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
            fd.setVisible(true);
            String folder = fd.getDirectory();
            String fileName = fd.getFile();
            try {
                DGraph g = this.dg;




                    FileOutputStream file = new FileOutputStream(fileName);
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    out.writeObject(g);


                file.close();
                out.close();


            } catch (IOException ex) {
                System.out.print("Error writing file  " + ex);
            }

        }
        public void paint1(Graphics g){
            super.paint(g);

//            DGraph dgr = this.gra;
//            for (node_data nd :dgr.getV()){
//                Point3D point = nd.getLocation();
//                if(point!=null){
//                    g.setColor(Color.BLUE);
//                    g.fillOval((int) point.x() - kRADIUS, (int) point.y() - kRADIUS,
//                            2 * kRADIUS, 2 * kRADIUS);
//                        for(edge_data ed : dgr.getE(nd.getKey())){
//
//                        if(ed.getTag()==0){
//
//                            ed.setTag(1);
//                            g.setColor(Color.yellow);
//                        }else{
//                            g.setColor(Color.red);
//                        }
//                        node_data des = dgr.getNode(ed.getDest());
//                        Point3D con = des.getLocation();
//                            if(con!=null)
//                            {
//                                g.drawLine(point.ix(), point.iy(), con.ix(), con.iy());
//                                g.drawString(ed.getWeight()+"", (point.ix()+con.ix())/2, (point.iy()+con.iy())/2);      //weight
//                                g.setColor(Color.BLACK);
//                    //            g.drawString(String.format("%.2f", dist), (int) ((point.x() + con.x()) / 2), (int) ((point.y() + con.y()) / 2));
//                            }
//
//                        }
//                }
//
//            }
//
//
//
//
      }
        public void paint(Graphics g) {

            super.paint(g);

            Point3D prev = null;

            for (Point3D p : points) {
                g.setColor(Color.BLUE);
                g.fillOval((int) p.x() - kRADIUS, (int) p.y() - kRADIUS,
                        2 * kRADIUS, 2 * kRADIUS);

                if (prev != null) {
                    g.setColor(Color.YELLOW);
                    g.fillOval( (int) (p.x() +((2/3)*(prev.x()-p.x() )) ) ,
                            (int) (p.y() +(  (2/3)*(prev.y()-p.y() )  ) ) ,   2*kRADIUS,  2*kRADIUS ) ;

                    g.setColor(Color.RED);
                    g.drawLine((int) p.x(), (int) p.y(),
                            (int) prev.x(), (int) prev.y());

                    double dist = prev.distance3D(p);
                    g.drawString(String.format("%.2f", dist),
                            (int) ((p.x() + prev.x()) / 2),
                            (int) ((p.y() + prev.y()) / 2));
                }

                prev = p;
            }

            if (mDraw_pivot && !mMoving_point) {
                g.setColor(Color.BLUE);
                g.fillOval((int) mPivot_point.x() - kRADIUS, (int) mPivot_point.y() - kRADIUS,
                        2 * kRADIUS, 2 * kRADIUS);
                if (prev != null) {
                    g.setColor(Color.RED);
                    g.drawLine((int) mPivot_point.x(), (int) mPivot_point.y(),
                            (int) prev.x(), (int) prev.y());

                    double dist = prev.distance3D(mPivot_point);
                    g.drawString(String.format("%.2f", dist), (int) ((mPivot_point.x() + prev.x()) / 2), (int) ((mPivot_point.y() + prev.y()) / 2));
                }

            }
            int i =0;
            double wei=0;
            for(Point3D p : points){


                if(prev!=null && mPivot_point!=null) {
                    wei = prev.distance3D(mPivot_point);
                }
                    nodeData nd = new nodeData(p, i, wei, "", 0);
                    dg.vertices.put(i, nd);


                    prev = p;
                    i++;

            }

    }

        public static void main(String[] args) {
            graphGui Menu = new graphGui();

        }

    }


