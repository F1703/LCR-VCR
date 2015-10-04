/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacionestp6;

import java.util.Scanner;


/**
 *
 * @author Franco
 */
public class ComunicacionesTP6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner tecl=new Scanner(System.in);
        int orden;
        int filas;
        int cols;
        int vector[][];
        int valor=0;
        
        while(valor!=3){
            System.out.println("\033[32m[-==========================-[MENU]-==========================-]");
            System.out.println("\033[32m 1.- Generar Matriz automaticamente y verificar LCR y VCR");
            System.out.println("\033[32m 2.- Ingresar Matriz y verificar LCR y VCR");
            System.out.println("\033[32m 3.- Salir.");
            System.out.println("\033[32m[-============================================================-]");
            valor=tecl.nextInt();
            switch (valor) {
                case 1: 
                    System.out.println("Ingrese orden de la Matriz: ");
                    orden=tecl.nextInt();
                    filas=orden;
                    cols=orden;
                    vector=new int[filas][cols];
                    generarMatriz(vector, filas, cols);
                    break;
                case 2: 
                    System.out.println("Ingrese orden de la Matriz:");
                    orden=tecl.nextInt();
                    filas=orden;
                    cols=orden;
                    vector=new int[filas][cols];
                    ingresarMatriz(vector,filas,cols);
                    break;
                case 3: 
                    break;
            }
        }
    }//main
    
    public static void ingresarMatriz(int vector[][],int filas,int cols){
        Scanner tecla=new Scanner(System.in);
        //ingresar matriz
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("vector["+i+"]["+j+"]:");
                vector[i][j]=tecla.nextInt();
            }
        }
        //mostrar matriz
        System.out.println("\n");
        for (int i = 0; i <filas; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(" ["+vector[i][j]+"] ");
            }
            System.out.println("");
        }
        //comprobar imparidad, paridad, estado vcr lcr
        corregirMatriz(vector, filas, cols);
        //comprobar solamente el vcr y lcr
        calcularLCRVCR(vector, filas, cols);
    }
    
    
    public static void generarMatriz(int vector[][],int filas, int cols){
        int cont_f=0;
        int cont_c=0;
        int cont_lcr=0;//para contar la imparidad de 1 de LCR
        //cargar matriz automaticamente
        System.out.println("\033[33m=======================[MATRIZ AUTOMATICA]=======================");
        System.out.println("\033[33m Creando matriz "+filas+"x"+cols+" automaticamente....");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                vector[i][j]=(int)(Math.random()*2);
                System.out.print("\033[32m ["+vector[i][j]+"] ");
            }
            System.out.println("");
        }
        System.out.println("\033[33m Matriz creada satisfactoriamente.");
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //a la matriz le calcula el VCR y el LCR
        System.out.println("\n\033[33m Calculando el VCR y LCR ...");
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(" ["+vector[i][j]+"] ");
                if (vector[i][j]==1) {
                    cont_f=cont_f+vector[i][j];
                }
                if(vector[j][i]==1){
                    cont_c=cont_c+vector[j][i];
                }
                if(j==cols-2){
                    if(cont_f%2==0){
                        vector[i][cols-1]=0;
                    }else if(cont_f%2==1){
                        vector[i][cols-1]=1;
                        cont_lcr=cont_lcr+vector[i][cols-1];
                    }
                    if(i==cols-1){//aqui pregunto si estoy en ultima posicion de la matriz,es para calcular la imparidad del lcr, si el lcr es impar le agrego 0,de lo contrario 1
                        int x=cont_lcr-vector[i][cols-1];
                        if(x%2==0){ 
                        vector[i][cols-1]=1;
                        }else if (x%2==1) {
                        vector[i][cols-1]=0;
                        }
                    }
                    cont_f=0;
                }
            }//for j
            cont_f=0;
            int x=cont_c-vector[filas-1][i];
            if(x%2==0){
                vector[filas-1][i]=1;
            }else if (x%2==1) {
                vector[filas-1][i]=0;
            }
            cont_c=0;
            System.out.println("");
        }
        System.out.println("\033[33m Matriz modificada satisfactoriamente.");
        System.out.println("\033[33m=====================[END MATRIZ AUTOMATICA]=====================");
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        corregirMatriz(vector, filas, cols);
        calcularLCRVCR(vector, filas, cols);
    }
    
    
    //metodo para corregir la paridad 1 e imparidad (vcr y lcr)
    public static void corregirMatriz(int vector[][],int filas, int cols){
        System.out.println("\033[33m============================[LCR-VCR]============================");
        System.out.println("\033[33mComprobando LCR(paridad par de 1) y VCR(paridad impar 1)");// \033[33m color amarillo
        int lcr=0;
        int vcr=0;
        boolean estado=true;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                if (vector[i][j]==1) {
                    lcr=lcr+vector[i][j];
                }
                if (vector[j][i]==1) {
                    vcr=vcr+vector[j][i];
                }
                if(j==cols-2){
                    if (lcr%2==0 && vector[i][cols-1]==1) {
                        System.out.println("error ["+i+"]"+"["+(cols-1)+"]="+vector[i][cols-1]+", se esperaba un 0.");
                        estado=false;
                    }else if (lcr%2==1 && vector[i][cols-1]==0) {
                        System.out.println("error ["+i+"]"+"["+(cols-1)+"]="+vector[i][cols-1]+", se esperaba un 1.");
                        estado=false;
                    }
                    lcr=0;
                }
            }//end for j
            lcr=0;
            int acum=vcr-vector[filas-1][i];
            //controla si la suma de las columnas es par y el vcr es 1, entonces esta correcto
            if (acum%2==0 && vector[filas-1][i]==0) {
                System.out.println("error ["+(filas-1)+"]["+i+"]="+vector[filas-1][i]+", se esperaba un 1.");
                estado=false;
            //controla si la suma de las columnas es impar y el vcr el 0, entonces esta correcto
            }else if (acum%2==1 && vector[filas-1][i]==1) {
                System.out.println("error ["+(filas-1)+"]["+i+"]="+vector[filas-1][i]+", se esperaba un 0.");
                estado=false;
            }
            vcr=0;
        }
        
        if (estado==true) {
            System.out.println("La matriz no presenta errores.");
        }else if (estado==false) {
            System.out.println("La matriz contiene errores.");
        }
        System.out.println("\033[33m=========================[END LCR-VCR]===========================");
    }//en metodo
    
    
    
    
    
    
    //calcula solamente el LCR y VCR
    public static void calcularLCRVCR(int vector[][], int filas, int cols){
        System.out.println("\033[33m=========================[ESTADO MATRIZ]=========================");
        int lcr=0;
        int vcr=0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                if(j==cols-1){
                    lcr=lcr+vector[i][cols-1];
                    vcr=vcr+vector[filas-1][i];
                }
                if (i==filas-1 && j==cols-1) {
                    int c=lcr-vector[filas-1][cols-1];
                    int x=vcr-vector[filas-1][cols-1];
                    if(c%2==0 && vector[filas-1][cols-1]==1){
                        System.out.println("LCR coreccto.");
                    }else if (c%2==0 && vector[filas-1][cols-1]==0) {
                        System.out.println("LCR incoreccto.");
                    }else if (c%2==1 && vector[filas-1][cols-1]==1) {
                        System.out.println("LCR incorrecto");
                    }else if (c%2==1 && vector[filas-1][cols-1]==0) {
                        System.out.println("LCR correcto");
                    }
                    if(x%2==0 && vector[filas-1][cols-1]==0){
                        System.out.println("VCR coreccto.");
                    }else if (x%2==0 && vector[filas-1][cols-1]==1) {
                        System.out.println("VCR incoreccto.");
                    }else if (x%2==1 && vector[filas-1][cols-1]==1) {
                        System.out.println("VCR correcto.");
                    }else if (x%2==1 && vector[filas-1][cols-1]==0) {
                        System.out.println("VCR incorrecto.");
                    }
                }
            }
        }
        System.out.println("\033[33m=======================[END ESTADO MATRIZ]=======================");
    }
    
    
}
