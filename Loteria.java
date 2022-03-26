package Vistas;

import Nodos.ListaCircularDoble;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.concurrent.*;
import java.io.File;
import java.util.Stack;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Loteria extends Stage implements EventHandler {

    ImageView imVCartaTotal;

    boolean ganaste = false;

    boolean plantCompl = false;

    Stack arregloStacks [] = new Stack[5];

    ListaCircularDoble obj = new ListaCircularDoble();

    int estadoActual= 1;

    int contadorPuntos=0;

    private VBox vBox;                      // Declarar todo lo que ocupo
    public HBox hBox1, hBox2;
    Button btnAtras, btnSig, btnJugar;
   // Label lblTiempo;
    public GridPane gdpPlantilla;
    public Image imgCarta;
    private ImageView imVCarta;
    public Scene escena;
    public Image[] imgMazo = new Image[23];
    public ImageView[] imgCuadricula = new ImageView[16];

    public String[] arrImagenes = {"Arbol.jpg","Bandera.jpg","Bandolon.jpg","Barril.jpg", "Bota.jpg", "Botella.jpg", "Catrin.jpg", "Dama.jpg", "Diablo.jpg", "Escalera.jpg","Gallo.jpg", "Garza.jpg", "Gorrito.jpg", "Luna.jpg", "Mano.jpg", "Melon.jpg", "Muerte.jpg", "Pajaro.jpg", "Paraguas.jpg", "Pera.jpg", "Sirena.jpg", "Valienrte.jpg", "Violoncello.jpg"};

    public  Button [][] arrBtnPlantilla = new Button[4][4];         // Crea los botones del GridPane en donde irán las imágenes


    Stack <Integer> stackImgMain = new Stack<Integer>();
    Stack <Integer> stack2 = new Stack<Integer>();
    Stack <Integer> stack3 = new Stack<Integer>();
    Stack <Integer> stack4 = new Stack<Integer>();
    Stack <Integer> stack5 = new Stack<Integer>();

    public Loteria(){     // Constructor de la clase
        CrearUI();
        this.setTitle("Loteria");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {                          // Crear la Interfaz Gráfica

        btnAtras = new Button("ANTERIOR");           //Inicializar botones
        btnAtras.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnAtrasMetodo();
            }
        });
        btnAtras.setPrefWidth(100);
        btnAtras.setTextFill(Color.RED);

        btnSig = new Button("SIGUIENTE");
        btnSig.addEventHandler(MouseEvent.MOUSE_CLICKED,this );
        btnSig.setPrefWidth(100);
        btnSig.setTextFill(Color.BLUE);

      //  lblTiempo   = new Label("00:00");              //Inicializar etiquetas
        hBox1       = new HBox();
        hBox1.setSpacing(5);               //Le damos espacio
        hBox1.getChildren().addAll(btnAtras, btnSig);      // Le meto los elementos de arriba

        gdpPlantilla    = new GridPane();             //Cera el objeto GridPane
        CrearPlantilla(gdpPlantilla);

        File file1      = new File("src/main/java/CartaVolteada/cartaVolteada.jpg");          //Crear imagen de la derecha
        imgCarta        = new Image(file1.toURI().toString());
        imVCartaTotal        = new ImageView(imgCarta);

        imVCartaTotal.setFitHeight(200);            //Tamaños de la imagen
        imVCartaTotal.setFitWidth(150);

        hBox2 = new HBox();
        hBox2.setSpacing(5);
        hBox2.getChildren().addAll(gdpPlantilla, imVCartaTotal);                      //Le meto los elementos del medio

        obj.insertarInicio(1);
        obj.insertarEnPosicion(2, 2);
        obj.insertarEnPosicion(3, 3);
        obj.insertarEnPosicion(4, 4);
        obj.insertarEnPosicion(5, 5);

        btnJugar = new Button("Jugar");                                       // El elemento del fondo
        btnJugar.setPrefWidth(290);
        btnJugar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    jugar();
                } catch (InterruptedException e) {

                }
            }
        });

        vBox = new VBox();
        vBox.setPadding(new Insets(5));
        vBox.setSpacing(5);                           // Darle espacio
        vBox.getChildren().addAll(hBox1, hBox2, btnJugar);             // Meto los 3 grupos en el vBox

        escena = new Scene(vBox, 430, 360);
    }

    private void jugar() throws InterruptedException {
        btnAtras.setDisable(true);
        btnSig.setDisable(true);
        btnJugar.setDisable(true);
        GenRandom(stackImgMain, 23);
        run();
    }


    private void run() throws InterruptedException {
            rellenarArreglo();
            rellenarArregloCuad();
            funcBotones();             //Agrega funcionalidad a los botones
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1) ;
            final Runnable runnable =  new Runnable(){
                int CDST = 0;
              public void run(){
                  imVCartaTotal.setImage(imgMazo[CDST]);
                  imVCartaTotal.setId(imgMazo[CDST].getUrl());
                  System.out.println(CDST);
                  CDST++;
                  if(CDST>22){
                      System.out.println("Finalizó");
                      scheduler.shutdown();
                      resultado();

                  }
              }
            };
            scheduler.scheduleAtFixedRate(runnable,0,2,SECONDS);
    }

    private void resultado(){
        if (16 != contadorPuntos) {
            ganaste = false;
            System.out.println("Perdiste, intentalo de nuevo");
        } else {
            ganaste = true;
            System.out.println("Felicidades, ganaste");
        }
    }

    private void rellenarArreglo() {       // Rellena arreglo de las imagenes que vas a en el mazo
        for (int i = 0; i < 23; i++) {

            File file2;
            file2 = new File("src/main/java/Imagenes/" + arrImagenes[stackImgMain.get(i)]);        //Elegir la imagen que se va usar
            imgCarta = new Image(file2.toURI().toString());
            imgMazo[i]=imgCarta;                                                                          // ponerle esa imagen a un arreglo de imagenes
        }
    }

    private void rellenarArregloCuad() {       // Rellena arreglo de las imagenes que vas a en el mazo
        File file2 = null;
        for (int i = 0; i < 16; i++) {
            switch (estadoActual){
                case 1 :
                    file2 = new File("src/main/java/Imagenes/"+arrImagenes[pCartas.get(i)]);        //Elegir la imagen que se va usar
                    break;
                case 2:
                    file2 = new File("src/main/java/Imagenes/"+arrImagenes[stack2.get(i)]);        //Elegir la imagen que se va usar
                    break;
                case 3:
                    file2 = new File("src/main/java/Imagenes/"+arrImagenes[stack3.get(i)]);        //Elegir la imagen que se va usar
                    break;
                case 4:
                    file2 = new File("src/main/java/Imagenes/"+arrImagenes[stack4.get(i)]);        //Elegir la imagen que se va usar
                    break;
                case 5:
                    file2 = new File("src/main/java/Imagenes/"+arrImagenes[stack5.get(i)]);        //Elegir la imagen que se va usar
                    break;
                default:
            }
            imgCarta = new Image(file2.toURI().toString());
            ImageView imVCarta = new ImageView(imgCarta);
            imVCarta.setId(imgCarta.getUrl());
            imgCuadricula[i] = imVCarta;
        }
    }

    private void funcBotones(){
        int indice=0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int finalI = i;
                    int finalJ = j;
                    int finalIndice = indice;
                    arrBtnPlantilla[j][i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            ComparacionBotones(finalI, finalJ, finalIndice);
                        }
                    });
                    indice++;
                }
            }
        }

    private void ComparacionBotones(int i, int j, int indice) {
            if (imgCuadricula[indice].getId().equals(imVCartaTotal.getId())){
                System.out.println("Coinciden");
                File file1      = new File("src/main/java/CartaVolteada/cartaVolteada.jpg");          //Crear imagen de la derecha
                imgCarta        = new Image(file1.toURI().toString());
               ImageView imVCarta        = new ImageView(imgCarta);

               imVCarta.setFitHeight(60);
               imVCarta.setFitWidth(50);

                arrBtnPlantilla[j][i].setGraphic(imVCarta);
                contadorPuntos++;
            }
    }


    private void btnAtrasMetodo() {
            switch (estadoActual){
                case 1:
                    if (estadoActual == 1 && arregloStacks[4] != null) { // Regresa a la quinta plantilla
                        moverse(4);
                        estadoActual = 5;
                        break;
                    }
                    break;
                case 2:                      // Regresa a la primer plantilla
                    moverse(0);
                    estadoActual = 1;
                    break;

                case 3:                     // Regresa a la segunda plantilla
                    moverse(1);
                    estadoActual = 2;
                    break;

                case 4:                       // Regresa a la tercera plantilla
                    moverse(2);
                    estadoActual = 3;
                    break;

                case 5:                       // Regresa a la cuarta plantilla
                    moverse(3);
                    estadoActual = 4;
                    break;

            }

    }

    private Stack <Integer> pCartas = new Stack<Integer>();

    private void CrearPlantilla(GridPane plantilla) {                // Le damos un formato a la plantilla

        GenRandom(pCartas, 16);             //Llamamos al metodo

        int aux = 0;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                arrBtnPlantilla[j][i] = new Button();

                File file2 = new File("src/main/java/Imagenes/"+arrImagenes[pCartas.get(aux)]);        //Elegir la imagen que se va usar
                imgCarta = new Image(file2.toURI().toString());
                ImageView view = new ImageView(imgCarta);


                view.setFitHeight(60);                //Tamaño de imagenes
                view.setFitWidth(50);

                arrBtnPlantilla[j][i].setGraphic(view);              //Al botón le agregamos el ImageView
                plantilla.add(arrBtnPlantilla[j][i], i,j);         //Ponemos el botón en el GridPane con cierta coordenada

                arregloStacks[0]=pCartas;

                aux ++;
            }
        }
    }

    public Stack GenRandom(Stack s, int i) {
        int pos = 0;                                                        // Llenar el Stack pCartas de numeros aleatorios hasta el 23
        for (int k = 0; k < i; k++) {
            pos = (int) Math.floor(Math.random()*23);
            while (s.contains(pos)){
                pos = (int) Math.floor(Math.random()*23);
            }
            s.push(pos);
        }
        return s;
    }

    @Override
    public void handle(Event event) {
        GenPlantillas();
    }


    private void GenPlantillas(){
        switch (estadoActual){
            case 1 :              // Muestra segunda plantilla
                if (arregloStacks[1] == null){
                    Generar(stack2);
                    GuardarStack(stack2, 1);
                }
                else{
                    moverse(1);
                    estadoActual++;
                }
                break;

            case 2:                     // Muestra tercera plantilla
                if (arregloStacks[2] == null){
                    Generar(stack3);
                    GuardarStack(stack3, 2);
                }
                else {
                    moverse(2);
                    estadoActual++;
                }
                break;

            case 3 :                 // Muestra cuarta plantilla
                if (arregloStacks[3] == null){
                    Generar(stack4);
                    GuardarStack(stack4, 3);
                }
                else {
                    moverse(3);
                    estadoActual++;
                }
                break;

            case 4:                  // Muestra quinta plantilla
                if (arregloStacks[4] == null){
                    Generar(stack5);
                    GuardarStack(stack5, 4);
                }
                else{
                    moverse(4);
                    estadoActual++;
                }
                break;

            case 5:                   //Vuelve a la primera plantilla
                plantCompl = true;  // Sale del while
                moverse(0);
                estadoActual = 1;
                break;

        }
}

    private void moverse(int a) {
        int aux = 0;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                File file2 = new File("src/main/java/Imagenes/"+arrImagenes[(int) arregloStacks[a].get(aux)]);        //Elegir la imagen que se va usar
                imgCarta = new Image(file2.toURI().toString());
                ImageView view = new ImageView(imgCarta);


                view.setFitHeight(60);                //Tamaño de imagenes
                view.setFitWidth(50);

                arrBtnPlantilla[j][i].setGraphic(view);              //Al botón le agregamos el ImageView

                aux ++;
            }
        }
    }

    private void GuardarStack(Stack stack, int i) {
        arregloStacks[i] = stack;
    }

    void Generar(Stack stack){   // En base a un Stack, le carga numeros y carga imagenes en el Grid
            GenRandom(stack, 16);
            int aux = 0;
            for (int i = 0; i < 4; i++) {

                for (int j = 0; j < 4; j++) {

                    File file2 = new File("src/main/java/Imagenes/"+arrImagenes[(int) stack.get(aux)]);        //Elegir la imagen que se va usar
                    imgCarta = new Image(file2.toURI().toString());
                    ImageView view = new ImageView(imgCarta);


                    view.setFitHeight(60);                //Tamaño de imagenes
                    view.setFitWidth(50);

                    arrBtnPlantilla[j][i].setGraphic(view);              //Al botón le agregamos el ImageView

                    aux ++;
                }
            }
            estadoActual++;

        }
        }