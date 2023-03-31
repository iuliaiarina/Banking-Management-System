package p1;


public class Main {
        public static void main(String[] args) {
        	//prima pagina:
        	PStartView view =new PStartView();
        	PStartModel model =new PStartModel();
        	PStartController controller =new PStartController(model, view);
        }
    }

