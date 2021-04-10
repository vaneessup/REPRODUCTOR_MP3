/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Map;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import mp3.prueba4.Music;

/**
 *
 * @author vanes
 */
public class player implements BasicPlayerListener {
    
    BasicPlayer player = new BasicPlayer();
    public BasicController control = (BasicController) player;
    float[] equalizador;
    float[] eq = new float[32]; 
    Music mp;

    public player(Music music) {
        player.addBasicPlayerListener(this);
        mp = music;
    }

    @Override
    public void opened(Object o, Map properties) {
        //System.out.println("opened : " + properties.toString());
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map properties) {
        equalizador = (float[]) properties.get("mp3.equalizer");
        System.arraycopy(eq, 0, equalizador, 0, equalizador.length);
        //System.out.println("progress : " + properties.toString()); 
    }

    @Override
    public void stateUpdated(BasicPlayerEvent event) {
        //System.out.println("stateUpdated : " + event.toString()); 
        if (player.getStatus() == BasicPlayer.STOPPED && mp.stop == false) {
            mp.eventoSiguiente();
        }
    }

    @Override
    public void setController(BasicController controller) {
        //System.out.println("setController : " + controller); 
    }
    
}
