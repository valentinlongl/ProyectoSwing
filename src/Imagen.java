import javax.swing.*;
import java.awt.*;

public class Imagen extends JPanel {

    public Imagen(){
        this.setSize(30, 30);
    }

    public void paint(Graphics grafico) {
        Dimension height = getSize();

        ImageIcon Img = new ImageIcon(getClass().getResource("/IconoPersonas30_30.jpg"));

        grafico.drawImage(Img.getImage(), 0, 0, 30, 30, null);

        setOpaque(false);
        super.paintComponent(grafico);
    }
}
