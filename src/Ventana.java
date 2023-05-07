import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Random;

public class Ventana extends JFrame {

    int registrosAleatoriosPorAccion = 10000;

    private Persona[] personas = new Persona[100000];

    private JTable tbl_mostrar_personas;

    private String[] cabezalTabla = {"ID persona", "Nombre", "Apellido", "Residencia", "Cantidad de Hijos", "Fecha de Nacimiento"};

    private JComboBox cmb_listar_filtro;

    public Ventana(int w, int h) {
        super("Registro de Personas");
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        for (int i = 0; i < personas.length; i++) {
            personas[i] = new Persona();
        }

        JTabbedPane panel_tabs = new JTabbedPane();
        panel_tabs.setVisible(true);

        JPanel panel_agregar = new JPanel(new BorderLayout());
        panel_agregar.setVisible(true);

        //region panel_agregar elements
        JPanel panel_agregar_menu_superior = new JPanel(new BorderLayout());

        JPanel panel_agregar_centro = new JPanel(new GridLayout(5, 2));

        JPanel panel_agregar_menu_inferior = new JPanel(new BorderLayout());

        JLabel lbl_agregar_nombre = new JLabel("Nombre");
        lbl_agregar_nombre.setVisible(true);
        JTextField txt_agregar_nombre = new JTextField();
        txt_agregar_nombre.setVisible(true);

        JLabel lbl_agregar_apellido = new JLabel("Apellido");
        lbl_agregar_apellido.setVisible(true);
        JTextField txt_agregar_apellido = new JTextField();
        txt_agregar_apellido.setVisible(true);

        JLabel lbl_agregar_residencia = new JLabel("Residencia");
        lbl_agregar_residencia.setVisible(true);
        JTextField txt_agregar_residencia = new JTextField();
        txt_agregar_residencia.setVisible(true);

        JLabel lbl_agregar_cant_hijos = new JLabel("Cantidad de hijos");
        lbl_agregar_cant_hijos.setVisible(true);
        JTextField txt_agregar_cant_hijos = new JTextField();
        txt_agregar_cant_hijos.setVisible(true);

        JLabel lbl_agregar_fec_nacimiento = new JLabel("Fecha de nacimiento (DD/MM/YYYY)");
        lbl_agregar_fec_nacimiento.setVisible(true);
        JTextField txt_agregar_fec_nacimiento = new JTextField();
        txt_agregar_fec_nacimiento.setVisible(true);


        JButton btn_agregar_registrar = new JButton();
        btn_agregar_registrar.setText("Guardar registro");
        btn_agregar_registrar.setVisible(true);
        btn_agregar_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean correct = agregarRegistro(txt_agregar_nombre.getText(), txt_agregar_apellido.getText(), txt_agregar_residencia.getText(), txt_agregar_cant_hijos.getText(), txt_agregar_fec_nacimiento.getText());

                if (correct) {
                    JOptionPane.showMessageDialog(null, "Persona ingresada correctamente", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
                    txt_agregar_nombre.setText("");
                    txt_agregar_apellido.setText("");
                    txt_agregar_residencia.setText("");
                    txt_agregar_cant_hijos.setText("");
                    txt_agregar_fec_nacimiento.setText("");
                }
            }
        });

        JButton btn_agregar_aleatorios = new JButton();
        btn_agregar_aleatorios.setText("Generar registros aleatorios");
        btn_agregar_aleatorios.setVisible(true);
        btn_agregar_aleatorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarAleatorios(registrosAleatoriosPorAccion);
            }
        });

        Imagen logo_agregar = new Imagen();
        logo_agregar.setPreferredSize(new Dimension(30, 30));

        //endregion

        JPanel panel_listar = new JPanel(new BorderLayout());
        panel_listar.setVisible(true);

        //region panel_listar elements
        JPanel panel_listar_menu_superior = new JPanel(new GridLayout(1, 4));

        JLabel lbl_listar_filtro = new JLabel("Filtrar por:");
        lbl_listar_filtro.setVisible(true);

        JTextField txt_listar_filtro = new JTextField();
        txt_listar_filtro.setVisible(true);

        cmb_listar_filtro = new JComboBox<>(new String[]{"Id", "Nombre", "Apellido", "Residencia", "Cantidad de Hijos", "Fecha de Nacimiento"});
        cmb_listar_filtro.setVisible(true);

        JButton btn_listar_update = new JButton();
        btn_listar_update.setText("Actualizar");
        btn_listar_update.setVisible(true);
        btn_listar_update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txt_listar_filtro.getText().length() > 0) {
                    filterUpdateTable(txt_listar_filtro.getText());
                } else {
                    updateTable();
                }
            }
        });

        tbl_mostrar_personas = new JTable(0, 6);
        tbl_mostrar_personas.setVisible(true);
        DefaultTableModel tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
        tbl_model.addRow(cabezalTabla);

        JScrollPane spanel_listar_centro = new JScrollPane(tbl_mostrar_personas);

        Imagen logo_listar = new Imagen();
        logo_listar.setPreferredSize(new Dimension(30, 30));

        //endregion

        JPanel panel_eliminar = new JPanel(new BorderLayout());
        panel_eliminar.setVisible(true);

        //region panel_eliminar elements
        JPanel panel_eliminar_menu_superior = new JPanel(new GridLayout(1, 4));

        JLabel lbl_eliminar_id = new JLabel();
        lbl_eliminar_id.setText("Id del registro a eliminar:");

        JTextField txt_eliminar_id = new JTextField();

        JButton btn_eliminar_eliminarRegistro = new JButton();
        btn_eliminar_eliminarRegistro.setText("ELIMINAR");
        btn_eliminar_eliminarRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean correct = eliminarRegistro(txt_eliminar_id.getText());

                if (correct){
                    txt_eliminar_id.setText("");
                }
            }
        });

        Imagen logo_eliminar = new Imagen();
        logo_listar.setPreferredSize(new Dimension(30, 30));
        //endregion

        //region add elements to containers

        //add to panel_principal
        add(panel_tabs);

        //add to panel_agregar_menu:superior
        panel_agregar_menu_superior.add(logo_agregar, BorderLayout.LINE_START);

        //add to panel_agregar_centro
        panel_agregar_centro.add(lbl_agregar_nombre);
        panel_agregar_centro.add(txt_agregar_nombre);
        panel_agregar_centro.add(lbl_agregar_apellido);
        panel_agregar_centro.add(txt_agregar_apellido);
        panel_agregar_centro.add(lbl_agregar_residencia);
        panel_agregar_centro.add(txt_agregar_residencia);
        panel_agregar_centro.add(lbl_agregar_cant_hijos);
        panel_agregar_centro.add(txt_agregar_cant_hijos);
        panel_agregar_centro.add(lbl_agregar_fec_nacimiento);
        panel_agregar_centro.add(txt_agregar_fec_nacimiento);

        //add to panel_agregar_menu_inferior
        panel_agregar_menu_inferior.add(btn_agregar_aleatorios, BorderLayout.LINE_START);
        panel_agregar_menu_inferior.add(btn_agregar_registrar, BorderLayout.LINE_END);

        //add to panel_agregar
        panel_agregar.add(panel_agregar_menu_superior, BorderLayout.NORTH);
        panel_agregar.add(panel_agregar_centro, BorderLayout.CENTER);
        panel_agregar.add(panel_agregar_menu_inferior, BorderLayout.SOUTH);


        //add to panel_listar_menu_superior
        panel_listar_menu_superior.add(logo_listar);
        panel_listar_menu_superior.add(lbl_listar_filtro);
        panel_listar_menu_superior.add(cmb_listar_filtro);
        panel_listar_menu_superior.add(txt_listar_filtro);
        panel_listar_menu_superior.add(btn_listar_update);

        //add to panel_listar
        panel_listar.add(panel_listar_menu_superior, BorderLayout.NORTH);
        panel_listar.add(spanel_listar_centro, BorderLayout.CENTER);

        //add to panel_eliminar_menu_superior
        panel_eliminar_menu_superior.add(logo_eliminar);
        panel_eliminar_menu_superior.add(lbl_eliminar_id);
        panel_eliminar_menu_superior.add(txt_eliminar_id);
        panel_eliminar_menu_superior.add(btn_eliminar_eliminarRegistro);


        //add to panel_eliminar
        panel_eliminar.add(panel_eliminar_menu_superior, BorderLayout.NORTH);

        //add to panel_tabs
        panel_tabs.addTab("Agregar Persona", panel_agregar);
        panel_tabs.addTab("Listar Personas", panel_listar);
        panel_tabs.addTab("Eliminar Registro", panel_eliminar);


        //endregion

    }

    public boolean eliminarRegistro(String id){
        int numId = -1;

        if (id.equals("*")){
            for (int i = 0; i < personas.length; i++) {
                personas[i] = new Persona();

            }
            JOptionPane.showMessageDialog(null, "Se han eliminado correctamente todos los registros", "Registros Eliminados", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        try {

            numId = Integer.valueOf(id);
            for (int i = 0; i < personas.length; i++) {
                if (personas[i].idPersona == numId){
                    personas[i] = new Persona();
                    JOptionPane.showMessageDialog(null, "Se eliminó correctamente el registro con id " + numId, "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }
            }
            JOptionPane.showMessageDialog(null, "No se encontró un registro con el id indicado", "Error", JOptionPane.ERROR_MESSAGE);
        }catch (NumberFormatException err){
            JOptionPane.showMessageDialog(null, "Id debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean agregarRegistro(String nombre, String apellido, String residencia, String cant_hijos, String fec_nacimiento){

        for (int i = 0; i < personas.length; i++) {
            if (personas[i].idPersona < 0){
                try {
                    if (nombre.length() == 0 || apellido.length() == 0 || residencia.length() == 0 || cant_hijos.length() == 0 || fec_nacimiento.length() == 0){
                        throw new DataInputException("Debe rellenar todos los campos");
                    } else {
                        int[] inputDate = splitDate(fec_nacimiento);
                        personas[i] = new Persona(i, nombre, apellido, residencia, Byte.valueOf(cant_hijos), LocalDate.of(inputDate[0], inputDate[1], inputDate[2]));
                        return true;
                    }

                } catch (DataInputException err){
                    JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                } catch (NumberFormatException err){
                    JOptionPane.showMessageDialog(null, "El dato Cantidad de hijos debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);

                }
                break;
            }
        }
        return false;
    }

    public String generarFecha(int hasta, Random generador){
        int año = LocalDate.now().getYear() - generador.nextInt(hasta);
        int mes = generador.nextInt(12) + 1;

        boolean bisiesto = año % 4 == 0 && (!(año % 100 == 0) || año % 400 == 0);

        int maxDay = 0;
        switch (mes){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                maxDay = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDay = 30;
                break;
            case 2:
                if (bisiesto){
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
                break;
        }

        int dia = generador.nextInt(maxDay) +1;
        String fecha = dia + "/" + mes + "/" + año;
        return fecha;
    }

    public void generarAleatorios(int n){

        Random generador = new Random();

        //region randomLists
        String[] lst_nombres = new String[] {
                "Valentin",
                "Magdalena",
                "Santiago",
                "Paula",
                "Magali"
        };

        String[] lst_apellidos = new String[] {
                "Long",
                "Cairo",
                "Aboal",
                "Salgado",
                "Silva"
        };

        String[] lst_residencias = new String[] {
                "Dolores",
                "Mercedes",
                "Carmelo",
                "Nueva Palmira",
                "Fray Bentos",
                "Colonia",
                "Cardona",
                "Montevideo",
                "Minas",
                "Melo",
                "Paysandu"
        };
        //endregion

        for (int i = 0; i < n; i++) {
            String randomNombre = lst_nombres[generador.nextInt(lst_nombres.length)];
            String randomApellido = lst_apellidos[generador.nextInt(lst_apellidos.length)];
            String randomResidencia = lst_residencias[generador.nextInt(lst_residencias.length)];
            String randomCantHijos = String.valueOf(generador.nextInt(5));
            String randomFechaNac = generarFecha(50, generador);

            agregarRegistro(randomNombre, randomApellido, randomResidencia, randomCantHijos, randomFechaNac);
        }
    }

    public int[] splitDate(String date) throws DataInputException {

        String[] splitInput = date.split("/");

        if (splitInput.length != 3){
            throw new DataInputException("La fecha tiene que ser en formato DD/MM/YYYY");
        }

        int[] output = new int[3];
        output[0] = Integer.valueOf(splitInput[2]);
        output[1] = Integer.valueOf(splitInput[1]);
        output[2] = Integer.valueOf(splitInput[0]);

        boolean bisiesto = output[0] % 4 == 0 && (!(output[0] % 100 == 0) || output[0] % 400 == 0);
        int maxDay = 0;

        if(output[1] > 12 || output[1] < 1){
            throw new DataInputException("El mes ingresado esta fuera del rango permitido (1-12). Valor introducido: " + output[1]);
        }

        switch (output[1]){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                maxDay = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDay = 30;
                break;
            case 2:
                if (bisiesto){
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
                break;
        }

        if (output[2] < 1 || output[2] > maxDay){
            throw new DataInputException("El día ingresado esta fuera del rango permitido (1-" + maxDay +"). Valor ntroducido: " + output[2]);
        }
        return output;
    }

    public void updateTable(){
        DefaultTableModel tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
        tbl_model.setRowCount(0);
        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
        tbl_model.addRow(cabezalTabla);
        for (int i = 0; i < personas.length; i++) {
            if (personas[i].idPersona >= 0){
                tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();

                String[] datosPersona =
                        {String.valueOf(personas[i].idPersona),
                                personas[i].nombre,
                                personas[i].apellido,
                                personas[i].residencia,
                                String.valueOf(personas[i].getCantHijos()),
                                personas[i].getFechaNacimiento().getDayOfMonth() + "/" + personas[i].getFechaNacimiento().getMonthValue() + "/" + personas[i].getFechaNacimiento().getYear()};

                tbl_model.addRow(datosPersona);
            }
        }
    }

    public void filterUpdateTable(String filter){
        DefaultTableModel tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
        tbl_model.setRowCount(0);
        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
        tbl_model.addRow(cabezalTabla);

        int numFilter;

        switch (cmb_listar_filtro.getSelectedIndex()){

            case 0:
                try {
                    numFilter = Integer.valueOf(filter);
                }catch (NumberFormatException err){
                    JOptionPane.showMessageDialog(null, "El filtro para Id debe ser un número", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                for (int i = 0; i < personas.length; i++) {
                    if (personas[i].idPersona >= 0 && personas[i].idPersona == Integer.valueOf(numFilter)){
                        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();

                        String[] datosPersona =
                                {String.valueOf(personas[i].idPersona),
                                        personas[i].nombre,
                                        personas[i].apellido,
                                        personas[i].residencia,
                                        String.valueOf(personas[i].getCantHijos()),
                                        personas[i].getFechaNacimiento().getDayOfMonth() + "/" + personas[i].getFechaNacimiento().getMonthValue() + "/" + personas[i].getFechaNacimiento().getYear()};

                        tbl_model.addRow(datosPersona);
                    }
                }
                break;

            case 1:
                for (int i = 0; i < personas.length; i++) {
                    String nombreMinusculas = "";
                    if (personas[i].idPersona >= 0 ){
                        nombreMinusculas = personas[i].nombre.toLowerCase();
                    }

                    if (personas[i].idPersona >= 0 && nombreMinusculas.equals(filter.toLowerCase())){
                        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
                        String[] datosPersona =
                                {String.valueOf(personas[i].idPersona),
                                        personas[i].nombre,
                                        personas[i].apellido,
                                        personas[i].residencia,
                                        String.valueOf(personas[i].getCantHijos()),
                                        personas[i].getFechaNacimiento().getDayOfMonth() + "/" + personas[i].getFechaNacimiento().getMonthValue() + "/" + personas[i].getFechaNacimiento().getYear()};

                        tbl_model.addRow(datosPersona);
                    }
                }
                break;

            case 2:
                for (int i = 0; i < personas.length; i++) {
                    String apellidoMinusculas = "";
                    if (personas[i].idPersona >= 0 ){
                        apellidoMinusculas = personas[i].apellido.toLowerCase();
                    }

                    if (personas[i].idPersona >= 0 && apellidoMinusculas.equals(filter.toLowerCase())){
                        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
                        String[] datosPersona =
                                {String.valueOf(personas[i].idPersona),
                                        personas[i].nombre,
                                        personas[i].apellido,
                                        personas[i].residencia,
                                        String.valueOf(personas[i].getCantHijos()),
                                        personas[i].getFechaNacimiento().getDayOfMonth() + "/" + personas[i].getFechaNacimiento().getMonthValue() + "/" + personas[i].getFechaNacimiento().getYear()};

                        tbl_model.addRow(datosPersona);
                    }
                }
                break;

            case 3:
                for (int i = 0; i < personas.length; i++) {
                    String residenciaMinusculas = "";
                    if (personas[i].idPersona >= 0 ){
                        residenciaMinusculas = personas[i].residencia.toLowerCase();
                    }

                    if (personas[i].idPersona >= 0 && residenciaMinusculas.equals(filter.toLowerCase())){
                        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();
                        String[] datosPersona =
                                {String.valueOf(personas[i].idPersona),
                                        personas[i].nombre,
                                        personas[i].apellido,
                                        personas[i].residencia,
                                        String.valueOf(personas[i].getCantHijos()),
                                        personas[i].getFechaNacimiento().getDayOfMonth() + "/" + personas[i].getFechaNacimiento().getMonthValue() + "/" + personas[i].getFechaNacimiento().getYear()};

                        tbl_model.addRow(datosPersona);
                    }
                }
                break;

            case 4:
                try {
                    numFilter = Integer.valueOf(filter);
                }catch (NumberFormatException err){
                    JOptionPane.showMessageDialog(null, "El filtro para cantidad de hijos tiene que ser un númeC+C-ro", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                for (int i = 0; i < personas.length; i++) {
                    if (personas[i].idPersona >= 0 && personas[i].getCantHijos() == Integer.valueOf(numFilter)){
                        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();

                        String[] datosPersona =
                                {String.valueOf(personas[i].idPersona),
                                        personas[i].nombre,
                                        personas[i].apellido,
                                        personas[i].residencia,
                                        String.valueOf(personas[i].getCantHijos()),
                                        personas[i].getFechaNacimiento().getDayOfMonth() + "/" + personas[i].getFechaNacimiento().getMonthValue() + "/" + personas[i].getFechaNacimiento().getYear()};

                        tbl_model.addRow(datosPersona);
                    }
                }
                break;

            case 5:
                LocalDate dateFilter;
                try {
                    int[] filterDate = splitDate(filter);
                    dateFilter = LocalDate.of(filterDate[0], filterDate[1], filterDate[2]);
                }catch (DataInputException err){
                    JOptionPane.showMessageDialog(null, "El filtro para Fecha es incorrecto: " + err.getMessage(), "Ingreso", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                for (int i = 0; i < personas.length; i++) {
                    if (personas[i].idPersona >= 0 && personas[i].getFechaNacimiento().equals(dateFilter)){
                        tbl_model = (DefaultTableModel) this.tbl_mostrar_personas.getModel();

                        String[] datosPersona =
                                {String.valueOf(personas[i].idPersona),
                                        personas[i].nombre,
                                        personas[i].apellido,
                                        personas[i].residencia,
                                        String.valueOf(personas[i].getCantHijos()),
                                        personas[i].getFechaNacimiento().getDayOfMonth() + "/" + personas[i].getFechaNacimiento().getMonthValue() + "/" + personas[i].getFechaNacimiento().getYear()};

                        tbl_model.addRow(datosPersona);
                    }
                }

        }
    }
}