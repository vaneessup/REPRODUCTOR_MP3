/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp3.prueba4;

import clases.Nodo;
import clases.listaOrdenada;
import clases.player;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author vanes
 */
public class Music extends javax.swing.JFrame {//interfaz grafica swing 
    
    private listaOrdenada list = new listaOrdenada();
    private Nodo actual = null;
    private player player;
    private int tam = 0;
    private DefaultListModel lista_modelo = new DefaultListModel();
    private String ultimaLista = "vacio";
    private boolean cambios = false;
    public boolean stop = false;
    
    public Music() {
        initComponents();//para inicializar todos los componentes graficos de mi jframe
        setTitle("MUSICA");
        setResizable(false);
        setLocationRelativeTo(null);//para que mi jframe me aparezca en el centro
        nameSong.setEditable(false);//para que mi textField se le pueda modificar el texto 
        volumen1.setEnabled(false);//para activar a desactivar mi jslider

        listado.addMouseListener(new MouseAdapter() {//para registrarsi el cursor entro en el list
            
            public void click(MouseEvent evt) {//metodo para el momento de hacer doble click+
             //en una cancion del listado, esta se reproduzca
                JList lista = (JList) evt.getSource();//creando una lista tipo JList para que nos permita
                //poder elegir un elemento de nuestra lista
                if (evt.getClickCount() == 2) {
                    int cancion = lista.locationToIndex(evt.getPoint());
                    if (cancion != -1) {
                        actual = list.get_cancion(cancion);
                        tam = 0;
                        btnPlayActionPerformed(null);
                    }
                }
            }
        });

        try {
            //para abrir el archivo en donde esta la musica
            BufferedReader archivo = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\config"));
            String aux = archivo.readLine();
            if (aux.equals("Si")) {
                aux = archivo.readLine();
                if (!aux.equals("vacio")) {
                    LlamarLista(aux);
                }
            } else {
                CargarLista.setSelected(false);
            }
        } catch (IOException e) {
        }

        addWindowListener(new java.awt.event.WindowAdapter() {//para poder agregar un escuchador a la ventana
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {//metodo para generar el evento al querer
                //cerrar una ventana
                if (!list.vacia()&& cambios) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                    if (opcion == JOptionPane.YES_OPTION) {
                        if (ultimaLista.equals("vacio")) {
                            ultimaLista = crearArchivoLista();
                        }
                        if (ultimaLista == null) {
                            ultimaLista = "vacio";
                        } else {
                            guardarLista(ultimaLista);
                        }
                    }
                }
                try {
                    BufferedWriter bw = new BufferedWriter(
                            new FileWriter(System.getProperty("user.dir") + "\\config"));
                    if (CargarLista.isSelected()) {
                        bw.write("Si\r\n");
                        bw.write(ultimaLista + "\r\n");
                    } else {
                        bw.write("No\r\n");
                    }
                    bw.close();
                } catch (Exception e) {
                }
                System.exit(0);
            }
        });
        player = new player(this);
        
        
    }
    
    public void guardarLista(String dir) {//para generar un archivo tipo lis y poder abrir
        
        try {
            BufferedWriter tec = new BufferedWriter(new FileWriter(dir));
            tec.write("\r\n");

            Nodo aux;
            aux = list.primero;
            while (aux != null) {
                tec.append(aux.dato + "<" + aux.direccion + "\r\n");
                aux = aux.siguiente;
            }

            tec.close();
            cambios = false;
        } catch (Exception e) {
        }
    }
    
    public String crearArchivoLista() {//para crear el archivo tipo lis
        String n = JOptionPane.showInputDialog("Escriba el nombre de la lista");
        if (n == null || n.isEmpty()) {//si no escribe nada le dira que hay error
            return null;
        }
        
        JFileChooser chooser = new JFileChooser();//para abrir una ventana al momento de seleccionar un fichero
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//para ubicar y seleccionar el archivo
        int seleccion = chooser.showOpenDialog(this);
        File ruta;

        if (seleccion == JFileChooser.APPROVE_OPTION) {//para aprobar seleccion
            ruta = chooser.getSelectedFile();
        } else {
            return null;
        }
        File save = new File(ruta.getAbsolutePath() + "\\" + n + ".lis");//CREAR UN ARCHIVO .LIS PARA GUARDAR LISTA
        if (save.exists()) {
            save.delete();
        }
        return save.getAbsolutePath();
    }
    public void LlamarLista(String ruta) {//para cargar la lista
        //guardada anteriormente
        try {
            FileInputStream rut = new FileInputStream(new File(ruta));
            BufferedReader archivo = new BufferedReader(new InputStreamReader(rut, "UTF-8"));
            String input[];
            archivo.readLine();

            while (archivo.ready()) {
                input = archivo.readLine().split("<");
                System.out.println(input[0] + " , " + input[1]);
                list.insertar(input[0], input[1]);
                lista_modelo.addElement(input[0]);
            }
            ultimaLista = ruta;
            cambios = false;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error\nal cargar la lista!!!", "alerta", 1);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error!!!", "alerta", 1);
        }
        listado.setModel(lista_modelo);
    }

    

    
    
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        gif = new javax.swing.JLabel();
        gif1 = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        siguiente = new javax.swing.JButton();
        anterior1 = new javax.swing.JButton();
        gif2 = new javax.swing.JLabel();
        nameSong = new javax.swing.JTextField();
        volumen1 = new javax.swing.JSlider();
        volumen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listado = new javax.swing.JList<>();
        reproduccion = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        save = new javax.swing.JMenuItem();
        salir = new javax.swing.JMenuItem();
        CargarLista = new javax.swing.JMenuItem();
        menu = new javax.swing.JMenu();
        AgregarCancion = new javax.swing.JMenuItem();
        EliminarCancion = new javax.swing.JMenuItem();
        detener = new javax.swing.JMenuItem();

        jSlider1.setToolTipText("");
        jSlider1.setValue(25);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosMP3/play.png"))); // NOI18N
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosMP3/proximo.png"))); // NOI18N
        siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteActionPerformed(evt);
            }
        });

        anterior1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosMP3/anterior.png"))); // NOI18N
        anterior1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anterior1ActionPerformed(evt);
            }
        });

        gif2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosMP3/ondas.gif"))); // NOI18N

        nameSong.setText("...");
        nameSong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameSongActionPerformed(evt);
            }
        });

        volumen1.setToolTipText("");
        volumen1.setValue(25);
        volumen1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volumen1StateChanged(evt);
            }
        });

        volumen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosMP3/volumen.png"))); // NOI18N

        jScrollPane1.setViewportView(listado);

        reproduccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Inversa", "Aleatoria" }));

        jMenu1.setText("File");

        save.setText("Guardar Lista");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jMenu1.add(save);

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        jMenu1.add(salir);

        CargarLista.setText("Cargar Lista");
        CargarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarListaActionPerformed(evt);
            }
        });
        jMenu1.add(CargarLista);

        jMenuBar1.add(jMenu1);

        menu.setText("Menu");
        menu.setActionCommand("edit");

        AgregarCancion.setText("Agregar Cancion");
        AgregarCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarCancionActionPerformed(evt);
            }
        });
        menu.add(AgregarCancion);

        EliminarCancion.setText("Eliminar Cancion");
        EliminarCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarCancionActionPerformed(evt);
            }
        });
        menu.add(EliminarCancion);

        detener.setText("Detener Reproduccion");
        detener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detenerActionPerformed(evt);
            }
        });
        menu.add(detener);

        jMenuBar1.add(menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(nameSong, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(gif2)))
                .addGap(0, 53, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(volumen))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(reproduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(volumen1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(anterior1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(84, 84, 84)
                    .addComponent(gif)
                    .addContainerGap(336, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(84, 84, 84)
                    .addComponent(gif1)
                    .addContainerGap(336, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(gif2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameSong, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(siguiente)
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(reproduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(anterior1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(volumen1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(volumen, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(gif, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(276, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(gif1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(276, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        stop = true;
        if (list.vacia()) {
            JOptionPane.showMessageDialog(null, "Lista de Reproduccion Vacia", "ALERTA", 1);
        } else {
            if (actual == null) {
                actual = list.primero;
            }
            try {
                if (tam == 0) {
                    
                    player.control.open(new URL("file:///" + actual.direccion));
                    player.control.play();
                    System.out.println("Reproducciendo");
                    nameSong.setText(actual.dato);
                    volumen1.setEnabled(true);
                    tam = 1;
                    btnPlay.setIcon(new ImageIcon(getClass().getResource("/iconosMP3/pausa.png")));
                } else {
                    if (tam == 1) {
                        player.control.pause();
                        System.out.println("En pausa");
                        tam = 2;
                        btnPlay.setIcon(new ImageIcon(getClass().getResource("/iconosMP3/play.png")));
                    } else {
                        player.control.resume();
                        System.out.println("CONTINUAR");
                        tam = 1;
                        btnPlay.setIcon(new ImageIcon(getClass().getResource("/iconosMP3/pausa.png")));
                    }
                }
            } catch (BasicPlayerException ex) {
                JOptionPane.showMessageDialog(null, "ERRO CON LA CANCION", "ALERTA", 1);
                tam = 0;
            } catch (MalformedURLException ex) {
                Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "ERROR CON LA DIRECCION!", "ALERTA", 1);
                tam = 0;
            }
        }
        stop = false;
    }//GEN-LAST:event_btnPlayActionPerformed

    private void AgregarCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarCancionActionPerformed
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo MP3", "mp3", "mp3"));//para que
        //solo acepte archivos tipo mp3
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File files[] = fileChooser.getSelectedFiles();
            boolean noMp3 = false, repetidos = false;
            cambios = true;

            for (File file : files) {
                String name = file.getName();
                if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".mp3")) {
                    noMp3 = true;
                    continue;
                }
                if (list.buscar(file.getName(), file.getPath())) {
                    repetidos = true;
                    continue;
                }
                list.insertar(file.getName(), file.getPath());
                System.out.println(file.getName());
                System.out.println(file.getPath());
                lista_modelo.addElement(file.getName());
                listado.setModel(lista_modelo);
                
            }
            if (noMp3) {
                JOptionPane.showMessageDialog(null, "ARCHIVO NO VALIDO", "ALERTA", 0);
            }
            if (repetidos) {
                JOptionPane.showMessageDialog(null, "EXISTEN CANCIONES REPETIDAS", "ALERTA", 0);
            }
        }
        
    }//GEN-LAST:event_AgregarCancionActionPerformed
    
    
    private void anterior1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anterior1ActionPerformed
        if (actual == null) {
            return;
        }

        switch (reproduccion.getSelectedIndex()) {
            case 0:
                if (actual.anterior == null) {
                    return;
                }
                actual = actual.anterior;
                break;

            case 1:
                if (actual.siguiente == null) {
                    return;
                }
                actual = actual.siguiente;
                break;

            default:
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        tam = 0;
        btnPlayActionPerformed(evt);// TODO add your handling code here:
    }//GEN-LAST:event_anterior1ActionPerformed

    private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteActionPerformed

       if (actual == null) {
            return;
        }

        switch (reproduccion.getSelectedIndex()) {
            case 0:
                if (actual.siguiente == null) {
                    return;
                }
                actual = actual.siguiente;
                break;

            case 1:
                if (actual.anterior == null) {
                    return;
                }
                actual = actual.anterior;
                break;

            default:
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        tam = 0;
        btnPlayActionPerformed(evt);
    }//GEN-LAST:event_siguienteActionPerformed

    public void eventoSiguiente(){
        siguienteActionPerformed(null);
    }
    private void EliminarCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarCancionActionPerformed
        if (list.vacia()) {
            return;
        }
        int q = list.contar(actual);
        if (q == -1) {
            JOptionPane.showMessageDialog(null, "NO HAY CANCIONES EN LISTA", "ALLERTA", 1);
        } else {
            lista_modelo.remove(q);
            list.borrar(actual);
            detenerActionPerformed(evt);
            if (list.vacia()) {
                actual = null;
                nameSong.setText("...");
            } else {
                if (list.tam == 1) {
                    actual = list.primero;
                } else {
                    if (actual.siguiente == null) {
                        actual = actual.anterior;
                    } else {
                        actual = actual.siguiente;
                    }
                }
                nameSong.setText(actual.dato);
            }
        }
        cambios = true;        // TODO add your handling code here:
    }//GEN-LAST:event_EliminarCancionActionPerformed

    private void detenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detenerActionPerformed
           stop = true;
          btnPlay.setIcon(new ImageIcon(getClass().getResource("/iconosMP3/play.png")));
        try {
            player.control.stop();
            tam = 0;
            volumen1.setEnabled(false);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
        stop = false;        
    }//GEN-LAST:event_detenerActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        
        if (list.vacia()) {
            JOptionPane.showMessageDialog(null, "SU LISTA ESTA VACIA /n AGREGUE CANCIONES+"
                    + "PARA REPRODUCIR", "ATENCION", 1);
            return;
        }
        guardarLista(crearArchivoLista());
    }//GEN-LAST:event_saveActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

    private void volumen1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volumen1StateChanged
        try {
            player.control.setGain((double) volumen1.getValue() / 100);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_volumen1StateChanged

    private void nameSongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameSongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameSongActionPerformed

    private void CargarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarListaActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("archivo lis", "lis"));
        int seleccion = chooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            detenerActionPerformed(evt);
            list.clear();
            lista_modelo.clear();
            actual = list.primero;

            String name = chooser.getSelectedFile().getName();
            if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".lis")) {
                JOptionPane.showMessageDialog(null, "NO ES UNA LISTA", "ALERTA", 0);
                return;
            }
            LlamarLista(chooser.getSelectedFile().getPath());
        }// TODO add your handling code here:
    }//GEN-LAST:event_CargarListaActionPerformed
    
  
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Music().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AgregarCancion;
    private javax.swing.JMenuItem CargarLista;
    private javax.swing.JMenuItem EliminarCancion;
    private javax.swing.JButton anterior1;
    public javax.swing.JButton btnPlay;
    private javax.swing.JMenuItem detener;
    private javax.swing.JLabel gif;
    private javax.swing.JLabel gif1;
    private javax.swing.JLabel gif2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JList<String> listado;
    private javax.swing.JMenu menu;
    private javax.swing.JTextField nameSong;
    private javax.swing.JComboBox<String> reproduccion;
    private javax.swing.JMenuItem salir;
    private javax.swing.JMenuItem save;
    private javax.swing.JButton siguiente;
    private javax.swing.JLabel volumen;
    private javax.swing.JSlider volumen1;
    // End of variables declaration//GEN-END:variables
}
