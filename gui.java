package MP7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class gui implements ActionListener{

    JFrame frame = new JFrame("MACHINE PROBLEM 8");
    JPanel pan, pan1, pan2, pan3, pan4, pan5;
    JLabel lab, lab1;
    JTextField txt = new JTextField(5);
    JTextField [] txt1 = new JTextField[1000];
    DefaultTableModel dtm;
    JTable tab;
    JButton but, but1, but2 ,but3, but4, but5;
    JTextArea area;

    gui(){
        frame.setSize(800,700);
        frame.setVisible(true);

        pan = new JPanel();
        pan.setBackground(new Color(255, 199, 199));
        pan.setLayout(new FlowLayout());

        lab = new JLabel("ENTER NUMBER OF MATRIX: ");
        lab.setFont(new Font("Times new roman", Font.PLAIN, 20));

        txt.setText(" ");
        txt.addActionListener(this);
        but3 = new JButton("ENTER");
        but3.addActionListener(this);

        pan1 = new JPanel();
        pan1.setBackground(new Color(255, 226, 226));
        pan1.setLayout(new BoxLayout(pan1, BoxLayout.PAGE_AXIS));

        pan2 = new JPanel();
        pan2.setBackground(new Color(255, 226, 226));

        pan3 = new JPanel();
        pan3.setBackground(new Color(255, 226, 226));
        pan3.setLayout(new FlowLayout());

        pan4 = new JPanel();
        pan4.setBackground(new Color(255, 226, 226));
        pan4.setLayout(new BoxLayout(pan4, BoxLayout.PAGE_AXIS));

        pan5 = new JPanel();
        pan5.setBackground(new Color(255, 226, 226));
        pan5.setLayout(new BoxLayout(pan5, BoxLayout.PAGE_AXIS));

        but2 = new JButton("RANDOM");
        but2.addActionListener(this);

        frame.add(pan, BorderLayout.NORTH);
        pan.add(lab);
        pan.add(txt);
        pan.add(but3);
        pan.add(but2);
        pan.updateUI();
        pan1.add(pan2, BorderLayout.NORTH);
        pan1.add(pan3, BorderLayout.CENTER);
        frame.add(pan1, BorderLayout.CENTER);
        frame.add(pan4, BorderLayout.EAST);
        frame.add(pan5, BorderLayout.WEST);

    }
    int n;
    int [][] graph;
    int click = 0;
    int [][]rand;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==but3){
            pan2.updateUI();

            n = Integer.valueOf(txt.getText());
            dtm = new DefaultTableModel(n,n);
            tab = new JTable(dtm);
            for(int i=0; i < n; i++){
                for(int j=0; j < n; j++){
                    if (i==j)
                        dtm.setValueAt(0,i,j);
                }
            }
            pan2.add(tab);
            graph = new int[n][n];


            pan3.updateUI();
            but = new JButton("ENTER");
            but.addActionListener(this);
            pan3.add(but);
        }
        if (e.getSource()==but2){
            pan2.updateUI();

            n = Integer.valueOf(txt.getText());
            dtm = new DefaultTableModel(n,n);
            tab = new JTable(dtm);
            rand = new int[n][n];
            for(int i=0; i < n; i++){
                for(int j=0; j < n; j++){
                    rand[i][j] = (int)(Math.random()*10);
                            dtm.setValueAt(rand[i][j], i, j);
                    if (i==j)
                        dtm.setValueAt(0,i,j);
                }
            }
            pan2.add(tab);


            pan3.updateUI();
            but4 = new JButton("ENTER");
            but4.addActionListener(this);
            pan3.add(but4);
        }
        if (e.getSource()==but4){
            n = Integer.valueOf(txt.getText());

            for (int i = 0; i < n; i++){
                for (int j =0; j < n; j++){
                    String [][] f = new String[n][n];
                    f [i][j] = dtm.getValueAt(i,j).toString();
                    rand[i][j] = Integer.parseInt(f[i][j]);
                }
            }
            dijkstra(rand,0);

            but1 = new JButton("           NEXT           ");
            but1.addActionListener(this);

            but5 = new JButton("           PREV           ");
            but5.addActionListener(this);
            pan5.add(but1);
            pan5.add(but5);
            pan5.updateUI();
        }
        if (e.getSource()==but){
            n = Integer.valueOf(txt.getText());

            for (int i = 0; i < n; i++){
                for (int j =0; j < n; j++){
                    String [][] f = new String[n][n];
                    f [i][j] = dtm.getValueAt(i,j).toString();
                    graph[i][j] = Integer.parseInt(f[i][j]);
                }
            }
            dijkstra(graph,0);

            but1 = new JButton("           NEXT           ");
            but1.addActionListener(this);
            but5 = new JButton("           PREV           ");
            but5.addActionListener(this);

            pan5.add(but1);
            pan5.add(but5);
            pan5.updateUI();
        }
        if (e.getSource()==but1){
            click++;
            codes(click);
        }
        if (e.getSource()==but5){
            click--;
            codes (click);
        }
    }

    public void dijkstra(int[][] graph, int sourceVertex){
        int vertexCount = graph.length;
        boolean[] visitedVertex = new boolean[vertexCount];
        int[] distance = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++){
            visitedVertex[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }
        distance[sourceVertex] = 0; // distance of source vertex to itself is zero
        for (int i = 0; i < vertexCount; i++){
            int u = findMinDistance(distance, visitedVertex);
            //u is the row and v is the column
            visitedVertex[u] = true;
            //now update all the neighbour vertex distances
            for (int v =0 ; v < vertexCount; v++){
                //graph[u][v] != 0 -> there should be a direct edge
                if(!visitedVertex[v] && graph[u][v] != 0 && (distance[u] + graph[u][v] < distance[v])){
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }
        for (int i = 0; i < distance.length; i++){
            txt1[distance.length] = new JTextField();
            txt1[distance.length].setText("Distance from source vertex " + (sourceVertex) + " to " + (i) + " is " + (distance[i]));
            txt1[distance.length].setEditable(false);
            txt1[distance.length].setPreferredSize(new Dimension(300,100));
            pan4.updateUI();
            pan4.add(txt1[distance.length]);
            System.out.println(String.format("Distance from source vertex %s to vertex %s is %s", sourceVertex, i, distance[i]));
        }

    }
    private int findMinDistance(int[] distance, boolean[] visitedVertex) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceVertex = -1;
        for (int i =0; i < distance.length; i++){
            //the vertex should not be visited and the distance should be the minimum.
            //this is similar to finding the min element of an array
            if(!visitedVertex[i] && distance[i] < minDistance){
                minDistance = distance[i];
                minDistanceVertex = i;
            }
        }
        return minDistanceVertex;
    }
    public void codes (int click){
        if (click == 1){
                area = new JTextArea();
                area.setText("STEP 1 \n" +
                        " Initialize distances from source vertex.\n" +
                        "In this program, we set it to 0.");
                area.setEditable(false);
                pan5.add(area);
                pan5.updateUI();
        }
        if (click == 2){
            area = new JTextArea();
            area.setText("SECOND STEP \n" +
                    "Pick first node and calculate distances\n" +
                    " to adjacent nodes.");

            area.setEditable(false);
            pan5.updateUI();
            pan5.add(area);
        }
        if (click == 3){
            area = new JTextArea();
            area.setText("THIRD STEP \n" +
                    " Pick node with minimal distance and\n " +
                    " repeat its calculations.");
            area.setEditable(false);
            pan5.updateUI();
            pan5.add(area);
        }
        if (click == 4){
            area = new JTextArea();
            area.setText("FOURTH STEP \n" +
                    "Final result of shortest distance.");
            area.setEditable(false);
            pan5.updateUI();
            pan5.add(area);
        }
    }
}
